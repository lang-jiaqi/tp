package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AttachCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cat.Cat;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 * Manages command parsing, execution, undo state tracking,
 * and persistence to storage after each command.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * The full command text of the last undoable command (e.g. "add n/Bowie t/Orange l/Utown"),
     * shown in the undo confirmation dialog so the user knows exactly what will be reversed.
     */
    private String lastUndoableCommandText;

    /** Temporarily holds the raw command text between {@code parseCommand} and {@code executeCommand}. */
    private String lastParsedCommandText;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        Command command = parseCommand(commandText);
        return executeCommand(command);
    }

    @Override
    public Command parseCommand(String commandText) throws ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        lastParsedCommandText = commandText.trim();
        return addressBookParser.parseCommand(commandText);
    }

    /**
     * Returns true if {@code command} is one of the commands whose effect can be undone:
     * {@code add}, {@code delete}, {@code update}, and {@code attach}.
     *
     * @param command the command about to be executed
     * @return true if the command is one of the undoable command types
     */
    private boolean isUndoableCommand(Command command) {
        return command instanceof AddCommand
                || command instanceof DeleteCommand
                || command instanceof UpdateCommand
                || command instanceof AttachCommand;
    }

    @Override
    public CommandResult executeCommand(Command command) throws CommandException {
        if (isUndoableCommand(command)) {
            model.saveUndoState();
            lastUndoableCommandText = lastParsedCommandText;
        } else if (command instanceof ClearCommand) {
            // Only clear clears the undo state, because it is a destructive
            // operation that cannot be meaningfully reversed.
            model.clearUndoState();
            lastUndoableCommandText = null;
        }
        // Read-only commands (list, find, help, export, exit) and UndoCommand
        // leave the undo state untouched so that a previous undoable command
        // can still be reversed.

        CommandResult commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public Optional<Cat> getUpdatePreview(Command command) throws CommandException {
        if (!(command instanceof UpdateCommand)) {
            return Optional.empty();
        }
        UpdateCommand updateCommand = (UpdateCommand) command;
        return Optional.of(updateCommand.getEditedCatPreview(model));
    }

    @Override
    public Optional<Cat> getDeletePreview(Command command) throws CommandException {
        if (!(command instanceof DeleteCommand)) {
            return Optional.empty();
        }
        DeleteCommand deleteCommand = (DeleteCommand) command;
        return Optional.of(deleteCommand.getCatToDeletePreview(model));
    }

    @Override
    public Optional<Integer> getClearPreview(Command command) {
        if (!(command instanceof ClearCommand)) {
            return Optional.empty();
        }
        return Optional.of(model.getAddressBook().getCatList().size());
    }

    @Override
    public boolean canUndo() {
        return model.canUndo();
    }

    @Override
    public Optional<String> getLastUndoableCommandDescription() {
        return Optional.ofNullable(lastUndoableCommandText);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Cat> getFilteredCatList() {
        return model.getFilteredCatList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}

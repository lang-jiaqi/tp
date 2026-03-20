package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
        return addressBookParser.parseCommand(commandText);
    }

    @Override
    public CommandResult executeCommand(Command command) throws CommandException {
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

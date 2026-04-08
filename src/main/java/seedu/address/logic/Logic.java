package seedu.address.logic;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cat.Cat;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Parses the command text into a Command without executing it.
     * @param commandText The command as entered by the user.
     * @return the parsed command.
     * @throws ParseException If an error occurs during parsing.
     */
    Command parseCommand(String commandText) throws ParseException;

    /**
     * Executes a pre-parsed command and returns the result.
     * @param command The command to execute.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult executeCommand(Command command) throws CommandException;

    /**
     * Returns a preview of the cat that would result from an update command, if the given command is an UpdateCommand.
     * @param command The parsed command.
     * @return Optional containing the edited cat preview, or empty if the command is not an UpdateCommand.
     * @throws CommandException If an error occurs while getting the preview (e.g. invalid index).
     */
    Optional<Cat> getUpdatePreview(Command command) throws CommandException;

    /**
     * Returns a preview of the cat that would be deleted, if the given command is a DeleteCommand.
     * @param command The parsed command.
     * @return Optional containing the cat to be deleted, or empty if the command is not a DeleteCommand.
     * @throws CommandException If an error occurs while getting the preview (e.g. invalid index).
     */
    Optional<Cat> getDeletePreview(Command command) throws CommandException;

    /**
     * Returns the count of cat entries that would be cleared, if the given command is a ClearCommand.
     * @param command The parsed command.
     * @return Optional containing the number of entries to be cleared, or empty if not a ClearCommand.
     */
    Optional<Integer> getClearPreview(Command command);

    /**
     * Returns true if there is a saved undo state that can be restored.
     * Used by the UI to decide whether to show an undo confirmation dialog.
     *
     * @return true if the most recent undoable command can be reversed
     */
    boolean canUndo();

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of cats */
    ObservableList<Cat> getFilteredCatList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

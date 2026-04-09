package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverses the most recently executed undoable command that modified the cat list.
 *
 * <p>Only the following commands are undoable: {@code add}, {@code delete}, {@code update},
 * and {@code attach}. Commands that only read data ({@code list}, {@code find}, {@code help})
 * or are system-level ({@code clear}, {@code export}) are not undoable. Read-only commands
 * do not affect the undo state, so undo remains available across them. Attempting to undo
 * when there is no saved state returns a "nothing to undo" message.</p>
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Previous command successfully undone.";
    public static final String MESSAGE_NOTHING_TO_UNDO = "Nothing to undo.";

    /**
     * Executes the undo command by restoring the address book to the state before the most recent
     * undoable command. If there is no saved state (e.g. no undoable command has been run yet, or
     * the state was cleared by a clear command), returns a message indicating nothing was undone.
     *
     * @param model the {@code Model} which the command should operate on
     * @return a {@code CommandResult} with a success or "nothing to undo" message
     * @throws CommandException not thrown by this command, but declared for interface compliance
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndo()) {
            return new CommandResult(MESSAGE_NOTHING_TO_UNDO);
        }

        model.undoLastChange();
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}

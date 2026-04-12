package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Name;

/**
 * Deletes a cat identified using its displayed index or name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the cat identified by the index number or name (case-sensitive) in the displayed cat list.\n"
            + "Parameters: INDEX (must be a positive integer) or CAT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " Whiskers";

    public static final String MESSAGE_DELETE_CAT_SUCCESS = "Deleted Cat: %1$s";
    public static final String MESSAGE_CAT_NOT_FOUND = "No cat with the name '%1$s' found in the displayed list.";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a DeleteCommand to delete the cat at the specified {@code targetIndex}.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DeleteCommand to delete the cat with the specified {@code targetName} (case-sensitive).
     */
    public DeleteCommand(Name targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> lastShownList = model.getFilteredCatList();

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            Cat catToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteCat(catToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_CAT_SUCCESS, Messages.format(catToDelete)));
        } else {
            Optional<Cat> catToDelete = lastShownList.stream()
                    .filter(cat -> cat.getName().fullName.equals(targetName.fullName))
                    .findFirst();
            if (catToDelete.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_CAT_NOT_FOUND, targetName.fullName));
            }
            model.deleteCat(catToDelete.get());
            return new CommandResult(String.format(MESSAGE_DELETE_CAT_SUCCESS, Messages.format(catToDelete.get())));
        }
    }

    /**
     * Returns a preview of the cat that would be deleted, without actually deleting.
     * Used for confirmation dialogs before executing the delete.
     *
     * @param model the current model state
     * @return the cat that would be deleted
     * @throws CommandException if the target cat cannot be found
     */
    public Cat getCatToDeletePreview(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> lastShownList = model.getFilteredCatList();

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            return lastShownList.get(targetIndex.getZeroBased());
        } else {
            return lastShownList.stream()
                    .filter(cat -> cat.getName().fullName.equals(targetName.fullName))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_CAT_NOT_FOUND, targetName.fullName)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        if (targetIndex != null) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        }
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}

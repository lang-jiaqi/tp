package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

/**
 * Finds and lists all cats in the cat notebook matching the provided search criteria.
 * Searches can be performed by name, location, trait, or health status.
 * Keyword matching is case-insensitive and uses substring matching.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds cats based on specific criteria.\n"
            + "Parameters: n/NAME l/LOCATION t/TRAIT h/HEALTH_STATUS...\n"
            + "Example: " + COMMAND_WORD + " n/Mochi l/UTown t/calico";

    public static final String MESSAGE_SUCCESS = "These are all the cats that I found for you: ";
    public static final String MESSAGE_NO_MATCH =
            "There is no such profile in my records! Is there a typo?";

    private final CatContainsKeywordsPredicate predicate;

    public FindCommand(CatContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCatList(predicate);

        if (model.getFilteredCatList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_MATCH);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_CATS_LISTED_OVERVIEW, model.getFilteredCatList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}

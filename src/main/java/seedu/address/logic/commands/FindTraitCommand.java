package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.cat.TraitContainsKeywordsPredicate;


/**
 * Finds and lists all cats in CatPals whose traits/tags contain any of the argument keywords.
 */
public class FindTraitCommand extends Command {

    public static final String COMMAND_WORD = "findtrait";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all cats whose traits contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friendly calico";

    private final TraitContainsKeywordsPredicate predicate;

    public FindTraitCommand(TraitContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCatList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CATS_LISTED_OVERVIEW, model.getFilteredCatList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindTraitCommand)) {
            return false;
        }

        FindTraitCommand otherFindTraitCommand = (FindTraitCommand) other;
        return predicate.equals(otherFindTraitCommand.predicate);
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }
}

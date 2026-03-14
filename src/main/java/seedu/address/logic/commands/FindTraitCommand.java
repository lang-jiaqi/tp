package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.cat.TraitContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;


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
}


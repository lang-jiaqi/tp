package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CATS;

import seedu.address.model.Model;

/**
 * Lists all cats in the cat notebook to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all cats";

    public static final String MESSAGE_EXTRA_ARGS =
            "list does not take extra parameters.\nDid you just mean: list";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCatList(PREDICATE_SHOW_ALL_CATS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

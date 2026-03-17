package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindTraitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.TraitContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTraitCommand object.
 */
public class FindTraitCommandParser implements Parser<FindTraitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTraitCommand
     * and returns a FindTraitCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindTraitCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindTraitCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindTraitCommand(new TraitContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

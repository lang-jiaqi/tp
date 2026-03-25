package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        assert args != null : "args passed to parser should never be null";
        logger.info("Parsing FindCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH);

        // Validation logic
        if (!argMultimap.getPreamble().isEmpty() || !isAnyPrefixPresent(argMultimap)) {
            logger.warning("Parsing failed: Missing prefixes or invalid preamble.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        checkForMultipleKeywords(argMultimap);

        // Extracting keywords - requiring individual flags for each keyword
        List<String> nameKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_NAME);
        List<String> locationKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_LOCATION);
        List<String> traitKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_TRAIT);
        List<String> healthKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_HEALTH);

        // Post-parsing assertions to ensure logic integrity
        assert nameKeywords != null : "Keyword list should be empty, not null";
        assert locationKeywords != null : "Keyword list should be empty, not null";

        logger.fine("Successfully parsed FindCommand with specific flags.");

        return new FindCommand(new CatContainsKeywordsPredicate(
                nameKeywords, locationKeywords, traitKeywords, healthKeywords));
    }

    /**
     * Checks if any prefix contains multiple words (separated by spaces).
     * @throws ParseException if a prefix contains more than one word.
     */
    private void checkForMultipleKeywords(ArgumentMultimap argMultimap) throws ParseException {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH};

        for (Prefix prefix : prefixes) {
            List<String> values = argMultimap.getAllValues(prefix);
            for (String value : values) {
                if (value.trim().contains(" ")) {
                    logger.warning("User provided multiple keywords for a single flag: " + prefix + value);
                    throw new ParseException("Each keyword must be preceded by its identifier flag "
                            + "(e.g., t/friendly t/calico).");
                }
            }
        }
    }

    /**
     * Returns true if at least one of the prefixes contains a value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH)
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Extracts keywords from the multimap for a specific prefix.
     * Splits values by identifiers to allow multiple keywords per command (e.g., t/friendly t/calico).
     */
    private List<String> getKeywordsFromPrefix(ArgumentMultimap argMultimap, Prefix prefix) {
        assert argMultimap != null : "ArgumentMultimap cannot be null";
        assert prefix != null : "Prefix cannot be null";

        return argMultimap.getAllValues(prefix).stream()
                .map(String::trim)
                .filter(val -> !val.isEmpty())
                .toList();
    }
}

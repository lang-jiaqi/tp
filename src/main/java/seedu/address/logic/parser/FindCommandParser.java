package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Pattern UPDATE_PREFIX_PATTERN = Pattern.compile("(?i)(^|\\s)([ntlh])/");
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        assert args != null : "args passed to parser should never be null";
        logger.info("Parsing FindCommand with arguments: " + args);

        String normalizedArgs = normalizeUpdatePrefixes(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(normalizedArgs, PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH);

        // Validation logic
        if (!argMultimap.getPreamble().isEmpty() || !isAnyPrefixPresent(argMultimap)) {
            logger.warning("Parsing failed: Missing prefixes or invalid preamble.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        validateKeywords(argMultimap);

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
     * Normalizes update field prefixes to lowercase so n/t/l/h are case-insensitive.
     */
    private String normalizeUpdatePrefixes(String args) {
        Matcher matcher = UPDATE_PREFIX_PATTERN.matcher(args);
        StringBuffer normalized = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(normalized, Matcher.quoteReplacement(
                    matcher.group(1) + matcher.group(2).toLowerCase() + "/"));
        }
        matcher.appendTail(normalized);
        return normalized.toString();
    }

    /**
     * Validates the extracted keywords to ensure they meet the command's constraints.
     * @throws ParseException if a keyword is empty or if a prefix contains multiple words.
     */
    private void validateKeywords(ArgumentMultimap argMultimap) throws ParseException {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH};

        for (Prefix prefix : prefixes) {
            List<String> values = argMultimap.getAllValues(prefix);
            for (String value : values) {
                String trimmedValue = value.trim();

                // 1. Check for empty keywords (Fixes Issue #186)
                if (trimmedValue.isEmpty()) {
                    logger.warning("User provided an empty keyword for flag: " + prefix);
                    throw new ParseException("Search keyword cannot be empty. "
                            + "Please provide a valid search term (e.g., n/Mochi).");
                }

                // 2. Check for multiple words per flag
                if (trimmedValue.contains(" ")) {
                    logger.warning("User provided multiple keywords for single flag " + prefix + ": " + value);
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

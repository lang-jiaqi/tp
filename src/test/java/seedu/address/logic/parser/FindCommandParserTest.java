package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // Random text without a prefix (Preamble only - should fail)
        assertParseFailure(parser, " Mochi", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Single flag
        CatContainsKeywordsPredicate singleFlagPredicate = new CatContainsKeywordsPredicate(
                Arrays.asList("Mochi"), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertParseSuccess(parser, " n/Mochi", new FindCommand(singleFlagPredicate));

        // Multiple flags
        CatContainsKeywordsPredicate multipleFlagsPredicate = new CatContainsKeywordsPredicate(
                Arrays.asList("Mochi"), Arrays.asList("UTown"),
                Arrays.asList("calico"), Collections.emptyList());
        assertParseSuccess(parser, " n/Mochi l/UTown t/calico", new FindCommand(multipleFlagsPredicate));
    }

    @Test
    public void parse_multipleKeywordsWithoutFlags_throwsParseException() {
        // Testing name flag with multiple words
        assertParseFailure(parser, " n/Mochi Luna",
                "Each keyword must be preceded by its identifier flag (e.g., t/friendly t/calico).");

        // Testing trait flag with multiple words
        assertParseFailure(parser, " t/calico friendly",
                "Each keyword must be preceded by its identifier flag (e.g., t/friendly t/calico).");
    }

}

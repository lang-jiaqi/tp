package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.cat.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                "Name is missing for this find command.");
    }

    @Test
    public void parse_invalidSymbols_throwsParseException() {
        assertParseFailure(parser, "Bowi###e",
                "The name must not contain symbols");
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        // normal spaces
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple spaces only
        assertParseSuccess(parser, "  Alice   Bob  ", expectedFindCommand);
    }

}

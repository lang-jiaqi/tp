package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.HEALTH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TRAIT_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.TRAIT_DESC_ORANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_ORANGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCats.AMY;
import static seedu.address.testutil.TypicalCats.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;
import seedu.address.testutil.CatBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Cat expectedCat = new CatBuilder(BOB).withTraits(VALID_TRAIT_ORANGE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TRAIT_DESC_ORANGE
                + LOCATION_DESC_BOB + HEALTH_DESC_BOB, new AddCommand(expectedCat));

        // multiple traits - all accepted (up to 3)
        Cat expectedCatMultipleTraits = new CatBuilder(BOB)
                .withTraits(VALID_TRAIT_FLUFFY, VALID_TRAIT_ORANGE).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + TRAIT_DESC_FLUFFY + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB + HEALTH_DESC_BOB,
                new AddCommand(expectedCatMultipleTraits));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // health is optional - defaults to Unknown
        Cat expectedCat = new CatBuilder(AMY).withHealth(Health.DEFAULT_VALUE)
                .withTraits(VALID_TRAIT_ORANGE).build();
        assertParseSuccess(parser, NAME_DESC_AMY + TRAIT_DESC_ORANGE + LOCATION_DESC_AMY,
                new AddCommand(expectedCat));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB,
                AddCommand.MESSAGE_MISSING_ATTRIBUTES);

        // missing location prefix
        assertParseFailure(parser, NAME_DESC_BOB + TRAIT_DESC_ORANGE + VALID_LOCATION_BOB,
                AddCommand.MESSAGE_MISSING_ATTRIBUTES);

        // missing trait prefix
        assertParseFailure(parser, NAME_DESC_BOB + LOCATION_DESC_BOB,
                "Your Add command is incomplete. Please enter again.");

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TRAIT_ORANGE + VALID_LOCATION_BOB,
                AddCommand.MESSAGE_MISSING_ATTRIBUTES);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name (contains symbol)
        assertParseFailure(parser, INVALID_NAME_DESC + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB + HEALTH_DESC_BOB,
                Name.MESSAGE_HAS_SYMBOLS);

        // blank name
        assertParseFailure(parser, " " + CliSyntax.PREFIX_NAME + " " + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB,
                Name.MESSAGE_BLANK);

        // name too long
        assertParseFailure(parser, " " + CliSyntax.PREFIX_NAME + "averyveryveryveryveryverylongname"
                + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB, Name.MESSAGE_TOO_LONG);

        // blank location
        assertParseFailure(parser, NAME_DESC_BOB + TRAIT_DESC_ORANGE + INVALID_LOCATION_DESC,
                Location.MESSAGE_BLANK);

        // too many traits
        assertParseFailure(parser, NAME_DESC_BOB + TRAIT_DESC_ORANGE + TRAIT_DESC_FLUFFY
                + " " + CliSyntax.PREFIX_TRAIT + "Brown"
                + " " + CliSyntax.PREFIX_TRAIT + "long tail"
                + LOCATION_DESC_BOB, Trait.MESSAGE_TOO_MANY);

        // duplicate traits
        assertParseFailure(parser, NAME_DESC_BOB + TRAIT_DESC_ORANGE + TRAIT_DESC_ORANGE
                + LOCATION_DESC_BOB, Trait.MESSAGE_DUPLICATE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TRAIT_DESC_ORANGE
                + LOCATION_DESC_BOB + HEALTH_DESC_BOB,
                AddCommand.MESSAGE_INVALID_PARAMETERS);
    }

    @Test
    public void parse_duplicateLocation_failure() {
        // duplicate l/ prefix
        assertParseFailure(parser, NAME_DESC_BOB + TRAIT_DESC_ORANGE
                + LOCATION_DESC_BOB + LOCATION_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_LOCATION));
    }

    @Test
    public void parse_duplicateName_failure() {
        // duplicate n/ prefix
        assertParseFailure(parser, NAME_DESC_BOB + NAME_DESC_AMY + TRAIT_DESC_ORANGE + LOCATION_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));
    }
}

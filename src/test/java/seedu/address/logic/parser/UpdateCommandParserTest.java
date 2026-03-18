package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.HEALTH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TRAIT_DESC_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.TRAIT_DESC_ORANGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_ORANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.testutil.EditCatDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String TRAIT_EMPTY = " " + PREFIX_TRAIT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_HAS_SYMBOLS); // invalid name
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Location.MESSAGE_BLANK); // blank location
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TRAIT_DESC_FLUFFY + TRAIT_DESC_ORANGE
                + LOCATION_DESC_AMY + NAME_DESC_AMY + HEALTH_DESC_AMY;

        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTraits(VALID_TRAIT_FLUFFY, VALID_TRAIT_ORANGE).withLocation(VALID_LOCATION_AMY)
                .withHealth(VALID_HEALTH_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_AMY;

        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withLocation(VALID_LOCATION_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_AMY;
        descriptor = new EditCatDescriptorBuilder().withLocation(VALID_LOCATION_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // traits
        userInput = targetIndex.getOneBased() + TRAIT_DESC_ORANGE;
        descriptor = new EditCatDescriptorBuilder().withTraits(VALID_TRAIT_ORANGE).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // duplicate location
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_AMY + LOCATION_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }

    @Test
    public void parse_resetTraits_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TRAIT_EMPTY;

        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withTraits().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

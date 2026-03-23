package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttachCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Name;

/**
 * Parses input arguments and creates a new AttachCommand object.
 * Expected format: attach INDEX IMAGE_PATH or attach CAT_NAME IMAGE_PATH
 * AI-generated.
 */
public class AttachCommandParser implements Parser<AttachCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an AttachCommand.
     * AI-generated.
     * @throws ParseException if the input does not conform to the expected format.
     */
    public AttachCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        int spaceIndex = trimmed.indexOf(' ');

        if (spaceIndex == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }

        String identifier = trimmed.substring(0, spaceIndex).trim();
        String imagePath = trimmed.substring(spaceIndex + 1).trim();

        if (identifier.isEmpty() || imagePath.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }

        CatImage image = ParserUtil.parseImage(imagePath);

        // Try to parse identifier as index first, fall back to name
        try {
            Index index = ParserUtil.parseIndex(identifier);
            return new AttachCommand(index, image);
        } catch (ParseException ignored) {
            // Not a valid index — try as cat name
        }

        if (!Name.isValidName(identifier)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }
        return new AttachCommand(new Name(identifier), image);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttachCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Name;

/**
 * Parses input arguments and creates a new AttachCommand object.
 * Expected format: attach INDEX IMAGE_PATH or attach CAT_NAME IMAGE_PATH
 *                  attach INDEX --reset   or attach CAT_NAME --reset
 */
public class AttachCommandParser implements Parser<AttachCommand> {

    public static final String RESET_FLAG = "--reset";

    /**
     * Parses the given {@code String} of arguments and returns an AttachCommand.
     * @throws ParseException if the input does not conform to the expected format.
     */
    public AttachCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        int lastSpaceIndex = trimmed.lastIndexOf(' ');

        if (lastSpaceIndex == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }

        String identifier = trimmed.substring(0, lastSpaceIndex).trim();
        String second = trimmed.substring(lastSpaceIndex + 1).trim();

        if (identifier.isEmpty() || second.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }

        if (second.equals(RESET_FLAG)) {
            return parseReset(identifier);
        }

        CatImage image = ParserUtil.parseImage(second);

        // Try to parse identifier as index first, fall back to name
        try {
            Index index = ParserUtil.parseIndex(identifier);
            return new AttachCommand(index, image);
        } catch (ParseException ignored) {
            // If input looks like a number (e.g. 0, -1), it's an invalid index — not a name
            if (trimmed.matches("-?\\d+")) {
                throw new ParseException(MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            // Not a valid index — try as cat name
        }

        if (!Name.isValidName(identifier)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }
        return new AttachCommand(new Name(identifier), image);
    }

    /**
     * Parses a reset command for the given identifier (index or name).
     */
    private AttachCommand parseReset(String identifier) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(identifier);
            return new AttachCommand(index);
        } catch (ParseException ignored) {
            // Not a valid index — try as cat name
        }

        if (!Name.isValidName(identifier)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachCommand.MESSAGE_USAGE));
        }
        return new AttachCommand(new Name(identifier));
    }
}

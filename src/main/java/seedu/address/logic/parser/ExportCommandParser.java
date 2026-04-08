package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MESSAGE_INVALID_FILENAME =
            "Filename must not contain any of these characters: \\ / : * ? \" < > |";

    private static final String INVALID_CHARS_REGEX = ".*[\\\\/:*?\"<>|].*";

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();

        if (trimmed.isEmpty()) {
            return new ExportCommand();
        }

        String filename = trimmed.replace(' ', '-');

        if (filename.matches(INVALID_CHARS_REGEX)) {
            throw new ParseException(MESSAGE_INVALID_FILENAME);
        }

        return new ExportCommand(filename, trimmed);
    }
}

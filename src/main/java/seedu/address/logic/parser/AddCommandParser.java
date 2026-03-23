package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TRAIT, PREFIX_LOCATION, PREFIX_HEALTH,
                        PREFIX_IMAGE);

        // Check required fields are present first (before preamble check)
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LOCATION)) {
            throw new ParseException(AddCommand.MESSAGE_MISSING_ATTRIBUTES);
        }

        // Trait must be present
        if (!arePrefixesPresent(argMultimap, PREFIX_TRAIT)) {
            throw new ParseException("Your Add command is incomplete. Please enter again.");
        }

        // Reject non-empty preamble (all required prefixes are present but extra text before them)
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(AddCommand.MESSAGE_INVALID_PARAMETERS);
        }

        // Enforce single name and single location (no duplicate prefixes)
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LOCATION);

        // Duplicate health detection: health is optional, restrict to one
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEALTH);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        List<Trait> traits = ParserUtil.parseTraits(argMultimap.getAllValues(PREFIX_TRAIT));

        // Health is optional; defaults to "Unknown"
        Health health;
        if (argMultimap.getValue(PREFIX_HEALTH).isPresent()) {
            health = new Health(argMultimap.getValue(PREFIX_HEALTH).get().trim());
        } else {
            health = new Health(Health.DEFAULT_VALUE);
        }

        // Image is optional; defaults to no image
        CatImage image = argMultimap.getValue(PREFIX_IMAGE).isPresent()
                ? ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE).get())
                : new CatImage(CatImage.DEFAULT_VALUE);

        Cat cat = new Cat(name, traits, location, health, image);
        return new AddCommand(cat);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

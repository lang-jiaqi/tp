package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Trait;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "No such command found!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CAT_DISPLAYED_INDEX = "The cat index provided is invalid";
    public static final String MESSAGE_CATS_LISTED_OVERVIEW = "%1$d cats listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code cat} for display to the user.
     * The output format is: {@code Name; Traits: trait1 trait2 ...; Location: loc; Health: health}.
     */
    public static String format(Cat cat) {
        final StringBuilder builder = new StringBuilder();
        builder.append(cat.getName());
        builder.append("; Traits: ");
        for (Trait trait : cat.getTraits()) {
            builder.append(trait.traitName).append(" ");
        }
        builder.append("; Location: ")
                .append(cat.getLocation())
                .append("; Health: ")
                .append(cat.getHealth());
        return builder.toString().trim();
    }

}

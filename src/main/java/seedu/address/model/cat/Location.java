package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cat's location in the cat notebook.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_BLANK = "Location must not be blank!";
    public static final String MESSAGE_TOO_LONG = "Location must be no longer than 50 chars!";
    public static final String MESSAGE_DUPLICATE = "You cannot add duplicate locations!";
    public static final int MAX_LENGTH = 50;

    // Constraints message used by constructor checkArgument
    public static final String MESSAGE_CONSTRAINTS = MESSAGE_BLANK;

    /*
     * The first character must not be whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location string.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = location;
    }

    /**
     * Returns true if a given string is a valid location (non-blank).
     *
     * @param test the string to test.
     * @return true if {@code test} is non-blank.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return value.equals(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

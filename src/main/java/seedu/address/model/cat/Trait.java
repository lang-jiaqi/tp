package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cat's trait in the cat notebook.
 * Guarantees: immutable; is valid as declared in {@link #isValidTrait(String)}
 */
public class Trait {

    public static final String MESSAGE_CONSTRAINTS = "Traits can take any values, and it should not be blank";
    public static final String MESSAGE_DUPLICATE = "You cannot add duplicate traits!";
    public static final String MESSAGE_TOO_MANY = "You added more than 3 traits to the cat. "
            + "Please only add up to 3 traits.";

    /*
     * The first character must not be whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String traitName;

    /**
     * Constructs a {@code Trait}.
     *
     * @param traitName A valid trait name.
     */
    public Trait(String traitName) {
        requireNonNull(traitName);
        checkArgument(isValidTrait(traitName), MESSAGE_CONSTRAINTS);
        this.traitName = traitName;
    }

    /**
     * Returns true if a given string is a valid trait.
     *
     * @param test the string to test.
     * @return true if {@code test} is non-blank.
     */
    public static boolean isValidTrait(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Trait)) {
            return false;
        }

        Trait otherTrait = (Trait) other;
        return traitName.equals(otherTrait.traitName);
    }

    @Override
    public int hashCode() {
        return traitName.hashCode();
    }

    @Override
    public String toString() {
        return '[' + traitName + ']';
    }

}

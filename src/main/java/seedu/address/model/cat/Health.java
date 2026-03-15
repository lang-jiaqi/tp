package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Cat's health status in the cat notebook.
 * Guarantees: immutable; value is not null.
 */
public class Health {

    public static final String DEFAULT_VALUE = "Unknown";

    public final String value;

    /**
     * Constructs a {@code Health}.
     *
     * @param health A health status string.
     */
    public Health(String health) {
        requireNonNull(health);
        value = health;
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

        if (!(other instanceof Health)) {
            return false;
        }

        Health otherHealth = (Health) other;
        return value.equals(otherHealth.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

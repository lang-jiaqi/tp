package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Cat's image file path in the cat notebook.
 * Guarantees: immutable; value is not null.
 * An empty string means no image has been assigned.
 */
public class CatImage {

    public static final String DEFAULT_VALUE = "";

    public final String value;

    /**
     * Constructs a {@code CatImage}.
     *
     * @param imagePath A relative file path string, or empty string for no image.
     */
    public CatImage(String imagePath) {
        requireNonNull(imagePath);
        value = imagePath;
    }

    /**
     * Returns true if an image path has been set.
     */
    public boolean hasImage() {
        return !value.isEmpty();
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

        if (!(other instanceof CatImage)) {
            return false;
        }

        CatImage otherImage = (CatImage) other;
        return value.equals(otherImage.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

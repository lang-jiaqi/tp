package seedu.address.model.cat;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Cat in the cat notebook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cat {

    // Identity field
    private final Name name;

    // Data fields
    private final List<Trait> traits = new ArrayList<>();
    private final Location location;
    private final Health health;
    private final CatImage image;

    /**
     * Every field must be present and not null.
     *
     * @param name     the cat's name.
     * @param traits   the cat's traits.
     * @param location the cat's location.
     * @param health   the cat's health status.
     * @param image    the cat's image file path.
     */
    public Cat(Name name, List<Trait> traits, Location location, Health health, CatImage image) {
        requireAllNonNull(name, traits, location, health, image);
        this.name = name;
        this.traits.addAll(traits);
        this.location = location;
        this.health = health;
        this.image = image;
    }

    /**
     * Returns the cat's name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable trait list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Trait> getTraits() {
        return Collections.unmodifiableList(traits);
    }

    /**
     * Returns the cat's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the cat's health status.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * Returns the cat's image.
     */
    public CatImage getImage() {
        return image;
    }

    /**
     * Returns true if both cats have the same name (case-insensitive).
     * This defines a weaker notion of equality between two cats.
     */
    public boolean isSameCat(Cat otherCat) {
        if (otherCat == this) {
            return true;
        }

        return otherCat != null
                && otherCat.getName().fullName.equalsIgnoreCase(this.getName().fullName);
    }

    /**
     * Returns true if both cats have the same identity and data fields.
     * This defines a stronger notion of equality between two cats.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Cat)) {
            return false;
        }

        Cat otherCat = (Cat) other;
        return name.equals(otherCat.name)
                && traits.equals(otherCat.traits)
                && location.equals(otherCat.location)
                && health.equals(otherCat.health)
                && image.equals(otherCat.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, traits, location, health, image);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("traits", traits)
                .add("location", location)
                .add("health", health)
                .add("image", image)
                .toString();
    }

}

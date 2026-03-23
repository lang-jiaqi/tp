package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Cat objects.
 */
public class CatBuilder {

    public static final String DEFAULT_NAME = "Bowie";
    public static final String DEFAULT_TRAIT = "Orange";
    public static final String DEFAULT_LOCATION = "Utown";
    public static final String DEFAULT_HEALTH = "Vaccinated";

    private Name name;
    private List<Trait> traits;
    private Location location;
    private Health health;
    private CatImage image;

    /**
     * Creates a {@code CatBuilder} with the default details.
     */
    public CatBuilder() {
        name = new Name(DEFAULT_NAME);
        traits = SampleDataUtil.getTraitList(DEFAULT_TRAIT);
        location = new Location(DEFAULT_LOCATION);
        health = new Health(DEFAULT_HEALTH);
        image = new CatImage(CatImage.DEFAULT_VALUE);
    }

    /**
     * Initializes the CatBuilder with the data of {@code catToCopy}.
     */
    public CatBuilder(Cat catToCopy) {
        name = catToCopy.getName();
        traits = new ArrayList<>(catToCopy.getTraits());
        location = catToCopy.getLocation();
        health = catToCopy.getHealth();
        image = catToCopy.getImage();
    }

    /**
     * Sets the {@code Name} of the {@code Cat} that we are building.
     */
    public CatBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code traits} into a {@code List<Trait>} and set it to the {@code Cat} that we are building.
     */
    public CatBuilder withTraits(String... traits) {
        this.traits = SampleDataUtil.getTraitList(traits);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Cat} that we are building.
     */
    public CatBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Health} of the {@code Cat} that we are building.
     */
    public CatBuilder withHealth(String health) {
        this.health = new Health(health);
        return this;
    }

    /**
     * Sets the {@code CatImage} of the {@code Cat} that we are building.
     * AI-generated.
     */
    public CatBuilder withImage(String imagePath) {
        this.image = new CatImage(imagePath);
        return this;
    }

    public Cat build() {
        return new Cat(name, traits, location, health, image);
    }

}

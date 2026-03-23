package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * Jackson-friendly version of {@link Cat}.
 */
class JsonAdaptedCat {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Cat's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedTrait> traits = new ArrayList<>();
    private final String location;
    private final String health;
    private final String image;

    /**
     * Constructs a {@code JsonAdaptedCat} with the given cat details.
     */
    @JsonCreator
    public JsonAdaptedCat(@JsonProperty("name") String name,
            @JsonProperty("traits") List<JsonAdaptedTrait> traits,
            @JsonProperty("location") String location,
            @JsonProperty("health") String health,
            @JsonProperty("image") String image) {
        this.name = name;
        if (traits != null) {
            this.traits.addAll(traits);
        }
        this.location = location;
        this.health = health;
        this.image = image;
    }

    /**
     * Converts a given {@code Cat} into this class for Jackson use.
     */
    public JsonAdaptedCat(Cat source) {
        name = source.getName().fullName;
        traits.addAll(source.getTraits().stream()
                .map(JsonAdaptedTrait::new)
                .collect(Collectors.toList()));
        location = source.getLocation().value;
        health = source.getHealth().value;
        image = source.getImage().value;
    }

    /**
     * Converts this Jackson-friendly adapted cat object into the model's {@code Cat} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cat.
     */
    public Cat toModelType() throws IllegalValueException {
        final List<Trait> catTraits = new ArrayList<>();
        for (JsonAdaptedTrait trait : traits) {
            catTraits.add(trait.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (location == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        final Health modelHealth = (health == null || health.isEmpty())
                ? new Health(Health.DEFAULT_VALUE)
                : new Health(health);

        final CatImage modelImage = (image == null) ? new CatImage(CatImage.DEFAULT_VALUE) : new CatImage(image);

        return new Cat(modelName, catTraits, modelLocation, modelHealth, modelImage);
    }

}

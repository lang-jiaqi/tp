package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cat.Trait;

/**
 * Jackson-friendly version of {@link Trait}.
 */
class JsonAdaptedTrait {

    private final String traitName;

    /**
     * Constructs a {@code JsonAdaptedTrait} with the given {@code traitName}.
     */
    @JsonCreator
    public JsonAdaptedTrait(String traitName) {
        this.traitName = traitName;
    }

    /**
     * Converts a given {@code Trait} into this class for Jackson use.
     */
    public JsonAdaptedTrait(Trait source) {
        traitName = source.traitName;
    }

    /**
     * Returns the trait name string used for JSON serialization.
     */
    @JsonValue
    public String getTraitName() {
        return traitName;
    }

    /**
     * Converts this Jackson-friendly adapted trait object into the model's {@code Trait} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted trait.
     */
    public Trait toModelType() throws IllegalValueException {
        if (!Trait.isValidTrait(traitName)) {
            throw new IllegalValueException(Trait.MESSAGE_CONSTRAINTS);
        }
        return new Trait(traitName);
    }

}

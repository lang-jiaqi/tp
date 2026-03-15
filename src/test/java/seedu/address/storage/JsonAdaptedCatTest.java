package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedCat.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.BOWIE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;

public class JsonAdaptedCatTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_TRAIT = ""; // blank not valid

    private static final String VALID_NAME = BOWIE.getName().toString();
    private static final List<JsonAdaptedTrait> VALID_TRAITS = BOWIE.getTraits().stream()
            .map(JsonAdaptedTrait::new)
            .collect(Collectors.toList());
    private static final String VALID_LOCATION = BOWIE.getLocation().toString();
    private static final String VALID_HEALTH = BOWIE.getHealth().toString();

    @Test
    public void toModelType_validCatDetails_returnsCat() throws Exception {
        JsonAdaptedCat cat = new JsonAdaptedCat(BOWIE);
        assertEquals(BOWIE, cat.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCat cat =
                new JsonAdaptedCat(INVALID_NAME, VALID_TRAITS, VALID_LOCATION, VALID_HEALTH);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, cat::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCat cat = new JsonAdaptedCat(null, VALID_TRAITS, VALID_LOCATION, VALID_HEALTH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, cat::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedCat cat =
                new JsonAdaptedCat(VALID_NAME, VALID_TRAITS, INVALID_LOCATION, VALID_HEALTH);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, cat::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedCat cat = new JsonAdaptedCat(VALID_NAME, VALID_TRAITS, null, VALID_HEALTH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, cat::toModelType);
    }

    @Test
    public void toModelType_invalidTrait_throwsIllegalValueException() {
        List<JsonAdaptedTrait> invalidTraits = new ArrayList<>(VALID_TRAITS);
        invalidTraits.add(new JsonAdaptedTrait(INVALID_TRAIT));
        JsonAdaptedCat cat =
                new JsonAdaptedCat(VALID_NAME, invalidTraits, VALID_LOCATION, VALID_HEALTH);
        assertThrows(IllegalValueException.class, cat::toModelType);
    }

}

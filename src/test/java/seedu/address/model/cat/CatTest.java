package seedu.address.model.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.BOB;
import static seedu.address.testutil.TypicalCats.BOWIE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CatBuilder;

public class CatTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Cat cat = new CatBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> cat.getTraits().remove(0));
    }

    @Test
    public void isSameCat() {
        // same object -> returns true
        assertTrue(BOWIE.isSameCat(BOWIE));

        // null -> returns false
        assertFalse(BOWIE.isSameCat(null));

        // same name, all other attributes different -> returns true
        Cat editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB)
                .withTraits(VALID_TRAIT_FLUFFY).withHealth("Unknown").build();
        assertTrue(BOWIE.isSameCat(editedBowie));

        // different name, all other attributes same -> returns false
        editedBowie = new CatBuilder(BOWIE).withName(VALID_NAME_BOB).build();
        assertFalse(BOWIE.isSameCat(editedBowie));

        // name differs in case -> returns true (case-insensitive check)
        Cat editedBob = new CatBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameCat(editedBob));

        // name has trailing spaces -> returns false (different trimmed string)
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new CatBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameCat(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Cat bowieCopy = new CatBuilder(BOWIE).build();
        assertTrue(BOWIE.equals(bowieCopy));

        // same object -> returns true
        assertTrue(BOWIE.equals(BOWIE));

        // null -> returns false
        assertFalse(BOWIE.equals(null));

        // different type -> returns false
        assertFalse(BOWIE.equals(5));

        // different cat -> returns false
        assertFalse(BOWIE.equals(BOB));

        // different name -> returns false
        Cat editedBowie = new CatBuilder(BOWIE).withName(VALID_NAME_BOB).build();
        assertFalse(BOWIE.equals(editedBowie));

        // different location -> returns false
        editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB).build();
        assertFalse(BOWIE.equals(editedBowie));

        // different traits -> returns false
        editedBowie = new CatBuilder(BOWIE).withTraits(VALID_TRAIT_FLUFFY).build();
        assertFalse(BOWIE.equals(editedBowie));
    }

    @Test
    public void toStringMethod() {
        String expected = Cat.class.getCanonicalName() + "{name=" + BOWIE.getName()
                + ", traits=" + BOWIE.getTraits()
                + ", location=" + BOWIE.getLocation()
                + ", health=" + BOWIE.getHealth() + "}";
        assertEquals(expected, BOWIE.toString());
    }
}

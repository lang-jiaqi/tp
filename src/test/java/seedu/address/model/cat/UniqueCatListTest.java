package seedu.address.model.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.BOB;
import static seedu.address.testutil.TypicalCats.BOWIE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cat.exceptions.CatNotFoundException;
import seedu.address.model.cat.exceptions.DuplicateCatException;
import seedu.address.testutil.CatBuilder;

public class UniqueCatListTest {

    private final UniqueCatList uniqueCatList = new UniqueCatList();

    @Test
    public void contains_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.contains(null));
    }

    @Test
    public void contains_catNotInList_returnsFalse() {
        assertFalse(uniqueCatList.contains(BOWIE));
    }

    @Test
    public void contains_catInList_returnsTrue() {
        uniqueCatList.add(BOWIE);
        assertTrue(uniqueCatList.contains(BOWIE));
    }

    @Test
    public void contains_catWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCatList.add(BOWIE);
        Cat editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB).withTraits(VALID_TRAIT_FLUFFY)
                .build();
        assertTrue(uniqueCatList.contains(editedBowie));
    }

    @Test
    public void add_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.add(null));
    }

    @Test
    public void add_duplicateCat_throwsDuplicateCatException() {
        uniqueCatList.add(BOWIE);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.add(BOWIE));
    }

    @Test
    public void setCat_nullTargetCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCat(null, BOWIE));
    }

    @Test
    public void setCat_nullEditedCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCat(BOWIE, null));
    }

    @Test
    public void setCat_targetCatNotInList_throwsCatNotFoundException() {
        assertThrows(CatNotFoundException.class, () -> uniqueCatList.setCat(BOWIE, BOWIE));
    }

    @Test
    public void setCat_editedCatIsSameCat_success() {
        uniqueCatList.add(BOWIE);
        uniqueCatList.setCat(BOWIE, BOWIE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOWIE);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasSameIdentity_success() {
        uniqueCatList.add(BOWIE);
        Cat editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB).withTraits(VALID_TRAIT_FLUFFY)
                .build();
        uniqueCatList.setCat(BOWIE, editedBowie);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(editedBowie);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasDifferentIdentity_success() {
        uniqueCatList.add(BOWIE);
        uniqueCatList.setCat(BOWIE, BOB);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCat_editedCatHasNonUniqueIdentity_throwsDuplicateCatException() {
        uniqueCatList.add(BOWIE);
        uniqueCatList.add(BOB);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.setCat(BOWIE, BOB));
    }

    @Test
    public void remove_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.remove(null));
    }

    @Test
    public void remove_catDoesNotExist_throwsCatNotFoundException() {
        assertThrows(CatNotFoundException.class, () -> uniqueCatList.remove(BOWIE));
    }

    @Test
    public void remove_existingCat_removesCat() {
        uniqueCatList.add(BOWIE);
        uniqueCatList.remove(BOWIE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_nullUniqueCatList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCats((UniqueCatList) null));
    }

    @Test
    public void setCats_uniqueCatList_replacesOwnListWithProvidedUniqueCatList() {
        uniqueCatList.add(BOWIE);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        uniqueCatList.setCats(expectedUniqueCatList);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCatList.setCats((List<Cat>) null));
    }

    @Test
    public void setCats_list_replacesOwnListWithProvidedList() {
        uniqueCatList.add(BOWIE);
        List<Cat> catList = Collections.singletonList(BOB);
        uniqueCatList.setCats(catList);
        UniqueCatList expectedUniqueCatList = new UniqueCatList();
        expectedUniqueCatList.add(BOB);
        assertEquals(expectedUniqueCatList, uniqueCatList);
    }

    @Test
    public void setCats_listWithDuplicateCats_throwsDuplicateCatException() {
        List<Cat> listWithDuplicateCats = Arrays.asList(BOWIE, BOWIE);
        assertThrows(DuplicateCatException.class, () -> uniqueCatList.setCats(listWithDuplicateCats));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCatList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCatList.asUnmodifiableObservableList().toString(), uniqueCatList.toString());
    }
}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.BOWIE;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.exceptions.DuplicateCatException;
import seedu.address.testutil.CatBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCatList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateCats_throwsDuplicateCatException() {
        // Two cats with the same identity fields
        Cat editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB).withTraits(VALID_TRAIT_FLUFFY)
                .build();
        List<Cat> newCats = Arrays.asList(BOWIE, editedBowie);
        AddressBookStub newData = new AddressBookStub(newCats);

        assertThrows(DuplicateCatException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasCat_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCat(null));
    }

    @Test
    public void hasCat_catNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCat(BOWIE));
    }

    @Test
    public void hasCat_catInAddressBook_returnsTrue() {
        addressBook.addCat(BOWIE);
        assertTrue(addressBook.hasCat(BOWIE));
    }

    @Test
    public void hasCat_catWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCat(BOWIE);
        Cat editedBowie = new CatBuilder(BOWIE).withLocation(VALID_LOCATION_BOB).withTraits(VALID_TRAIT_FLUFFY)
                .build();
        assertTrue(addressBook.hasCat(editedBowie));
    }

    @Test
    public void getCatList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCatList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{cats=" + addressBook.getCatList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose cats list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Cat> cats = FXCollections.observableArrayList();

        AddressBookStub(Collection<Cat> cats) {
            this.cats.setAll(cats);
        }

        @Override
        public ObservableList<Cat> getCatList() {
            return cats;
        }
    }

}

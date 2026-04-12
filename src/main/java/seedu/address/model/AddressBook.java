package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.UniqueCatList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCat comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCatList cats;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cats = new UniqueCatList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Cats in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the cat list with {@code cats}.
     * {@code cats} must not contain duplicate cats.
     */
    public void setCats(List<Cat> cats) {
        this.cats.setCats(cats);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCats(newData.getCatList());
    }

    //// cat-level operations

    /**
     * Returns true if a cat with the same identity as {@code cat} exists in the address book.
     */
    public boolean hasCat(Cat cat) {
        requireNonNull(cat);
        return cats.contains(cat);
    }

    /**
     * Adds a cat to the address book.
     * The cat must not already exist in the address book.
     */
    public void addCat(Cat cat) {
        cats.add(cat);
    }

    /**
     * Replaces the given cat {@code target} in the list with {@code editedCat}.
     * {@code target} must exist in the address book.
     * The cat identity of {@code editedCat} must not be the same as another existing cat in the address book.
     */
    public void setCat(Cat target, Cat editedCat) {
        requireNonNull(editedCat);

        cats.setCat(target, editedCat);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCat(Cat key) {
        cats.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("cats", cats)
                .toString();
    }

    @Override
    public ObservableList<Cat> getCatList() {
        return cats.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return cats.equals(otherAddressBook.cats);
    }

    @Override
    public int hashCode() {
        return cats.hashCode();
    }
}

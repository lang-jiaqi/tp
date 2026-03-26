package seedu.address.model.cat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cat.exceptions.CatNotFoundException;
import seedu.address.model.cat.exceptions.DuplicateCatException;
/**
 * A list of cats that enforces uniqueness between its elements and does not allow nulls.
 * A cat is considered unique by comparing using {@code Cat#isSameCat(Cat)}. As such, adding and updating of
 * cats uses Cat#isSameCat(Cat) for equality so as to ensure that the cat being added or updated is
 * unique in terms of identity in the UniqueCatList. However, the removal of a cat uses Cat#equals(Object) so
 * as to ensure that the cat with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Cat#isSameCat(Cat)
 */
public class UniqueCatList implements Iterable<Cat> {

    private final ObservableList<Cat> internalList = FXCollections.observableArrayList();
    private final ObservableList<Cat> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent cat as the given argument.
     */
    public boolean contains(Cat toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCat);
    }

    /**
     * Adds a cat to the list.
     * The cat must not already exist in the list.
     */
    public void add(Cat toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCatException();
        }
        internalList.add(toAdd);
        assert contains(toAdd) : "Cat should be present in list immediately after add";
    }

    /**
     * Replaces the cat {@code target} in the list with {@code editedCat}.
     * {@code target} must exist in the list.
     * The cat identity of {@code editedCat} must not be the same as another existing cat in the list.
     */
    public void setCat(Cat target, Cat editedCat) {
        requireAllNonNull(target, editedCat);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CatNotFoundException();
        }

        if (!target.isSameCat(editedCat) && contains(editedCat)) {
            throw new DuplicateCatException();
        }

        internalList.set(index, editedCat);
    }

    /**
     * Removes the equivalent cat from the list.
     * The cat must exist in the list.
     */
    public void remove(Cat toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CatNotFoundException();
        }
        assert !contains(toRemove) : "Cat should not be present in list after removal";
    }

    public void setCats(UniqueCatList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cats}.
     * {@code cats} must not contain duplicate cats.
     */
    public void setCats(List<Cat> cats) {
        requireAllNonNull(cats);
        if (!catsAreUnique(cats)) {
            throw new DuplicateCatException();
        }

        internalList.setAll(cats);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cat> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Cat> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueCatList)) {
            return false;
        }

        UniqueCatList otherUniqueCatList = (UniqueCatList) other;
        return internalList.equals(otherUniqueCatList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code cats} contains only unique cats.
     */
    private boolean catsAreUnique(List<Cat> cats) {
        for (int i = 0; i < cats.size() - 1; i++) {
            for (int j = i + 1; j < cats.size(); j++) {
                if (cats.get(i).isSameCat(cats.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

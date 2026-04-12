package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cat.Cat;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Cat> PREDICATE_SHOW_ALL_CATS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a cat with the same identity as {@code cat} exists in the address book.
     */
    boolean hasCat(Cat cat);

    /**
     * Deletes the given cat.
     * The cat must exist in the address book.
     */
    void deleteCat(Cat target);

    /**
     * Adds the given cat.
     * {@code cat} must not already exist in the address book.
     */
    void addCat(Cat cat);

    /**
     * Replaces the given cat {@code target} with {@code editedCat}.
     * {@code target} must exist in the address book.
     * The cat identity of {@code editedCat} must not be the same as another existing cat in the address book.
     */
    void setCat(Cat target, Cat editedCat);

    /** Returns an unmodifiable view of the filtered cat list */
    ObservableList<Cat> getFilteredCatList();

    /**
     * Updates the filter of the filtered cat list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCatList(Predicate<Cat> predicate);

    /**
     * Saves a snapshot of the current address book state so it can be restored by {@link #undoLastChange()}.
     * Should be called before executing any command that modifies data (add, delete, update, attach).
     */
    void saveUndoState();

    /**
     * Clears the saved undo snapshot. Should be called after executing a destructive non-reversible
     * command (e.g. clear), so that a subsequent {@code undo} will have no effect.
     * Read-only commands leave the undo state untouched.
     */
    void clearUndoState();

    /**
     * Returns true if there is a saved state that can be restored by {@link #undoLastChange()}.
     */
    boolean canUndo();

    /**
     * Restores the address book to the state saved by the most recent {@link #saveUndoState()} call,
     * then clears the saved state so that consecutive undos have no effect.
     */
    void undoLastChange();
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cat.Cat;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Cat> filteredCats;

    /** Snapshot of the address book taken before the last undoable command, or {@code null} if none. */
    private ReadOnlyAddressBook previousAddressBook = null;

    /** Whether there is a valid undo snapshot available. */
    private boolean hasUndoableState = false;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCats = new FilteredList<>(this.addressBook.getCatList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasCat(Cat cat) {
        requireNonNull(cat);
        return addressBook.hasCat(cat);
    }

    @Override
    public void deleteCat(Cat target) {
        addressBook.removeCat(target);
    }

    @Override
    public void addCat(Cat cat) {
        addressBook.addCat(cat);
        updateFilteredCatList(PREDICATE_SHOW_ALL_CATS);
    }

    @Override
    public void setCat(Cat target, Cat editedCat) {
        requireAllNonNull(target, editedCat);

        addressBook.setCat(target, editedCat);
    }

    //=========== Filtered Cat List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Cat} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Cat> getFilteredCatList() {
        return filteredCats;
    }

    @Override
    public void updateFilteredCatList(Predicate<Cat> predicate) {
        requireNonNull(predicate);
        filteredCats.setPredicate(predicate);
    }

    //=========== Undo Support ================================================================================

    @Override
    public void saveUndoState() {
        previousAddressBook = new AddressBook(addressBook);
        hasUndoableState = true;
    }

    @Override
    public void clearUndoState() {
        previousAddressBook = null;
        hasUndoableState = false;
    }

    @Override
    public boolean canUndo() {
        return hasUndoableState;
    }

    @Override
    public void undoLastChange() {
        assert hasUndoableState && previousAddressBook != null;
        setAddressBook(previousAddressBook);
        updateFilteredCatList(PREDICATE_SHOW_ALL_CATS);
        previousAddressBook = null;
        hasUndoableState = false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredCats.equals(otherModelManager.filteredCats);
    }

}

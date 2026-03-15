package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCats.BOWIE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.cat.Cat;
import seedu.address.testutil.CatBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_catAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCatAdded modelStub = new ModelStubAcceptingCatAdded();
        Cat validCat = new CatBuilder().build();

        CommandResult commandResult = new AddCommand(validCat).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validCat)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCat), modelStub.catsAdded);
    }

    @Test
    public void execute_duplicateCat_throwsCommandException() {
        Cat validCat = new CatBuilder().build();
        AddCommand addCommand = new AddCommand(validCat);
        ModelStub modelStub = new ModelStubWithCat(validCat);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CAT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateCatCaseInsensitive_throwsCommandException() {
        Cat validCat = new CatBuilder().withName("Bowie").build();
        Cat duplicateWithDifferentCase = new CatBuilder().withName("BOWIE").build();
        AddCommand addCommand = new AddCommand(duplicateWithDifferentCase);
        ModelStub modelStub = new ModelStubWithCat(validCat);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CAT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Cat alice = new CatBuilder().withName("Alice").build();
        Cat bob = new CatBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different cat -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(BOWIE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + BOWIE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCat(Cat cat) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCat(Cat cat) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCat(Cat target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCat(Cat target, Cat editedCat) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Cat> getFilteredCatList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCatList(Predicate<Cat> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single cat.
     */
    private class ModelStubWithCat extends ModelStub {
        private final Cat cat;

        ModelStubWithCat(Cat cat) {
            requireNonNull(cat);
            this.cat = cat;
        }

        @Override
        public boolean hasCat(Cat cat) {
            requireNonNull(cat);
            return this.cat.isSameCat(cat);
        }
    }

    /**
     * A Model stub that always accept the cat being added.
     */
    private class ModelStubAcceptingCatAdded extends ModelStub {
        final ArrayList<Cat> catsAdded = new ArrayList<>();

        @Override
        public boolean hasCat(Cat cat) {
            requireNonNull(cat);
            return catsAdded.stream().anyMatch(cat::isSameCat);
        }

        @Override
        public void addCat(Cat cat) {
            requireNonNull(cat);
            catsAdded.add(cat);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

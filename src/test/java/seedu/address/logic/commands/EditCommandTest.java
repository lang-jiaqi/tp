package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCatAtIndex;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditCatDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.Cat;
import seedu.address.testutil.CatBuilder;
import seedu.address.testutil.EditCatDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Cat editedCat = new CatBuilder().build();
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder(editedCat).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCat(model.getFilteredCatList().get(0), editedCat);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCat = Index.fromOneBased(model.getFilteredCatList().size());
        Cat lastCat = model.getFilteredCatList().get(indexLastCat.getZeroBased());

        CatBuilder catInList = new CatBuilder(lastCat);
        Cat editedCat = catInList.withName(VALID_NAME_BOB).withTraits(VALID_TRAIT_FLUFFY).build();

        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTraits(VALID_TRAIT_FLUFFY).build();
        EditCommand editCommand = new EditCommand(indexLastCat, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCat(lastCat, editedCat);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCatDescriptor());
        Cat editedCat = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCatAtIndex(model, INDEX_FIRST_PERSON);

        Cat catInFilteredList = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());
        Cat editedCat = new CatBuilder(catInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditCatDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCat(model.getFilteredCatList().get(0), editedCat);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCatUnfilteredList_failure() {
        Cat firstCat = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder(firstCat).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CAT);
    }

    @Test
    public void execute_duplicateCatFilteredList_failure() {
        showCatAtIndex(model, INDEX_FIRST_PERSON);

        Cat catInList = model.getAddressBook().getCatList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditCatDescriptorBuilder(catInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CAT);
    }

    @Test
    public void execute_invalidCatIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCatList().size() + 1);
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidCatIndexFilteredList_failure() {
        showCatAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCatList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCatDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editLocation_success() {
        Cat catToEdit = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());
        Cat editedCat = new CatBuilder(catToEdit).withLocation(VALID_LOCATION_BOB).build();

        EditCatDescriptor descriptor = new EditCatDescriptorBuilder().withLocation(VALID_LOCATION_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCat(catToEdit, editedCat);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditCatDescriptor copyDescriptor = new EditCatDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCatDescriptor editCatDescriptor = new EditCatDescriptor();
        EditCommand editCommand = new EditCommand(index, editCatDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editCatDescriptor="
                + editCatDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}

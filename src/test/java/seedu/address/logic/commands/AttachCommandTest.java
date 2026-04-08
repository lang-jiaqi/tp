package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Name;
import seedu.address.testutil.CatBuilder;

public class AttachCommandTest {

    @TempDir
    public Path tempDir;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // ── happy path: by index ──────────────────────────────────────────────────

    /**
     * Attaching an image to a valid index in an unfiltered list should succeed
     * and update the cat's image path in the model.
     */
    @Test
    public void execute_validIndex_success() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("bowie.png"));

        Cat catToEdit = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());
        CatImage image = new CatImage(imageFile.toString());
        AttachCommand command = new AttachCommand(INDEX_FIRST_PERSON, image);

        Cat updatedCat = new CatBuilder(catToEdit).withImage(imageFile.toString()).build();
        String expectedMessage = String.format(AttachCommand.MESSAGE_ATTACH_SUCCESS,
                Messages.format(updatedCat));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCat(catToEdit, updatedCat);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // ── happy path: by name ───────────────────────────────────────────────────

    /**
     * Attaching an image using a valid cat name should succeed
     * and update the cat's image path in the model.
     */
    @Test
    public void execute_validName_success() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("bowie.png"));

        Cat catToEdit = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased()); // Bowie
        CatImage image = new CatImage(imageFile.toString());
        AttachCommand command = new AttachCommand(new Name("Bowie"), image);

        Cat updatedCat = new CatBuilder(catToEdit).withImage(imageFile.toString()).build();
        String expectedMessage = String.format(AttachCommand.MESSAGE_ATTACH_SUCCESS,
                Messages.format(updatedCat));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCat(catToEdit, updatedCat);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // ── name matching is case-insensitive ─────────────────────────────────────

    /**
     * Attaching an image using a cat name with different capitalisation (e.g. "bowie" for "Bowie")
     * should succeed, as name matching is case-insensitive.
     */
    @Test
    public void execute_nameMatchIsCaseInsensitive_success() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("bowie.png"));

        // "bowie" (all lowercase) should still match the cat named "Bowie"
        Cat catToEdit = model.getFilteredCatList().get(INDEX_FIRST_PERSON.getZeroBased());
        CatImage image = new CatImage(imageFile.toString());
        AttachCommand command = new AttachCommand(new Name("bowie"), image);

        Cat updatedCat = new CatBuilder(catToEdit).withImage(imageFile.toString()).build();
        String expectedMessage = String.format(AttachCommand.MESSAGE_ATTACH_SUCCESS,
                Messages.format(updatedCat));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCat(catToEdit, updatedCat);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // ── error: index out of bounds ────────────────────────────────────────────

    /**
     * Attaching an image using an index that exceeds the list size should throw a CommandException.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("bowie.png"));

        Index outOfBound = Index.fromOneBased(model.getFilteredCatList().size() + 1);
        AttachCommand command = new AttachCommand(outOfBound, new CatImage(imageFile.toString()));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
    }

    // ── error: file does not exist (by index) ────────────────────────────────

    /**
     * Attaching a non-existent image file using an index should throw a CommandException
     * with the file-not-found message.
     */
    @Test
    public void execute_fileNotFoundByIndex_throwsCommandException() {
        CatImage missing = new CatImage("nonexistent/path/cat.png");
        AttachCommand command = new AttachCommand(INDEX_FIRST_PERSON, missing);

        assertCommandFailure(command, model,
                String.format(AttachCommand.MESSAGE_FILE_NOT_FOUND, missing.value));
    }

    // ── error: file does not exist (by name) ─────────────────────────────────

    /**
     * Attaching a non-existent image file using a cat name should throw a CommandException
     * with the file-not-found message.
     */
    @Test
    public void execute_fileNotFoundByName_throwsCommandException() {
        CatImage missing = new CatImage("nonexistent/path/cat.png");
        AttachCommand command = new AttachCommand(new Name("Bowie"), missing);

        assertCommandFailure(command, model,
                String.format(AttachCommand.MESSAGE_FILE_NOT_FOUND, missing.value));
    }

    // ── error: cat name not in list ───────────────────────────────────────────

    /**
     * Attaching an image using a name that does not match any cat in the list should throw
     * a CommandException with the cat-not-found message.
     */
    @Test
    public void execute_catNameNotFound_throwsCommandException() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("ghost.png"));

        AttachCommand command = new AttachCommand(new Name("Ghost"), new CatImage(imageFile.toString()));

        assertCommandFailure(command, model,
                String.format(AttachCommand.MESSAGE_CAT_NOT_FOUND, "Ghost"));
    }

    // ── equals ────────────────────────────────────────────────────────────────

    /**
     * Tests the equals method: same object, same values, different index,
     * index-based vs name-based, wrong type, and null all behave correctly.
     */
    @Test
    public void equals() throws IOException {
        Path imageFile = Files.createFile(tempDir.resolve("bowie.png"));
        CatImage image = new CatImage(imageFile.toString());

        AttachCommand attachFirst = new AttachCommand(INDEX_FIRST_PERSON, image);
        AttachCommand attachSecond = new AttachCommand(INDEX_SECOND_PERSON, image);
        AttachCommand attachByName = new AttachCommand(new Name("Bowie"), image);

        // same object -> true
        assertTrue(attachFirst.equals(attachFirst));

        // same values -> true
        assertTrue(attachFirst.equals(new AttachCommand(INDEX_FIRST_PERSON, image)));

        // different index -> false
        assertFalse(attachFirst.equals(attachSecond));

        // index-based vs name-based -> false even if they resolve to the same cat,
        // because one has index=1/name=null and the other has index=null/name="Bowie"
        assertFalse(attachFirst.equals(attachByName));

        // different type -> false
        assertFalse(attachFirst.equals("notACommand"));

        // null -> false
        assertFalse(attachFirst.equals(null));
    }
}

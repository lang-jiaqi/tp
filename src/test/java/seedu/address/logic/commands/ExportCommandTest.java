package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get("export.html"));
    }

    // ── happy path: full list ─────────────────────────────────────────────────

    /**
     * Exporting the full unfiltered cat list should succeed and report the correct cat count
     * in the feedback message.
     */
    @Test
    public void execute_fullList_success() throws CommandException {
        int count = model.getFilteredCatList().size();
        CommandResult result = new ExportCommand().execute(model);

        assertTrue(result.getFeedbackToUser().contains(String.valueOf(count)));
    }

    // ── happy path: empty list ────────────────────────────────────────────────

    /**
     * Exporting an empty cat list should succeed and report zero cats in the feedback message.
     */
    @Test
    public void execute_emptyList_success() throws CommandException {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        CommandResult result = new ExportCommand().execute(emptyModel);

        assertTrue(result.getFeedbackToUser().contains("0"));
    }

    // ── filtered list exports only visible cats ───────────────────────────────

    /**
     * Exporting after filtering the list should report only the number of visible cats,
     * not the total number of cats in the address book.
     */
    @Test
    public void execute_filteredList_reportsVisibleCountOnly() throws CommandException {
        model.updateFilteredCatList(cat -> cat.getName().fullName.equals("Bowie"));
        CommandResult result = new ExportCommand().execute(model);

        assertTrue(result.getFeedbackToUser().contains("1"));
    }

    // ── output file is created ────────────────────────────────────────────────

    /**
     * Executing export should create export.html on disk.
     */
    @Test
    public void execute_createsHtmlFile() throws CommandException {
        new ExportCommand().execute(model);

        assertTrue(Files.exists(Paths.get("export.html")));
    }

    // ── model is not modified ─────────────────────────────────────────────────

    /**
     * Executing export should not modify the model's cat list.
     */
    @Test
    public void execute_doesNotModifyModel() throws CommandException {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        new ExportCommand().execute(model);

        assertEquals(expectedModel.getFilteredCatList(), model.getFilteredCatList());
    }

    // ── equals ────────────────────────────────────────────────────────────────

    /**
     * Tests the equals method: ExportCommand has no fields, so all instances are equal to each other.
     */
    @Test
    public void equals() {
        ExportCommand first = new ExportCommand();
        ExportCommand second = new ExportCommand();

        // same object -> true
        assertTrue(first.equals(first));

        // any two ExportCommand instances are equal (command has no distinguishing fields)
        assertTrue(first.equals(second));

        // different type -> false
        assertFalse(first.equals("notACommand"));

        // null -> false
        assertFalse(first.equals(null));
    }
}

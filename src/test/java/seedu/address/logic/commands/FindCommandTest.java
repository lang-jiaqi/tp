package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCats.BOWIE;
import static seedu.address.testutil.TypicalCats.LUNA;
import static seedu.address.testutil.TypicalCats.MOCHI;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.NameContainsKeywordsPredicate;

public class FindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(1, findFirstCommand);

        // null -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(null, findFirstCommand);

        // different command -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_noMatchingKeywords_noPersonFound() {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("NonExistentCatName"));
        FindCommand command = new FindCommand(predicate);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(FindCommand.MESSAGE_NO_MATCH, thrown.getMessage());
        org.junit.jupiter.api.Assertions.assertTrue(model.getFilteredCatList().isEmpty());
    }

    @Test
    public void execute_multipleKeywords_multipleCatsFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Bowie Mochi Luna");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);
        assertCommandSuccess(command, model, FindCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Arrays.asList(BOWIE, MOCHI, LUNA), model.getFilteredCatList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(
                Arrays.asList(userInput.trim().split("\\s+")));
    }

}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CATS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FindCommand.MESSAGE_NO_MATCH;
import static seedu.address.testutil.TypicalCats.MOCHI;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

public class FindCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // Predicate 1: Searches for name "first"
        CatContainsKeywordsPredicate firstPredicate =
                new CatContainsKeywordsPredicate(Collections.singletonList("first"),
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        // Predicate 2: Searches for name "second"
        CatContainsKeywordsPredicate secondPredicate =
                new CatContainsKeywordsPredicate(Collections.singletonList("second"),
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(identity(findFirstCommand).equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different predicate values -> returns false
        assertNotEquals(findSecondCommand, findFirstCommand);
    }

    @Test
    public void execute_noMatchingKeywords() {
        // Search for a name that definitely doesn't exist in TypicalCats
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("NonExistentCatName"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);

        // assertCommandSuccess verifies the command returns the expected message
        assertCommandSuccess(command, model, MESSAGE_NO_MATCH, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCatList());
    }

    @Test
    public void execute_multipleKeywords_oneCatFound() {
        String expectedMessage = String.format(MESSAGE_CATS_LISTED_OVERVIEW, 1);
        // Search for name "Mochi" with trait "White"
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                List.of("Mochi"), Collections.emptyList(),
                List.of("White"), Collections.emptyList());

        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(MOCHI), model.getFilteredCatList());
    }

    @Test
    public void toStringMethod() {
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                List.of("Mochi"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        FindCommand findCommand = new FindCommand(predicate);

        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    private <T> T identity(T value) {
        return value;
    }

}

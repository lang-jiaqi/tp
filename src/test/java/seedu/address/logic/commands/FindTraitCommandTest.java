package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CATS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCats.LUNA;
import static seedu.address.testutil.TypicalCats.MOCHI;
import static seedu.address.testutil.TypicalCats.PIPI;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.TraitContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTraitCommand}.
 */
public class FindTraitCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TraitContainsKeywordsPredicate firstPredicate =
                new TraitContainsKeywordsPredicate(Collections.singletonList("first"));
        TraitContainsKeywordsPredicate secondPredicate =
                new TraitContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTraitCommand findFirstCommand = new FindTraitCommand(firstPredicate);
        FindTraitCommand findSecondCommand = new FindTraitCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTraitCommand findFirstCommandCopy = new FindTraitCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCatsFound() {
        String expectedMessage = String.format(MESSAGE_CATS_LISTED_OVERVIEW, 0);
        TraitContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTraitCommand command = new FindTraitCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCatList());
    }

    @Test
    public void execute_multipleKeywords_multipleCatsFound() {
        // TypicalCats: Pipi has 'Calico', Mochie has 'White' and Luna has 'Black'
        String expectedMessage = String.format(MESSAGE_CATS_LISTED_OVERVIEW, 3);
        TraitContainsKeywordsPredicate predicate = preparePredicate("Calico Fluffy Black");
        FindTraitCommand command = new FindTraitCommand(predicate);
        expectedModel.updateFilteredCatList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MOCHI, LUNA, PIPI), model.getFilteredCatList());
    }

    /**
     * Parses {@code userInput} into a {@code TraitContainsKeywordsPredicate}.
     */
    private TraitContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TraitContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

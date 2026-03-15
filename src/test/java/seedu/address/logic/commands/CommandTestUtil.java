package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditCatDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy";
    public static final String VALID_NAME_BOB = "Bob";
    public static final String VALID_LOCATION_AMY = "Utown";
    public static final String VALID_LOCATION_BOB = "Science";
    public static final String VALID_HEALTH_AMY = "Vaccinated";
    public static final String VALID_HEALTH_BOB = "Unknown";
    public static final String VALID_TRAIT_ORANGE = "Orange";
    public static final String VALID_TRAIT_FLUFFY = "Fluffy";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String LOCATION_DESC_AMY = " " + PREFIX_LOCATION + VALID_LOCATION_AMY;
    public static final String LOCATION_DESC_BOB = " " + PREFIX_LOCATION + VALID_LOCATION_BOB;
    public static final String HEALTH_DESC_AMY = " " + PREFIX_HEALTH + VALID_HEALTH_AMY;
    public static final String HEALTH_DESC_BOB = " " + PREFIX_HEALTH + VALID_HEALTH_BOB;
    public static final String TRAIT_DESC_ORANGE = " " + PREFIX_TRAIT + VALID_TRAIT_ORANGE;
    public static final String TRAIT_DESC_FLUFFY = " " + PREFIX_TRAIT + VALID_TRAIT_FLUFFY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION; // empty string not allowed
    public static final String INVALID_TRAIT_DESC = " " + PREFIX_TRAIT + " "; // blank not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCatDescriptor DESC_AMY;
    public static final EditCommand.EditCatDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCatDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTraits(VALID_TRAIT_ORANGE).withLocation(VALID_LOCATION_AMY)
                .withHealth(VALID_HEALTH_AMY).build();
        DESC_BOB = new EditCatDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTraits(VALID_TRAIT_FLUFFY, VALID_TRAIT_ORANGE).withLocation(VALID_LOCATION_BOB)
                .withHealth(VALID_HEALTH_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered cat list and selected cat in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Cat> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCatList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCatList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the cat at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCatAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCatList().size());

        Cat cat = model.getFilteredCatList().get(targetIndex.getZeroBased());
        final String[] splitName = cat.getName().fullName.split("\\s+");
        model.updateFilteredCatList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCatList().size());
    }

}

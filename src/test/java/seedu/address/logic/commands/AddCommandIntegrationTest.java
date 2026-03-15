package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCats.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cat.Cat;
import seedu.address.testutil.CatBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newCat_success() {
        Cat validCat = new CatBuilder().withName("Whiskers").withTraits("Striped")
                .withLocation("Kent Ridge").withHealth("Unknown").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addCat(validCat);

        assertCommandSuccess(new AddCommand(validCat), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validCat)),
                expectedModel);
    }

    @Test
    public void execute_duplicateCat_throwsCommandException() {
        Cat catInList = model.getAddressBook().getCatList().get(0);
        assertCommandFailure(new AddCommand(catInList), model,
                AddCommand.MESSAGE_DUPLICATE_CAT);
    }

}

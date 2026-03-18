package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.testutil.EditCatDescriptorBuilder;

public class EditCatDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCatDescriptor descriptorWithSameValues = new EditCatDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCatDescriptor editedAmy = new EditCatDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different location -> returns false
        editedAmy = new EditCatDescriptorBuilder(DESC_AMY).withLocation(VALID_LOCATION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different traits -> returns false
        editedAmy = new EditCatDescriptorBuilder(DESC_AMY).withTraits(VALID_TRAIT_FLUFFY).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCatDescriptor editCatDescriptor = new EditCatDescriptor();
        String expected = EditCatDescriptor.class.getCanonicalName() + "{name="
                + editCatDescriptor.getName().orElse(null) + ", traits="
                + editCatDescriptor.getTraits().orElse(null) + ", location="
                + editCatDescriptor.getLocation().orElse(null) + ", health="
                + editCatDescriptor.getHealth().orElse(null) + "}";
        assertEquals(expected, editCatDescriptor.toString());
    }
}

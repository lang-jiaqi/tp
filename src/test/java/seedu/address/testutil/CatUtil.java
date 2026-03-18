package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Trait;

/**
 * A utility class for Cat.
 */
public class CatUtil {

    /**
     * Returns an add command string for adding the {@code cat}.
     */
    public static String getAddCommand(Cat cat) {
        return AddCommand.COMMAND_WORD + " " + getCatDetails(cat);
    }

    /**
     * Returns the part of command string for the given {@code cat}'s details.
     */
    public static String getCatDetails(Cat cat) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + cat.getName().fullName + " ");
        cat.getTraits().forEach(
            s -> sb.append(PREFIX_TRAIT + s.traitName + " ")
        );
        sb.append(PREFIX_LOCATION + cat.getLocation().value + " ");
        sb.append(PREFIX_HEALTH + cat.getHealth().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCatDescriptor}'s details.
     */
    public static String getEditCatDescriptorDetails(EditCatDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getTraits().isPresent()) {
            List<Trait> traits = descriptor.getTraits().get();
            if (traits.isEmpty()) {
                sb.append(PREFIX_TRAIT);
            } else {
                traits.forEach(s -> sb.append(PREFIX_TRAIT).append(s.traitName).append(" "));
            }
        }
        descriptor.getLocation().ifPresent(location -> sb.append(PREFIX_LOCATION).append(location.value).append(" "));
        descriptor.getHealth().ifPresent(health -> sb.append(PREFIX_HEALTH).append(health.value).append(" "));
        return sb.toString();
    }
}

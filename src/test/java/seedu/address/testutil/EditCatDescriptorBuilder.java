package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * A utility class to help with building EditCatDescriptor objects.
 */
public class EditCatDescriptorBuilder {

    private EditCatDescriptor descriptor;

    public EditCatDescriptorBuilder() {
        descriptor = new EditCatDescriptor();
    }

    public EditCatDescriptorBuilder(EditCatDescriptor descriptor) {
        this.descriptor = new EditCatDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCatDescriptor} with fields containing {@code cat}'s details
     */
    public EditCatDescriptorBuilder(Cat cat) {
        descriptor = new EditCatDescriptor();
        descriptor.setName(cat.getName());
        descriptor.setTraits(cat.getTraits());
        descriptor.setLocation(cat.getLocation());
        descriptor.setHealth(cat.getHealth());
    }

    /**
     * Sets the {@code Name} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code traits} into a {@code List<Trait>} and set it to the {@code EditCatDescriptor}
     * that we are building.
     */
    public EditCatDescriptorBuilder withTraits(String... traits) {
        List<Trait> traitList = Arrays.stream(traits).map(Trait::new).collect(Collectors.toList());
        descriptor.setTraits(traitList);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Health} of the {@code EditCatDescriptor} that we are building.
     */
    public EditCatDescriptorBuilder withHealth(String health) {
        descriptor.setHealth(new Health(health));
        return this;
    }

    public EditCatDescriptor build() {
        return descriptor;
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CATS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * Updates the details of an existing cat in the cat notebook.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the cat identified "
            + "by its name or index number in the displayed cat list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CAT_NAME [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TRAIT + "TRAIT]... "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_HEALTH + "HEALTH_STATUS]\n"
            + "   OR: INDEX (must be a positive integer) [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TRAIT + "TRAIT]... "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_HEALTH + "HEALTH_STATUS]\n"
            + "Example: " + COMMAND_WORD + " Snowy " + PREFIX_LOCATION + "utown\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_LOCATION + "PGPR";

    public static final String MESSAGE_EDIT_CAT_SUCCESS = "Updated Cat: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_CAT = "This cat already exists in the cat notebook.";
    public static final String MESSAGE_INVALID_CAT_NAME = "The cat name provided is invalid";

    private final Index index;
    private final Name targetName;
    private final EditCatDescriptor editCatDescriptor;

    /**
     * @param index             of the cat in the filtered cat list to update
     * @param editCatDescriptor details to update the cat with
     */
    public UpdateCommand(Index index, EditCatDescriptor editCatDescriptor) {
        requireNonNull(index);
        requireNonNull(editCatDescriptor);

        this.index = index;
        this.targetName = null;
        this.editCatDescriptor = new EditCatDescriptor(editCatDescriptor);
    }

    /**
     * @param targetName        name of the cat in the address book to update
     * @param editCatDescriptor details to update the cat with
     */
    public UpdateCommand(Name targetName, EditCatDescriptor editCatDescriptor) {
        requireNonNull(targetName);
        requireNonNull(editCatDescriptor);

        this.index = null;
        this.targetName = targetName;
        this.editCatDescriptor = new EditCatDescriptor(editCatDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> lastShownList = model.getFilteredCatList();

        Cat catToEdit;
        if (index != null) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            catToEdit = lastShownList.get(index.getZeroBased());
        } else {
            catToEdit = model.getAddressBook().getCatList().stream()
                    .filter(cat -> cat.getName().fullName.equalsIgnoreCase(targetName.fullName))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(MESSAGE_INVALID_CAT_NAME));
        }
        Cat editedCat = createEditedCat(catToEdit, editCatDescriptor);

        if (!catToEdit.isSameCat(editedCat) && model.hasCat(editedCat)) {
            throw new CommandException(MESSAGE_DUPLICATE_CAT);
        }

        model.setCat(catToEdit, editedCat);
        model.updateFilteredCatList(PREDICATE_SHOW_ALL_CATS);
        return new CommandResult(String.format(MESSAGE_EDIT_CAT_SUCCESS, Messages.format(editedCat)));
    }

    /**
     * Returns a preview of the cat that would result from this update, without applying the change.
     * Used for confirmation dialogs before executing the update.
     *
     * @param model the current model state
     * @return the cat that would result from applying this update
     * @throws CommandException if the target cat cannot be found or the update would create a duplicate
     */
    public Cat getEditedCatPreview(Model model) throws CommandException {
        requireNonNull(model);
        List<Cat> lastShownList = model.getFilteredCatList();

        Cat catToEdit;
        if (index != null) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            catToEdit = lastShownList.get(index.getZeroBased());
        } else {
            catToEdit = model.getAddressBook().getCatList().stream()
                    .filter(cat -> cat.getName().fullName.equalsIgnoreCase(targetName.fullName))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(MESSAGE_INVALID_CAT_NAME));
        }
        Cat editedCat = createEditedCat(catToEdit, editCatDescriptor);

        if (!catToEdit.isSameCat(editedCat) && model.hasCat(editedCat)) {
            throw new CommandException(MESSAGE_DUPLICATE_CAT);
        }

        return editedCat;
    }

    /**
     * Creates and returns a {@code Cat} with the details of {@code catToEdit}
     * edited with {@code editCatDescriptor}.
     */
    private static Cat createEditedCat(Cat catToEdit, EditCatDescriptor editCatDescriptor) {
        assert catToEdit != null;

        Name updatedName = editCatDescriptor.getName().orElse(catToEdit.getName());
        List<Trait> updatedTraits = editCatDescriptor.getTraits().orElse(catToEdit.getTraits());
        Location updatedLocation = editCatDescriptor.getLocation().orElse(catToEdit.getLocation());
        Health updatedHealth = editCatDescriptor.getHealth().orElse(catToEdit.getHealth());
        return new Cat(updatedName, updatedTraits, updatedLocation, updatedHealth, catToEdit.getImage());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return Objects.equals(index, otherUpdateCommand.index)
                && Objects.equals(targetName, otherUpdateCommand.targetName)
                && editCatDescriptor.equals(otherUpdateCommand.editCatDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCatDescriptor", editCatDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the cat with. Each non-empty field value will replace the
     * corresponding field value of the cat.
     */
    public static class EditCatDescriptor {
        private Name name;
        private List<Trait> traits;
        private Location location;
        private Health health;

        /**
         * Creates an empty {@code EditCatDescriptor} with no fields set.
         */
        public EditCatDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code traits} is used internally.
         */
        public EditCatDescriptor(EditCatDescriptor toCopy) {
            setName(toCopy.name);
            setTraits(toCopy.traits);
            setLocation(toCopy.location);
            setHealth(toCopy.health);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, traits, location, health);
        }

        /**
         * Sets the {@code Name} to update.
         */
        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Returns the {@code Name} to update, or empty if not set.
         */
        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the {@code traits} list to update.
         * A defensive copy is stored internally.
         */
        public void setTraits(List<Trait> traits) {
            this.traits = (traits != null) ? new ArrayList<>(traits) : null;
        }

        /**
         * Returns an unmodifiable trait list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code traits} is null.
         */
        public Optional<List<Trait>> getTraits() {
            return (traits != null) ? Optional.of(Collections.unmodifiableList(traits)) : Optional.empty();
        }

        /**
         * Sets the {@code Location} to update.
         */
        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         * Returns the {@code Location} to update, or empty if not set.
         */
        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets the {@code Health} status to update.
         */
        public void setHealth(Health health) {
            this.health = health;
        }

        /**
         * Returns the {@code Health} status to update, or empty if not set.
         */
        public Optional<Health> getHealth() {
            return Optional.ofNullable(health);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCatDescriptor)) {
                return false;
            }

            EditCatDescriptor otherEditCatDescriptor = (EditCatDescriptor) other;
            return Objects.equals(name, otherEditCatDescriptor.name)
                    && Objects.equals(traits, otherEditCatDescriptor.traits)
                    && Objects.equals(location, otherEditCatDescriptor.location)
                    && Objects.equals(health, otherEditCatDescriptor.health);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("traits", traits)
                    .add("location", location)
                    .add("health", health)
                    .toString();
        }
    }
}

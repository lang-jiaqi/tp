package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImage;
import seedu.address.model.cat.Name;

/**
 * Attaches an image to an existing cat identified by index or name.
 * AI-generated.
 */
public class AttachCommand extends Command {

    public static final String COMMAND_WORD = "attach";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Attaches an image to the cat identified by index or name.\n"
            + "Parameters: INDEX IMAGE_PATH or CAT_NAME IMAGE_PATH\n"
            + "To reset to auto-detection: INDEX --reset or CAT_NAME --reset\n"
            + "Example: " + COMMAND_WORD + " 1 images/bowie.png\n"
            + "Example: " + COMMAND_WORD + " Bowie images/bowie.png\n"
            + "Example: " + COMMAND_WORD + " 1 --reset\n"
            + "Example: " + COMMAND_WORD + " Bowie --reset";

    public static final String MESSAGE_ATTACH_SUCCESS = "Image attached to cat: %1$s";
    public static final String MESSAGE_RESET_SUCCESS = "Image reset for cat: %1$s";
    public static final String MESSAGE_CAT_NOT_FOUND = "No cat with the name '%1$s' found.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Image file not found: %1$s";
    public static final String MESSAGE_FILE_FORMAT_INCORRECT = "Image file does not have the correct format: %1$s. " +
            "Please use .jpg, .png, or .jpeg file.";

    private final Index index;
    private final Name targetName;
    private final CatImage image;
    private final boolean reset;

    /**
     * Creates an AttachCommand to attach {@code image} to the cat at {@code index}.
     * AI-generated.
     */
    public AttachCommand(Index index, CatImage image) {
        requireNonNull(index);
        requireNonNull(image);
        this.index = index;
        this.targetName = null;
        this.image = image;
        this.reset = false;
    }

    /**
     * Creates an AttachCommand to attach {@code image} to the cat with {@code targetName}.
     * AI-generated.
     */
    public AttachCommand(Name targetName, CatImage image) {
        requireNonNull(targetName);
        requireNonNull(image);
        this.index = null;
        this.targetName = targetName;
        this.image = image;
        this.reset = false;
    }

    /**
     * Creates an AttachCommand that resets the image of the cat at {@code index}
     * back to auto-detection.
     */
    public AttachCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.targetName = null;
        this.image = null;
        this.reset = true;
    }

    /**
     * Creates an AttachCommand that resets the image of the cat with {@code targetName}
     * back to auto-detection.
     */
    public AttachCommand(Name targetName) {
        requireNonNull(targetName);
        this.index = null;
        this.targetName = targetName;
        this.image = null;
        this.reset = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!reset) {
            if (!new File(image.value).exists()) {
                throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, image.value));
            }
            String lower = image.value.toLowerCase();
            if (!lower.endsWith(".jpg") && !lower.endsWith(".jpeg") && !lower.endsWith(".png")) {
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_INCORRECT, image.value));
            }
        }

        List<Cat> lastShownList = model.getFilteredCatList();

        Cat catToEdit;
        if (index != null) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CAT_DISPLAYED_INDEX);
            }
            catToEdit = lastShownList.get(index.getZeroBased());
        } else {
            Optional<Cat> match = model.getAddressBook().getCatList().stream()
                    .filter(cat -> cat.getName().fullName.equalsIgnoreCase(targetName.fullName))
                    .findFirst();
            if (match.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_CAT_NOT_FOUND, targetName.fullName));
            }
            catToEdit = match.get();
        }

        CatImage newImage = reset ? new CatImage(CatImage.DEFAULT_VALUE) : image;
        Cat updatedCat = new Cat(catToEdit.getName(), catToEdit.getTraits(),
                catToEdit.getLocation(), catToEdit.getHealth(), newImage);
        model.setCat(catToEdit, updatedCat);

        String messageTemplate = reset ? MESSAGE_RESET_SUCCESS : MESSAGE_ATTACH_SUCCESS;
        return new CommandResult(String.format(messageTemplate, Messages.format(updatedCat)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttachCommand)) {
            return false;
        }

        AttachCommand otherAttachCommand = (AttachCommand) other;
        return java.util.Objects.equals(index, otherAttachCommand.index)
                && java.util.Objects.equals(targetName, otherAttachCommand.targetName)
                && java.util.Objects.equals(image, otherAttachCommand.image)
                && reset == otherAttachCommand.reset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("targetName", targetName)
                .add("image", image)
                .add("reset", reset)
                .toString();
    }
}

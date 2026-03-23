package seedu.address.ui;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Trait;


/**
 * An UI component that displays information of a {@code Cat}.
 */
public class CatCard extends UiPart<Region> {

    private static final String FXML = "CatListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Cat cat;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label catLocation;
    @FXML
    private Label health;
    @FXML
    private FlowPane traits;
    @FXML
    private ImageView catImage;

    /**
     * Creates a {@code CatCard} with the given {@code Cat} and index to display.
     *
     * @param cat            the cat to display.
     * @param displayedIndex the 1-based index shown in the list.
     */
    public CatCard(Cat cat, int displayedIndex) {
        super(FXML);
        this.cat = cat;
        id.setText(displayedIndex + ". ");
        name.setText(cat.getName().fullName);
        catLocation.setText(cat.getLocation().value);
        health.setText(cat.getHealth().value);
        List<Trait> traitSet = cat.getTraits();
        int totalTraits = traitSet.size();
        int currentCount = 0;

        for (Trait trait : traitSet) {
            currentCount++;
            String traitName = trait.traitName;

            // Add a comma and space if this is not the last trait
            if (currentCount < totalTraits) {
                traitName += ", ";
            }

            traits.getChildren().add(new Label(traitName));
        }

        File imageFile = resolveImageFile(cat);
        if (imageFile != null) {
            catImage.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    /**
     * Resolves the image file for the given cat.
     * Uses the explicit image path if set; otherwise tries to auto-detect a matching
     * file in the images/ folder by cat name (e.g. "Bowie" matches images/Bowie.png).
     * Returns null if no image file can be found.
     * AI-generated.
     */
    private File resolveImageFile(Cat cat) {
        // 1. Explicit path set — use it directly
        if (cat.getImage().hasImage()) {
            File f = new File(cat.getImage().value);
            return f.exists() ? f : null;
        }

        // 2. Auto-detect: try images/{name}.ext and images/{name_lowercase}.ext
        String[] extensions = {"png", "jpg", "jpeg"};
        String catName = cat.getName().fullName;

        for (String ext : extensions) {
            File f = new File("images/" + catName + "." + ext);
            if (f.exists()) {
                return f;
            }
            File fLower = new File("images/" + catName.toLowerCase() + "." + ext);
            if (fLower.exists()) {
                return fLower;
            }
        }

        return null;
    }
}

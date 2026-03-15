package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        for (Trait trait : cat.getTraits()) {
            traits.getChildren().add(new Label(trait.traitName));
        }
    }
}

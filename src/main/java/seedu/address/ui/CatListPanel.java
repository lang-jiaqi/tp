package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cat.Cat;

/**
 * Panel containing the sidebar list of cats.
 */
public class CatListPanel extends UiPart<Region> {
    private static final String FXML = "CatListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CatListPanel.class);

    @FXML
    private ListView<Cat> catListView;

    /**
     * Creates a {@code CatListPanel} with no-op selection and empty-list callbacks.
     */
    public CatListPanel(ObservableList<Cat> catList) {
        this(catList, cat -> { }, () -> { });
    }

    /**
     * Creates a {@code CatListPanel} that fires {@code onCatSelected} whenever
     * the user selects a different cat.
     */
    public CatListPanel(ObservableList<Cat> catList, Consumer<Cat> onCatSelected) {
        this(catList, onCatSelected, () -> { });
    }

    /**
     * Creates a {@code CatListPanel} that fires {@code onCatSelected} when a cat is selected
     * and {@code onListEmpty} when the list becomes empty.
     */
    public CatListPanel(ObservableList<Cat> catList, Consumer<Cat> onCatSelected, Runnable onListEmpty) {
        super(FXML);
        catListView.setItems(catList);
        catListView.setCellFactory(listView -> new CatListViewCell());

        // Disable mouse-based selection — navigation is via keyboard only
        catListView.addEventFilter(MouseEvent.MOUSE_PRESSED, javafx.event.Event::consume);

        catListView.getSelectionModel().selectedItemProperty().addListener((obs, oldCat, newCat) -> {
            if (newCat != null) {
                onCatSelected.accept(newCat);
            }
        });

        // Select first cat on initial load — deferred so the scene has fully laid out
        // before displayCat() is called (avoids zero-width rendering bug on first load)
        if (!catList.isEmpty()) {
            Platform.runLater(() -> catListView.getSelectionModel().selectFirst());
        }

        // Re-select first item if selection is lost after a list change (e.g. find/filter);
        // clear the detail panel when the list becomes empty.
        catList.addListener((javafx.collections.ListChangeListener<Cat>) change -> {
            if (catList.isEmpty()) {
                onListEmpty.run();
            } else if (catListView.getSelectionModel().getSelectedItem() == null) {
                catListView.getSelectionModel().selectFirst();
            }
        });
    }

    /** Moves the selection one item down. */
    public void selectNext() {
        catListView.getSelectionModel().selectNext();
        catListView.scrollTo(catListView.getSelectionModel().getSelectedIndex());
    }

    /** Moves the selection one item up. */
    public void selectPrevious() {
        catListView.getSelectionModel().selectPrevious();
        catListView.scrollTo(catListView.getSelectionModel().getSelectedIndex());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cat}
     * using a {@code CatSidebarCard}.
     */
    class CatListViewCell extends ListCell<Cat> {
        @Override
        protected void updateItem(Cat cat, boolean empty) {
            super.updateItem(cat, empty);
            if (empty || cat == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CatSidebarCard(cat, getIndex() + 1).getRoot());
            }
        }
    }
}

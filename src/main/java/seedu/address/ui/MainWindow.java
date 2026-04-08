package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Cat;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String UPDATE_CONFIRMATION_HEADER =
            "Are you sure you want to update this cat entry to: ";
    private static final String DELETE_CONFIRMATION_HEADER =
            "Are you sure you want to delete this cat entry: ";
    private static final String CLEAR_CONFIRMATION_HEADER =
            "Are you sure you want to clear all cat entries? "
                    + "Note that this is a system-level operation and it CANNOT be undone!";
    private static final String CLEAR_CONFIRMATION_ENTRIES = "(%d entries will be removed)";
    private static final String EXPORT_OVERWRITE_HEADER = "%s already exists. Overwrite it?";
    private static final String UNDO_CONFIRMATION_HEADER =
            "Are you sure you want to undo the previous action?";
    private static final String CONFIRMATION_HINT = "\n\nPress Enter to confirm, Esc to cancel.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CatListPanel catListPanel;
    private CatDetailPanel catDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private Label appHeaderLabel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane catListPanelPlaceholder;

    @FXML
    private StackPane catDetailPanelPlaceholder;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        Font momoFont = Font.loadFont(
                getClass().getResourceAsStream("/view/MomoSignature-Regular.ttf"), 14);
        if (momoFont != null) {
            String family = momoFont.getFamily();
            Platform.runLater(() -> appHeaderLabel.setStyle(
                    "-fx-font-family: '" + family + "';"
                    + "-fx-font-size: 56px;"
                    + "-fx-font-weight: bold;"
            ));
        }

        catDetailPanel = new CatDetailPanel();
        catDetailPanelPlaceholder.getChildren().add(catDetailPanel.getRoot());

        catListPanel = new CatListPanel(logic.getFilteredCatList(),
                cat -> catDetailPanel.displayCat(cat),
                catDetailPanel::clearDisplay);
        catListPanelPlaceholder.getChildren().add(catListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // UP/DOWN in the command box navigates the cat list (CLI style)
        commandBox.setListNavigationHandler(
                catListPanel::selectPrevious,
                catListPanel::selectNext);

        // Lock the split pane divider so it cannot be dragged
        Platform.runLater(() -> {
            for (Node divider : mainSplitPane.lookupAll(".split-pane-divider")) {
                divider.setMouseTransparent(true);
                divider.setStyle("-fx-cursor: default;");
            }
        });

        // Force a complete layout pass by programmatically nudging the window size.
        // This works around a JavaFX timing issue where the first layout after
        // switching from the splash screen leaves some components unsized.
        Platform.runLater(() -> {
            primaryStage.setWidth(primaryStage.getWidth() + 1);
            Platform.runLater(() -> {
                primaryStage.setWidth(primaryStage.getWidth() - 1);
                // Re-anchor the divider so the sidebar keeps its width even
                // when the cat list is empty and has no content to hold it.
                mainSplitPane.setDividerPositions(0.35);
            });
        });

    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }


    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public CatListPanel getCatListPanel() {
        return catListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            Command command = logic.parseCommand(commandText);

            // Show confirmation dialog for update commands
            if (command instanceof UpdateCommand) {
                Optional<Cat> preview = logic.getUpdatePreview(command);
                if (preview.isPresent()) {
                    boolean confirmed = showUpdateConfirmationDialog(preview.get());
                    if (!confirmed) {
                        return CommandResult.cancelled();
                    }
                }
            }

            // Show confirmation dialog for delete commands
            if (command instanceof DeleteCommand) {
                Optional<Cat> preview = logic.getDeletePreview(command);
                if (preview.isPresent()) {
                    boolean confirmed = showDeleteConfirmationDialog(preview.get());
                    if (!confirmed) {
                        return CommandResult.cancelled();
                    }
                }
            }

            // Show confirmation dialog when export would overwrite an existing file
            if (command instanceof ExportCommand exportCommand) {
                if (exportCommand.getOutputPath().toFile().exists()) {
                    boolean confirmed = showExportOverwriteConfirmationDialog(
                            exportCommand.getOutputPath().getFileName().toString());
                    if (!confirmed) {
                        return CommandResult.cancelled();
                    }
                }
            }

            // Show confirmation dialog for clear commands
            if (command instanceof ClearCommand) {
                Optional<Integer> entryCount = logic.getClearPreview(command);
                if (entryCount.isPresent()) {
                    boolean confirmed = showClearConfirmationDialog(entryCount.get());
                    if (!confirmed) {
                        return CommandResult.cancelled();
                    }
                }
            }

            // Show confirmation dialog for undo commands
            if (command instanceof UndoCommand && logic.canUndo()
                    && !showUndoConfirmationDialog()) {
                return CommandResult.cancelled();
            }

            CommandResult commandResult = logic.executeCommand(command);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Shows a confirmation dialog for update commands.
     * Enter confirms, Esc cancels. No buttons - user presses keys.
     *
     * @param updatedEntry the cat entry that would result from the update
     * @return true if the user confirmed, false if cancelled
     */
    private boolean showUpdateConfirmationDialog(Cat updatedEntry) {
        String content = UPDATE_CONFIRMATION_HEADER + Messages.format(updatedEntry) + "?"
                + CONFIRMATION_HINT;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm Update");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(420);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");

        // Hide OK and Cancel buttons - user presses Enter/Esc instead
        alert.setOnShown(event -> {
            var okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
            var cancelButton = alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (okButton != null) {
                okButton.setVisible(false);
            }
            if (cancelButton != null) {
                cancelButton.setVisible(false);
            }
        });

        // Handle Enter = confirm, Esc = cancel
        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alert.setResult(ButtonType.OK);
                e.consume();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                alert.setResult(ButtonType.CANCEL);
                e.consume();
            }
        });

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows a confirmation dialog for delete commands.
     * Enter confirms, Esc cancels. No buttons - user presses keys.
     *
     * @param catToDelete the cat entry that would be deleted
     * @return true if the user confirmed, false if cancelled
     */
    private boolean showDeleteConfirmationDialog(Cat catToDelete) {
        String content = DELETE_CONFIRMATION_HEADER + Messages.format(catToDelete) + "?"
                + CONFIRMATION_HINT;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(420);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");

        // Hide OK and Cancel buttons - user presses Enter/Esc instead
        alert.setOnShown(event -> {
            var okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
            var cancelButton = alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (okButton != null) {
                okButton.setVisible(false);
            }
            if (cancelButton != null) {
                cancelButton.setVisible(false);
            }
        });

        // Handle Enter = confirm, Esc = cancel
        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alert.setResult(ButtonType.OK);
                e.consume();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                alert.setResult(ButtonType.CANCEL);
                e.consume();
            }
        });

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows a confirmation dialog for undo commands.
     * Enter confirms, Esc cancels. No buttons — user presses keys.
     *
     * @return true if the user confirmed, false if cancelled
     */
    private boolean showUndoConfirmationDialog() {
        return showConfirmationDialog("Confirm Undo",
                UNDO_CONFIRMATION_HEADER + CONFIRMATION_HINT);
    }

    /**
     * Shows a confirmation dialog for clear commands.
     * Enter confirms, Esc cancels. No buttons - user presses keys.
     *
     * @param entryCount the number of cat entries that would be cleared
     * @return true if the user confirmed, false if cancelled
     */
    private boolean showClearConfirmationDialog(int entryCount) {
        String content = CLEAR_CONFIRMATION_HEADER
                + String.format(CLEAR_CONFIRMATION_ENTRIES, entryCount)
                + CONFIRMATION_HINT;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm Clear");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(420);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");

        // Hide OK and Cancel buttons - user presses Enter/Esc instead
        alert.setOnShown(event -> {
            var okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
            var cancelButton = alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (okButton != null) {
                okButton.setVisible(false);
            }
            if (cancelButton != null) {
                cancelButton.setVisible(false);
            }
        });

        // Handle Enter = confirm, Esc = cancel
        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alert.setResult(ButtonType.OK);
                e.consume();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                alert.setResult(ButtonType.CANCEL);
                e.consume();
            }
        });

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows a confirmation dialog when an export would overwrite an existing file.
     * Enter confirms, Esc cancels. No buttons - user presses keys.
     *
     * @param filename the name of the file that would be overwritten
     * @return true if the user confirmed, false if cancelled
     */
    private boolean showExportOverwriteConfirmationDialog(String filename) {
        String content = String.format(EXPORT_OVERWRITE_HEADER, filename) + CONFIRMATION_HINT;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm Overwrite");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(420);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");

        // Hide OK and Cancel buttons - user presses Enter/Esc instead
        alert.setOnShown(event -> {
            var okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
            var cancelButton = alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (okButton != null) {
                okButton.setVisible(false);
            }
            if (cancelButton != null) {
                cancelButton.setVisible(false);
            }
        });

        // Handle Enter = confirm, Esc = cancel
        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alert.setResult(ButtonType.OK);
                e.consume();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                alert.setResult(ButtonType.CANCEL);
                e.consume();
            }
        });

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Creates and shows a styled confirmation dialog with the given title and content.
     * The dialog uses Enter to confirm and Esc to cancel, with default buttons hidden.
     *
     * @param title the dialog window title
     * @param content the message displayed in the dialog body
     * @return true if the user pressed Enter to confirm, false if cancelled
     */
    private boolean showConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(420);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");

        hideDialogButtons(alert);
        addEnterEscHandler(alert);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Hides the default OK and Cancel buttons on the given alert dialog.
     *
     * @param alert the alert whose buttons should be hidden
     */
    private void hideDialogButtons(Alert alert) {
        alert.setOnShown(event -> {
            var okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
            var cancelButton = alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (okButton != null) {
                okButton.setVisible(false);
            }
            if (cancelButton != null) {
                cancelButton.setVisible(false);
            }
        });
    }

    /**
     * Registers a key event filter on the given alert so that Enter confirms
     * and Escape cancels the dialog.
     *
     * @param alert the alert to attach key handlers to
     */
    private void addEnterEscHandler(Alert alert) {
        alert.getDialogPane().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alert.setResult(ButtonType.OK);
                e.consume();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                alert.setResult(ButtonType.CANCEL);
                e.consume();
            }
        });
    }
}

package seedu.savenus.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.AutoSortCommand;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.commands.BuyCommand;
import seedu.savenus.logic.commands.ClearCommand;
import seedu.savenus.logic.commands.CollapseCommand;
import seedu.savenus.logic.commands.CustomSortCommand;
import seedu.savenus.logic.commands.DefaultCommand;
import seedu.savenus.logic.commands.DeleteCommand;
import seedu.savenus.logic.commands.DislikeCommand;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.ExitCommand;
import seedu.savenus.logic.commands.ExpandCommand;
import seedu.savenus.logic.commands.FilterCommand;
import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.HelpCommand;
import seedu.savenus.logic.commands.HistoryCommand;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.commands.LikeCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.MakeSortCommand;
import seedu.savenus.logic.commands.RecommendCommand;
import seedu.savenus.logic.commands.RemoveDislikeCommand;
import seedu.savenus.logic.commands.RemoveLikeCommand;
import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.commands.SortCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://github.com/AY1920S1"
            + "-CS2103T-F13-2/main/blob/master/docs/UserGuide.adoc";
    public static final String HELP_MESSAGE = "Here is the list of available commands:\n\n"
            + AddCommand.COMMAND_WORD + "\n"
            + AutoSortCommand.COMMAND_WORD + "\n"
            + BudgetCommand.COMMAND_WORD + "\n"
            + BuyCommand.COMMAND_WORD + "\n"
            + ClearCommand.COMMAND_WORD + "\n"
            + CollapseCommand.COMMAND_WORD + "\n"
            + CustomSortCommand.COMMAND_WORD + "\n"
            + DefaultCommand.COMMAND_WORD + "\n"
            + DeleteCommand.COMMAND_WORD + "\n"
            + DislikeCommand.COMMAND_WORD + "\n"
            + EditCommand.COMMAND_WORD + "\n"
            + ExitCommand.COMMAND_WORD + "\n"
            + ExpandCommand.COMMAND_WORD + "\n"
            + FilterCommand.COMMAND_WORD + "\n"
            + FindCommand.COMMAND_WORD + "\n"
            + HelpCommand.COMMAND_WORD + "\n"
            + HistoryCommand.COMMAND_WORD + "\n"
            + InfoCommand.COMMAND_WORD + "\n"
            + LikeCommand.COMMAND_WORD + "\n"
            + ListCommand.COMMAND_WORD + "\n"
            + MakeSortCommand.COMMAND_WORD + "\n"
            + RecommendCommand.COMMAND_WORD + "\n"
            + RemoveDislikeCommand.COMMAND_WORD + "\n"
            + RemoveLikeCommand.COMMAND_WORD + "\n"
            + SaveCommand.COMMAND_WORD + "\n"
            + SortCommand.COMMAND_WORD + "\n\n"
            + "Click the user guide button below to open to full user guide.\n"
            + "You will need internet connection to open the user guide.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button openButton;

    @FXML
    private Button closeButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        root.initStyle(StageStyle.UNDECORATED);
        root.centerOnScreen();
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void openUrl() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            runtime.exec("rundll32 url.dll,FileProtocolHandler " + USERGUIDE_URL);
        } else {
            runtime.exec("open " + USERGUIDE_URL);
        }
    }

    @FXML
    private void closeWindow() {
        getRoot().close();
    }
}

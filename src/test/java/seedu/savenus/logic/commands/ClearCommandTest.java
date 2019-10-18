package seedu.savenus.logic.commands;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;

public class ClearCommandTest {

    @Test
    public void execute_emptyMenu_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMenu_success() {
        Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations());
        Model expectedModel = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations());
        expectedModel.setMenu(new Menu());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

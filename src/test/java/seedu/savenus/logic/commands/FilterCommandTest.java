package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.savings.SavingsHistory;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

public class FilterCommandTest {
    private List<String> fields;
    private FilterCommand command;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void set_up() {
        fields = new ArrayList<String>();
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(), new AliasList());
        expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
                new AliasList());
    }

    @Test
    public void execute_recommendCommand() {
        assertCommandSuccess(new FilterCommand(fields), model,
            FilterCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void getFields_test() {
        command = new FilterCommand(fields);
        assertEquals(command.getFields(), fields);
        assertEquals(command.getFields(), new ArrayList<String>());
    }

    @Test
    public void equals_test() {
        command = new FilterCommand(fields);
        assertEquals(command, command);
        assertEquals(command, new FilterCommand(fields));
        assertNotEquals(command, null);
    }

}

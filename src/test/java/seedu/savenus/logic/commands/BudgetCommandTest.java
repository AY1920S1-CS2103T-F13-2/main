package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

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
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

//@@author raikonen
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BudgetCommand}.
 */
public class BudgetCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
            new AliasList());

    @Test
    public void execute_validBudgetAmountAndDuration_success() {

        RemainingBudget testRemainingBudget = new RemainingBudget("100");
        DaysToExpire testDaysToExpire = new DaysToExpire("30");
        BudgetCommand budgetCommand = new BudgetCommand(new Wallet(testRemainingBudget, testDaysToExpire));

        String expectedMessage = String.format(BudgetCommand.MESSAGE_SET_BUDGET_SUCCESS,
                testRemainingBudget.toString(), testDaysToExpire.toString());

        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
                new AliasList());
        try {
            expectedModel.getWallet().setRemainingBudget(testRemainingBudget);
            expectedModel.getWallet().setDaysToExpire(testDaysToExpire);
        } catch (Exception e) {
            return;
        }
        assertCommandSuccess(budgetCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        BudgetCommand budgetFirstCommand = new BudgetCommand(new Wallet(
                new RemainingBudget("100.50"),
                new DaysToExpire("30")));
        BudgetCommand budgetSecondCommand = new BudgetCommand(new Wallet(
                new RemainingBudget("250.50"),
                new DaysToExpire("50")));

        // same object -> returns true
        assertTrue(budgetFirstCommand.equals(budgetFirstCommand));

        // same values -> returns true
        BudgetCommand budgetFirstCommandCopy = new BudgetCommand(new Wallet(
                new RemainingBudget("100.50"),
                new DaysToExpire("30")));
        assertTrue(budgetFirstCommand.equals(budgetFirstCommandCopy));

        // different types -> returns false
        assertFalse(budgetFirstCommand.equals(1));

        // null -> returns false
        assertFalse(budgetFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(budgetFirstCommand.equals(budgetSecondCommand));
    }
}

package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

public class FavoriteCommandTest {
    private FavoriteCommand command;
    private Index index;
    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount());

    @BeforeEach
    public void setUp() {
        index = Index.fromOneBased(1);
        command = new FavoriteCommand(index);
    }

    @Test
    public void command_tests() {
        System.out.println(model.getFilteredFoodList());
        assertFalse(command.isFoodAlreadyFavorite(model.getFilteredFoodList().get(0)));
    }

    @Test
    public void indexOutOfBounds_error() {
        FavoriteCommand inavlidCommand = new FavoriteCommand(Index.fromOneBased(Integer.MAX_VALUE));
        assertThrows(
                CommandException.class,
                Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX, () -> inavlidCommand.execute(model)
        );
    }

    @Test
    public void equals() {
        assertEquals(command, command);
        assertEquals(command, new FavoriteCommand(index));
    }
}

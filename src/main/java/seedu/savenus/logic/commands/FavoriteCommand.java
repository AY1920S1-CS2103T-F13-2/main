package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.FavoriteValue;
import seedu.savenus.model.food.Food;

/**
 * Represents a simple command to favorite a food item.
 */
public class FavoriteCommand extends Command {

    public static final String COMMAND_WORD = "favorite";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the food at the particular index as  your favorite food item"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_FAVORITE_FOOD_SUCCESS = "Favorite Command Successful!";
    private final Index targetIndex;

    /**
     * Creates a new Favorite command to favorite the food item at the target index.
     * @param targetIndex the index of the food item.
     */
    public FavoriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Index getIndex() {
        return this.targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToEdit = lastShownList.get(targetIndex.getZeroBased());
        Food foodCopy = foodToEdit;
        if (!isFoodAlreadyFavorite(foodCopy)) {
            foodCopy.reverseFavoriteValue();
        }

        model.setFood(foodToEdit, foodCopy);
        return new CommandResult(String.format(MESSAGE_FAVORITE_FOOD_SUCCESS));
    }

    /**
     * Checks if the food item is already favorite.
     * @param food the food item.
     * @return true if the food item's favorite value is 1.
     */
    public boolean isFoodAlreadyFavorite(Food food) {
        return food.getFavoriteValue().equals(new FavoriteValue("1"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && targetIndex.equals(((FavoriteCommand) other).getIndex())); // state check
    }
}

package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.sort.FoodComparator;

//@@author seanlowjk
/**
 * Sorts all the foods in the $aveNUS menu based on given criterion.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String EXAMPLE_USAGE = "Example Usage: " + COMMAND_WORD + " PRICE ASC NAME DESC";
    public static final String MESSAGE_SUCCESS = "You have successfully sorted the food items!";
    public static final String NO_FIELDS_ERROR = "You have keyed in zero fields! "
        + "You need to key in at least one field.";
    public static final String AUTO_SORT_WARNING = "Autosort is turned on! \n"
            + "This command will not work unless you turn autosort off.";

    private List<String> fields;

    /**
     * Create a simple Sort Command.
     * @param fields the list of fields.
     */
    public SortCommand(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return this.fields;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getAutoSortFlag() == true) {
            return new CommandResult(AUTO_SORT_WARNING);
        }

        // Clear the recommendation system (if it was used)
        model.setRecommendationSystemInUse(false);

        ObservableList<Food> foodList = model.getFilteredFoodList();
        SortedList<Food> sortedList = foodList.sorted(new FoodComparator(fields));
        model.setFoods(sortedList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && getFields().equals(((SortCommand) other).getFields()));
    }
}

package seedu.savenus.model.sort;

import java.util.Comparator;

import seedu.savenus.model.food.Food;

//@@author seanlowjk
/**
 * A Simple Comparator that compares by category, name and then price.
 */
public class DefaultComparator implements Comparator<Food> {
    @Override
    public int compare(Food a, Food b) {
        int categoryComparisonFactor = a.getCategory().compareTo(b.getCategory());
        int nameComparisonFactor = a.getName().compareTo(b.getName());
        int priceComparisonFactor = a.getPrice().compareTo(b.getPrice());

        if (categoryComparisonFactor != 0) {
            return categoryComparisonFactor;
        } else {
            if (nameComparisonFactor != 0) {
                return nameComparisonFactor;
            } else {
                return priceComparisonFactor;
            }
        }
    }
}

package seedu.savenus.storage.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.storage.food.JsonAdaptedFood;
import seedu.savenus.storage.wallet.JsonAdaptedWallet;

/**
 * An Immutable Menu that is serializable to JSON format.
 */
@JsonRootName(value = "savenus")
public class JsonSerializableMenu {

    public static final String MESSAGE_DUPLICATE_FOOD = "foods list contains duplicate food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();
    private JsonAdaptedWallet wallet;

    /**
     * Constructs a {@code JsonSerializableMenu} with the given foods.
     */
    @JsonCreator
    public JsonSerializableMenu(@JsonProperty("foods") List<JsonAdaptedFood> foods,
                                @JsonProperty("wallet") JsonAdaptedWallet wallet) {
        this.foods.addAll(foods);
        this.wallet = wallet;
    }

    /**
     * Converts a given {@code ReadOnlyMenu} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMenu}.
     */
    public JsonSerializableMenu(ReadOnlyMenu source) {
        foods.addAll(source.getFoodList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
        wallet = new JsonAdaptedWallet(source.getWallet());
    }

    /**
     * Converts this menu into the model's {@code Menu} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Menu toModelType() throws IllegalValueException {
        Menu menu = new Menu();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (menu.hasFood(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            menu.addFood(food);
        }
        menu.setWallet(wallet.toModelType());
        return menu;
    }

}

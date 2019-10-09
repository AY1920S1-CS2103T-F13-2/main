package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalFood.ALICE;
import static seedu.savenus.testutil.TypicalFood.BOB;

import org.junit.jupiter.api.Test;

import seedu.savenus.testutil.FoodBuilder;

public class FoodTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Food food = new FoodBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> food.getTags().remove(0));
    }

    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(ALICE.isSameFood(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameFood(null));

        // different price and description -> returns false
        Food editedAlice = new FoodBuilder(ALICE).withPrice(VALID_PRICE_BOB)
                .withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.isSameFood(editedAlice));

        // different name -> returns false
        editedAlice = new FoodBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameFood(editedAlice));

        // same name, same price, different description -> returns false
        editedAlice = new FoodBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .build();
        assertFalse(ALICE.isSameFood(editedAlice));

        // same name, same description, different price -> returns false
        editedAlice = new FoodBuilder(ALICE).withPrice(VALID_PRICE_BOB)
                .build();
        assertFalse(ALICE.isSameFood(editedAlice));

        // same name, same price, same description, different tags -> returns true
        editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameFood(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Food aliceCopy = new FoodBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different food -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Food editedAlice = new FoodBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different price -> returns false
        editedAlice = new FoodBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new FoodBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}

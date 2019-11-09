package seedu.savenus.model.wallet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author raikonen
public class DaysToExpireTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DaysToExpire(null));
    }

    @Test
    public void constructor_invalidDaysToExpire_throwsIllegalArgumentException() {
        String invalidDaysToExpire = "abc";
        assertThrows(IllegalArgumentException.class, () -> new DaysToExpire(invalidDaysToExpire));
    }

    @Test
    public void constructor_daysToExpireWithSpacesOnly_throwsIllegalArgumentException() {
        String invalidDaysToExpire = "                      ";
        assertThrows(IllegalArgumentException.class, () -> new DaysToExpire(invalidDaysToExpire));
    }

    @Test
    public void isValidDaysToExpireTest() {
        // null name
        assertThrows(NullPointerException.class, () -> DaysToExpire.isValidDaysToExpire(null));

        // invalid daysToExpire
        assertFalse(DaysToExpire.isValidDaysToExpire("^")); // only non-alphanumeric characters
        assertFalse(DaysToExpire.isValidDaysToExpire("prata*")); // contains non-alphanumeric characters
        assertFalse(DaysToExpire.isValidDaysToExpire("-10")); // negative daysToExpire

        // valid daysToExpire
        assertTrue(DaysToExpire.isValidDaysToExpire("123")); // valid daysToExpire
    }

    @Test
    public void isOutOfBoundsTest() {
        // in bound remainingBudget
        assertFalse(new DaysToExpire("100").isOutOfBounds()); // only non-alphanumeric characters

        // out of bounds remainingBudget
        assertTrue(new DaysToExpire("1000000000").isOutOfBounds()); // only non-alphanumeric characters
    }

    @Test
    public void isEmptyDaysToExpire() {
        assertFalse(DaysToExpire.isValidDaysToExpire("")); // empty string
        assertFalse(DaysToExpire.isValidDaysToExpire(" ")); // spaces only
        assertFalse(DaysToExpire.isValidDaysToExpire("           ")); // tons of spaces
    }

}

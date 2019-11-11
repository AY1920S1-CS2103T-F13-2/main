package seedu.savenus.model.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.DESCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_AYAM;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

//@@author seanlowjk
public class FoodComparatorTest {
    private List<String> fields = new ArrayList<String>();

    @Test
    public void check_compare() {
        fields.add(FIELD_NAME_NAME);
        fields.add(ASCENDING_DIRECTION);
        assertThrows(NullPointerException.class, () -> new FoodComparator(fields).compare(CARBONARA, null));
        assertEquals(new FoodComparator(fields).compare(CARBONARA, CARBONARA), 0);
        assertNotEquals(new FoodComparator(fields).compare(CARBONARA, NASI_AYAM), 0);
    }

    @Test
    public void check_compare_desc() {
        fields.add(FIELD_NAME_NAME);
        fields.add(DESCENDING_DIRECTION);
        assertThrows(NullPointerException.class, () -> new FoodComparator(fields).compare(CARBONARA, null));
        assertEquals(new FoodComparator(fields).compare(CARBONARA, CARBONARA), 0);
        assertNotEquals(new FoodComparator(fields).compare(CARBONARA, NASI_AYAM), 0);
    }

    @Test
    public void check_direction() {
        FoodComparator test = new FoodComparator(fields);
        assertTrue(test.isDirectionAscending(ASCENDING_DIRECTION));
        assertFalse(test.isDirectionAscending(DESCENDING_DIRECTION));
    }

    @Test
    public void equals() {
        FoodComparator comparator = new FoodComparator(new ArrayList<String>());
        assertNotEquals(comparator, new Object());
        assertEquals(comparator, comparator);
    }

    @Test
    public void equal_strings() {
        fields.add(FIELD_NAME_NAME);
        fields.add(ASCENDING_DIRECTION);
        FoodComparator test = new FoodComparator(fields);

        String representation = "NAME ASC ";

        assertEquals(test.toString(), representation);
    }
}

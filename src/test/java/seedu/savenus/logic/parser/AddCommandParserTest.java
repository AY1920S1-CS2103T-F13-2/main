package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.savenus.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.savenus.testutil.TypicalFood.AMY;
import static seedu.savenus.testutil.TypicalFood.BOB;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.testutil.FoodBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Food expectedFood = new FoodBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFood));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFood));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_AMY + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFood));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_AMY + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFood));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFood));

        // multiple tags - all accepted
        Food expectedFoodMultipleTags = new FoodBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + OPENING_HOURS_DESC_BOB
                + RESTRICTIONS_DESC_BOB, new AddCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Food expectedFood = new FoodBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PRICE_DESC_AMY + DESCRIPTION_DESC_AMY
                        + CATEGORY_DESC_AMY + OPENING_HOURS_DESC_AMY + RESTRICTIONS_DESC_AMY,
                new AddCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                        + CATEGORY_DESC_BOB,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PRICE_BOB + DESCRIPTION_DESC_BOB
                        + CATEGORY_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PRICE_BOB + VALID_DESCRIPTION_BOB
                        + CATEGORY_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PRICE_DESC + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + INVALID_DESCRIPTION_DESC
                + CATEGORY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                + CATEGORY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                        + CATEGORY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PRICE_DESC_BOB + DESCRIPTION_DESC_BOB
                        + CATEGORY_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

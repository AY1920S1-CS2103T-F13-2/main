package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.TAG_DESC_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_LOCATION_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_RICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_THIRD_FOOD;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Tag;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_CHICKEN_RICE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_CHICKEN_RICE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_CHICKEN_RICE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid price followed by valid description
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC
            + DESCRIPTION_DESC_CHICKEN_RICE, Price.MESSAGE_CONSTRAINTS);

        // valid price followed by invalid price. The test case for invalid price followed by valid price
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PRICE_DESC_NASI_LEMAK
            + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Food} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_RICE + TAG_DESC_CHICKEN
            + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_RICE + TAG_EMPTY
            + TAG_DESC_CHICKEN, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_RICE
            + TAG_DESC_CHICKEN, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC
            + INVALID_DESCRIPTION_DESC + VALID_PRICE_CHICKEN_RICE,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FOOD;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_NASI_LEMAK + TAG_DESC_CHICKEN
                + DESCRIPTION_DESC_CHICKEN_RICE + NAME_DESC_CHICKEN_RICE + CATEGORY_DESC_CHICKEN_RICE
                + TAG_DESC_RICE + LOCATION_DESC_CHICKEN_RICE + OPENING_HOURS_DESC_CHICKEN_RICE
                + RESTRICTIONS_DESC_CHICKEN_RICE;

        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_CHICKEN_RICE)
                .withPrice(VALID_PRICE_NASI_LEMAK).withDescription(VALID_DESCRIPTION_CHICKEN_RICE)
                .withCategory(VALID_CATEGORY_CHICKEN_RICE)
                .withTags(VALID_TAG_CHICKEN, VALID_TAG_RICE)
                .withLocation(VALID_LOCATION_CHICKEN_RICE)
                .withOpeningHours(VALID_OPENING_HOURS_CHICKEN_RICE)
                .withRestrictions(VALID_RESTRICTIONS_CHICKEN_RICE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_CHICKEN_RICE;

        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withPrice(VALID_PRICE_NASI_LEMAK)
                .withDescription(VALID_DESCRIPTION_CHICKEN_RICE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_FOOD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_CHICKEN_RICE;
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_CHICKEN_RICE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PRICE_DESC_CHICKEN_RICE;
        descriptor = new EditFoodDescriptorBuilder().withPrice(VALID_PRICE_CHICKEN_RICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CHICKEN_RICE;
        descriptor = new EditFoodDescriptorBuilder().withDescription(VALID_DESCRIPTION_CHICKEN_RICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_RICE;
        descriptor = new EditFoodDescriptorBuilder().withTags(VALID_TAG_RICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_CHICKEN_RICE;
        descriptor = new EditFoodDescriptorBuilder().withLocation(VALID_LOCATION_CHICKEN_RICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE
                + TAG_DESC_RICE + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE + TAG_DESC_RICE
                + PRICE_DESC_NASI_LEMAK + DESCRIPTION_DESC_NASI_LEMAK + TAG_DESC_CHICKEN;

        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withPrice(VALID_PRICE_NASI_LEMAK)
                .withDescription(VALID_DESCRIPTION_NASI_LEMAK).withTags(VALID_TAG_RICE, VALID_TAG_CHICKEN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = targetIndex.getOneBased() + INVALID_PRICE_DESC + PRICE_DESC_NASI_LEMAK;
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withPrice(VALID_PRICE_NASI_LEMAK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        // other valid values specified
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_NASI_LEMAK + INVALID_PRICE_DESC
                + PRICE_DESC_NASI_LEMAK;
        descriptor = new EditFoodDescriptorBuilder()
                .withPrice(VALID_PRICE_NASI_LEMAK).withDescription(VALID_DESCRIPTION_NASI_LEMAK)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FOOD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

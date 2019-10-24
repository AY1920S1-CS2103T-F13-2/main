package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.FavoriteCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

public class FavoriteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE);

    private FavoriteCommandParser parser = new FavoriteCommandParser();

    @Test
    public void parse_invalidParts_failure() {
        // no index specified
        assertParseFailure(parser, "aa", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_success() throws ParseException {
        assertEquals(parser.parse("1"), new FavoriteCommand(Index.fromOneBased(1)));
    }

}

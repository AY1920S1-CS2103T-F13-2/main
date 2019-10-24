package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.AutoSortCommand;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.commands.BuyCommand;
import seedu.savenus.logic.commands.ClearCommand;
import seedu.savenus.logic.commands.CollapseCommand;
import seedu.savenus.logic.commands.Command;
import seedu.savenus.logic.commands.CustomSortCommand;
import seedu.savenus.logic.commands.DefaultCommand;
import seedu.savenus.logic.commands.DeleteCommand;
import seedu.savenus.logic.commands.DislikeCommand;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.ExitCommand;
import seedu.savenus.logic.commands.ExpandCommand;
import seedu.savenus.logic.commands.FavoriteCommand;
import seedu.savenus.logic.commands.FilterCommand;
import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.HelpCommand;
import seedu.savenus.logic.commands.HistoryCommand;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.commands.LikeCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.MakeSortCommand;
import seedu.savenus.logic.commands.RecommendCommand;
import seedu.savenus.logic.commands.RemoveDislikeCommand;
import seedu.savenus.logic.commands.RemoveLikeCommand;
import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.commands.SortCommand;

import seedu.savenus.logic.commands.TopUpCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SaveNusParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case BudgetCommand.COMMAND_WORD:
            return new BudgetCommandParser().parse(arguments);

        case TopUpCommand.COMMAND_WORD:
            return new TopUpCommandParser().parse(arguments);

        case BuyCommand.COMMAND_WORD:
            return new BuyCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case RecommendCommand.COMMAND_WORD:
            return new RecommendCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case InfoCommand.COMMAND_WORD:
            return new InfoCommandParser().parse(arguments);

        case DefaultCommand.COMMAND_WORD:
            return new DefaultCommand();

        case HistoryCommand.COMMAND_ALIAS:
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case LikeCommand.COMMAND_WORD:
            return new PreferenceCommandParser().parse(arguments, true);

        case RemoveLikeCommand.COMMAND_WORD:
            return new RemovePreferenceCommandParser().parse(arguments, true);

        case DislikeCommand.COMMAND_WORD:
            return new PreferenceCommandParser().parse(arguments, false);

        case RemoveDislikeCommand.COMMAND_WORD:
            return new RemovePreferenceCommandParser().parse(arguments, false);

        case CollapseCommand.COMMAND_WORD:
            return new CollapseCommand();

        case ExpandCommand.COMMAND_WORD:
            return new ExpandCommand();

        case CustomSortCommand.COMMAND_WORD:
            return new CustomSortCommand();

        case MakeSortCommand.COMMAND_WORD:
            return new MakeSortCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case AutoSortCommand.COMMAND_WORD:
            return new AutoSortCommandParser().parse(arguments);

        case FavoriteCommand.COMMAND_WORD:
            return new FavoriteCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

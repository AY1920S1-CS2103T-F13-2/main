package seedu.savenus.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_TAG, PREFIX_LOCATION, PREFIX_OPENING_HOURS, PREFIX_RESTRICTIONS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditFoodDescriptor editFoodDescriptor = new EditFoodDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editFoodDescriptor.setName(ParserUtil.parseTheNames(argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editFoodDescriptor.setPrice(ParserUtil.parseThePrices(argMultimap.getAllValues(PREFIX_PRICE)));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editFoodDescriptor.setDescription(
                    ParserUtil.parseTheDescriptions(argMultimap.getAllValues(PREFIX_DESCRIPTION)));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editFoodDescriptor.setCategory(
                    ParserUtil.parseTheCategories(argMultimap.getAllValues(PREFIX_CATEGORY)));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editFoodDescriptor.setLocation(
                    ParserUtil.parseTheLocations(argMultimap.getAllValues(PREFIX_LOCATION)));
        }
        if (argMultimap.getValue(PREFIX_OPENING_HOURS).isPresent()) {
            editFoodDescriptor.setOpeningHours(
                    ParserUtil.parseTheOpeningHours(argMultimap.getAllValues(PREFIX_OPENING_HOURS)));
        }
        if (argMultimap.getValue(PREFIX_RESTRICTIONS).isPresent()) {
            editFoodDescriptor.setRestrictions(
                    ParserUtil.parseTheRestrictions(argMultimap.getAllValues(PREFIX_RESTRICTIONS)));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editFoodDescriptor::setTags);

        if (!editFoodDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFoodDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

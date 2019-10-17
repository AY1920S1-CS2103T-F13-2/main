package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;


/**
 * Adds the user's liked recommendations to the $aveNUS recommendation system.
 */
public class LikeCommand extends PreferenceCommand {

    public static final String COMMAND_WORD = "like";

    /**
     * Creates an LikeCommand to add the user's recommendations
     */
    public LikeCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        super(categoryList, tagList, locationList);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return this.execute(model, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LikeCommand // instanceof handles nulls
                && categoryList.equals(((LikeCommand) other).categoryList))
                && tagList.equals(((LikeCommand) other).tagList)
                && locationList.equals(((LikeCommand) other).locationList);
    }
}
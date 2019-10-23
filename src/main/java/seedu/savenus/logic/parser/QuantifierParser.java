package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;

/**
 * Checks if the fields and quantifiers are valid.
 */
public class QuantifierParser {

    public static final String NO_ARGUMENTS_USAGE = "Note you have entered in zero arguments:\n";
    public static final String DUPLICATE_FIELD_USAGE = "Note you have entered a duplicate field.";
    public static final String WRONG_ARGUMENT_NUMBER = "Note that you have key-ed in a wrong number of arguments.\n"
            + "Please fix the formatting to: FIELD QUANTIFIER VALUE\n"
            + "Note that VALUE MUST be only one word.";
    public static final String INVALID_FIELD_USAGE = "Note you have entered an invalid field! \n"
            + "You are only allowed to enter the following fields:\n"
            + "NAME, PRICE, CATEGORY, DESCRIPTION, LOCATION, OPENING_HOURS, RESTRICTIONS";
    public static final String INVALID_QUANTIFIER_USAGE = "Note you have entered an invalid quantifier! \n"
            + "You are only allowed to enter the following quantifiers:\n"
            + "LESS_THAN, EQUALS_TO or MORE_THAN";
    public static final String INVALID_VALUE_USAGE = "Note you have entered an invalid value! \n"
            + "Please do make sure your value follow the field requirements.";

    /**
     * Parses the given String and returns a List of fields for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public List<String> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(NO_ARGUMENTS_USAGE);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        checkKeywords(nameKeywords);
        return (Arrays.asList(nameKeywords));
    }

    /**
     * Checks if the array of keywords are valid or not.
     * @param keywords the array of keywords.
     * @throws ParseException if there is an error found in the array.
     */
    public void checkKeywords(String[] keywords) throws ParseException {
        if (isWrongArgumentNumber(keywords)) {
            throw new ParseException(WRONG_ARGUMENT_NUMBER);
        } else if (areFieldsInvalid(keywords)) {
            throw new ParseException(INVALID_FIELD_USAGE);
        } else if (areQuantifiersInvalid(keywords)) {
            throw new ParseException(INVALID_QUANTIFIER_USAGE);
        } else if (areValuesInvalid(keywords)) {
            throw new ParseException(INVALID_VALUE_USAGE);
        } else if (areFieldsDuplicate(keywords)) {
            throw new ParseException(DUPLICATE_FIELD_USAGE);
        }
    }

    /**
     * Checks if the array of fields contain invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid fields. False if otherwise.
     */
    public boolean areFieldsInvalid(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (!isValidField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the quantifiers are missing for certain fields.
     * @param keywords the array of fields.
     * @return true if quantifiers are missing, false if otherwise.
     */
    public boolean isWrongArgumentNumber(String[] keywords) {
        return keywords.length % 3 != 0;
    }

    /**
     * Checks if the array of fields contain invalid directions.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid directions. False if otherwise.
     */
    public boolean areQuantifiersInvalid(String[] keywords) {
        for (int i = 1; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (!isValidQuantifier(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields contain invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid values. False if otherwise.
     */
    public boolean areValuesInvalid(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            String value = keywords[i + 2];
            if (!isValidValue(field, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields contain duplicate values.
     * @param keywords the array of fields.
     * @return true if the fields do contain duplicate fields. False if otherwise.
     */
    public boolean areFieldsDuplicate(String[] keywords) {
        Set<String> noDuplicateFields = new HashSet<String>();
        for (int i = 0; i < keywords.length; i += 3) {
            String field = keywords[i];
            if (!noDuplicateFields.add(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the field is valid or not.
     * @param field the field.
     * @return true if the field is valid. False if otherwise.
     */
    public boolean isValidField(String field) {
        return field.equals(FIELD_NAME_CATEGORY)
                || field.equals(FIELD_NAME_DESCRIPTION)
                || field.equals(FIELD_NAME_LOCATION)
                || field.equals(FIELD_NAME_NAME)
                || field.equals(FIELD_NAME_OPENING_HOURS)
                || field.equals(FIELD_NAME_PRICE)
                || field.equals(FIELD_NAME_RESTRICTIONS);
    }

    /**
     * Checks if the quantifier is valid or not.
     * @param quantifier the quantifier.
     * @return true if the quantifier is valid. False if otherwise.
     */
    public boolean isValidQuantifier(String quantifier) {
        return quantifier.equals(QUANTIFY_EQUALS_TO)
                || quantifier.equals(QUANTIFY_LESS_THAN)
                || quantifier.equals(QUANTIFY_MORE_THAN);
    }

    /**
     * Checks if the value is valid or not.
     * @param field the field.
     * @param value the value.
     * @return true if the value is valid. False if otherwsie.
     */
    public boolean isValidValue(String field, String value) {
        switch(field) {

        case FIELD_NAME_CATEGORY:
            return Category.isValidCategory(value);

        case FIELD_NAME_DESCRIPTION:
            return Description.isValidDescription(value);

        case FIELD_NAME_LOCATION:
            return Location.isValidLocation(value);

        case FIELD_NAME_NAME:
            return Name.isValidName(value);

        case FIELD_NAME_OPENING_HOURS:
            return OpeningHours.isValidOpeningHours(value);

        case FIELD_NAME_PRICE:
            return Price.isValidPrice(value);

        case FIELD_NAME_RESTRICTIONS:
            return Restrictions.isValidRestrictions(value);

        default:
            return false;
        }
    }
}

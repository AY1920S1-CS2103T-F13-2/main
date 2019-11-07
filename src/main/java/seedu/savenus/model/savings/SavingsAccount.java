package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import seedu.savenus.model.savings.exceptions.InsufficientSavingsException;
import seedu.savenus.model.savings.exceptions.SavingsOutOfBoundException;
import seedu.savenus.model.util.Money;

//@@author fatclarence
/**
 * Represents a user's {@code: SavingsAccount} in the application.
 */
public class SavingsAccount implements ReadOnlySavingsAccount {

    private final CurrentSavings currentSavings;

    /**
     * Default constructor when app initialises without an existing readable SavingsAccount file.
     */
    public SavingsAccount() {
        this.currentSavings = new CurrentSavings(new Money("0.00"));
    }

    /**
     * Overloaded constructor that sets {@code CurrentSavings} with the provided currentSavings.
     */
    public SavingsAccount(CurrentSavings currSavings) {
        this.currentSavings = currSavings;
    }

    /**
     * Overloaded constructor that resets the account data with the provided data to be copied.
     */
    public SavingsAccount(ReadOnlySavingsAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code SavingsAccount} with {@code toBeCopied}.
     * @param toBeCopied
     */
    private void resetData(ReadOnlySavingsAccount toBeCopied) {
        requireNonNull(toBeCopied);

        // Get money value from the Savings Account to be copied
        Money toCopy = toBeCopied.getCurrentSavings().get();
        // Overwrite current savings value with value toCopy
        currentSavings.setCurrentSavings(toCopy);
    }

    /**
     * Add to the current savings.
     */
    public void addToSavings(Savings savings) throws SavingsOutOfBoundException {
        Money amountToAdd = savings.getSavingsAmount();
        Money currentSavingsMoney = this.currentSavings.getCurrentSavingsMoney();
        Money newCurrentSavings = currentSavingsMoney.add(amountToAdd);
        if (newCurrentSavings.isOutOfBounds()) {
            throw new SavingsOutOfBoundException();
        } else {
            this.currentSavings.setCurrentSavings(newCurrentSavings);
        }
    }

    /**
     * Deduct money from the current savings
     */
    public void deductFromSavings(Savings savings) throws InsufficientSavingsException {
        // Check whether savings account has enough money.
        Money toSubtract = savings.getSavingsAmount();
        Money currentSavingsMoney = this.currentSavings.getCurrentSavingsMoney();
        Money newSavingsMoney = currentSavingsMoney.add(toSubtract);
        if (newSavingsMoney.isNegativeValue()) {
            throw new InsufficientSavingsException();
        } else {
            currentSavings.setCurrentSavings(newSavingsMoney);
        }
    }

    /**
     * Get the current savings amount in the savings account
     * @return Unmodifiable Current Savings Amount
     */
    @Override
    public ObjectProperty<Money> getCurrentSavings() {
        return this.currentSavings.getCurrentSavingsProperty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SavingsAccount
                && currentSavings.equals(((SavingsAccount) other).currentSavings));
    }
}

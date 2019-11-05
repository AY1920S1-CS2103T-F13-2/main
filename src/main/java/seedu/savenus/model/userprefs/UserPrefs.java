package seedu.savenus.model.userprefs;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.savenus.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path aliasFilePath = Paths.get("data", "savenus-alias.json");
    private Path menuFilePath = Paths.get("data" , "savenus-menu.json");
    private Path recsFilePath = Paths.get("data" , "savenus-recs.json");
    private Path purchaseHistoryFilePath = Paths.get("data" , "savenus-purchases.json");
    private Path sortFilePath = Paths.get("data" , "savenus-sort.json");
    private Path savingsHistoryFilePath = Paths.get("data", "savings.json");
    private Path walletFilePath = Paths.get("data", "savenus-wallet.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setMenuFilePath(newUserPrefs.getMenuFilePath());
        setPurchaseHistoryFilePath(newUserPrefs.getPurchaseHistoryFilePath());
        setSavingsHistoryFilePath(newUserPrefs.getSavingsHistoryFilePath());
        setWalletFilePath(newUserPrefs.getWalletFilePath());
        setAliasFilePath(newUserPrefs.getAliasFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getMenuFilePath() {
        return menuFilePath;
    }

    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        this.menuFilePath = menuFilePath;
    }

    public Path getSavingsHistoryFilePath() {
        return savingsHistoryFilePath;
    }

    public void setSavingsHistoryFilePath(Path savingsHistoryFilePath) {
        requireNonNull(savingsHistoryFilePath);
        this.savingsHistoryFilePath = savingsHistoryFilePath;
    }

    public Path getRecsFilePath() {
        return recsFilePath;
    }

    public void setRecsFilePath(Path recsFilePath) {
        requireNonNull(recsFilePath);
        this.recsFilePath = recsFilePath;
    }

    public Path getPurchaseHistoryFilePath() {
        return purchaseHistoryFilePath;
    }

    public void setPurchaseHistoryFilePath(Path purchaseHistoryFilePath) {
        requireNonNull(recsFilePath);
        this.purchaseHistoryFilePath = purchaseHistoryFilePath;
    }

    public Path getWalletFilePath() {
        return walletFilePath;
    }

    public void setWalletFilePath(Path walletFilePath) {
        requireNonNull(recsFilePath);
        this.walletFilePath = walletFilePath;
    }

    public Path getSortFilePath() {
        return sortFilePath;
    }

    public void setSortFilePath(Path sortFilePath) {
        requireNonNull(sortFilePath);
        this.sortFilePath = sortFilePath;
    }

    public Path getAliasFilePath() {
        return aliasFilePath;
    }

    public void setAliasFilePath(Path aliasFilePath) {
        requireNonNull(aliasFilePath);
        this.aliasFilePath = aliasFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && menuFilePath.equals(o.menuFilePath)
                && savingsHistoryFilePath.equals(savingsHistoryFilePath)
                && recsFilePath.equals(o.recsFilePath)
                && purchaseHistoryFilePath.equals(o.purchaseHistoryFilePath)
                && sortFilePath.equals(o.sortFilePath)
                && aliasFilePath.equals(o.aliasFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, menuFilePath, savingsHistoryFilePath,
                recsFilePath, purchaseHistoryFilePath, sortFilePath, aliasFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + menuFilePath);
        sb.append("\nLocal savings account data file location: " + savingsHistoryFilePath);
        sb.append("\nRecommendations data file location : " + recsFilePath);
        sb.append("\nPurchase History data file location : " + purchaseHistoryFilePath);
        sb.append("\nCustomSort data file location : " + sortFilePath);
        sb.append("\nAliasList data file location : " + aliasFilePath);
        return sb.toString();
    }

}

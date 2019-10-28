package seedu.savenus.model;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.commandhistory.CommandHistory;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.FoodFilter;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.purchase.ReadOnlyPurchaseHistory;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.ReadOnlyUserPrefs;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.model.wallet.exceptions.BudgetAmountOutOfBoundsException;
import seedu.savenus.model.wallet.exceptions.BudgetDurationOutOfBoundsException;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

/**
 * Represents the in-memory model of the menu data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final Menu menu;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;
    private final PurchaseHistory purchaseHistory;
    private final Wallet wallet;
    private final CustomSorter customSorter;
    private final SavingsAccount savingsAccount;
    private boolean autoSortFlag;

    /**
     * Initializes a ModelManager with the given menu and userPrefs.
     */
    public ModelManager(ReadOnlyMenu menu, ReadOnlyUserPrefs userPrefs, UserRecommendations userRecs,
                        ReadOnlyPurchaseHistory purchaseHistory, Wallet wallet,
                        CustomSorter customSorter, ReadOnlySavingsAccount savingsAccount) {
        super();
        requireAllNonNull(menu, userPrefs);

        logger.fine("Initializing with $aveNUS menu: " + menu + " and user prefs " + userPrefs);

        this.menu = new Menu(menu);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.menu.getFoodList());
        this.purchaseHistory = new PurchaseHistory(purchaseHistory);
        this.wallet = wallet;
        this.savingsAccount = new SavingsAccount(savingsAccount);
        this.customSorter = customSorter;
        this.autoSortFlag = false;
        RecommendationSystem.getInstance().setUserRecommendations(userRecs);
    }

    public ModelManager() {
        this(new Menu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getMenuFilePath() {
        return userPrefs.getMenuFilePath();
    }

    @Override
    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setMenuFilePath(menuFilePath);
    }

    @Override
    public Path getPurchaseHistoryFilePath() {
        return userPrefs.getPurchaseHistoryFilePath();
    }

    @Override
    public void setPurchaseHistoryFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setPurchaseHistoryFilePath(menuFilePath);
    }

    @Override
    public Path getWalletFilePath() {
        return userPrefs.getWalletFilePath();
    }

    @Override
    public void setWalletFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setWalletFilePath(menuFilePath);
    }


    //=========== Menu ================================================================================

    @Override
    public void setMenu(ReadOnlyMenu menu) {
        this.menu.resetData(menu);
    }

    @Override
    public ReadOnlyMenu getMenu() {
        return menu;
    }

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return menu.hasFood(food);
    }

    @Override
    public void deleteFood(Food target) {
        menu.removeFood(target);
    }

    @Override
    public void addFood(Food food) {
        menu.addFood(food);
        updateFilteredFoodList(PREDICATE_SHOW_ALL_FOOD);
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);
        menu.setFood(target, editedFood);
    }

    @Override
    public void setFoods(List<Food> list) {
        requireNonNull(list);
        menu.setFoods(list);
    }

    @Override
    public ObservableList<Food> getFoods() {
        return menu.getFoodList();
    }

    @Override
    public void buyFood(Food foodToBuy) throws InsufficientFundsException {
        requireNonNull(foodToBuy);
        this.deductFromWallet(foodToBuy.getPrice());
        Purchase purchaseToAdd = new Purchase(foodToBuy);
        this.addPurchase(purchaseToAdd);
    }

    //=========== PurchaseHistory Methods =========================================================================

    @Override
    public ReadOnlyPurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    @Override
    public void setPurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) {
        this.purchaseHistory.resetData(purchaseHistory);
    }

    @Override
    public void addPurchase(Purchase target) {
        purchaseHistory.addPurchase(target);
    }

    @Override
    public void removePurchase(Purchase target) {
        purchaseHistory.removePurchase(target);
    }

    //=========== PurchaseHistory List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the {@code PurchaseHistory} backed by the internal list of
     * {@code versionedMenu}
     */
    @Override
    public ObservableList<Purchase> getPurchaseHistoryList() {
        return purchaseHistory.getPurchaseHistoryList();
    }

    //=========== Wallet Accessors =========================================================================

    /**
     * Get user's {@code Wallet}.
     */
    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public void setWallet(RemainingBudget newRemainingBudget, DaysToExpire newDaysToExpire)
            throws BudgetDurationOutOfBoundsException, BudgetAmountOutOfBoundsException {
        requireNonNull(newRemainingBudget);
        wallet.setRemainingBudget(newRemainingBudget);
        requireNonNull(newDaysToExpire);
        wallet.setDaysToExpire(newDaysToExpire);
    }

    @Override
    public void deductFromWallet(Price priceToDeduct) throws InsufficientFundsException {
        requireNonNull(priceToDeduct);
        wallet.deduct(priceToDeduct);
    }

    @Override
    public void deductFromWallet(Savings savings) throws InsufficientFundsException {
        requireNonNull(savings);
        wallet.deduct(savings);
    }

    //=========== Filtered Food List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Food} backed by the internal list of
     * {@code versionedMenu}
     */
    @Override
    public ObservableList<Food> getFilteredFoodList() {
        // Update the Recommendation System's purchase history and budget
        RecommendationSystem.getInstance().updatePurchaseHistory(purchaseHistory.getPurchaseHistoryList());
        RecommendationSystem.getInstance().updateDaysToExpire(getWallet().getNumberOfDaysToExpire());
        RecommendationSystem.getInstance().updateBudget(getWallet().getRemainingBudgetAmount());

        return filteredFoods
                .filtered(RecommendationSystem.getInstance().getRecommendationPredicate())
                .sorted(RecommendationSystem.getInstance().getRecommendationComparator());
    }


    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    @Override
    public void editFilteredFoodList(List<String> fieldList) {
        requireNonNull(fieldList);
        filteredFoods.setPredicate(new FoodFilter(fieldList));
    }

    //=========== CustomSorter ========================================================================

    @Override
    public void setCustomSorter(List<String> fields) {
        customSorter.setComparator(fields);
    }

    @Override
    public CustomSorter getCustomSorter() {
        return customSorter;
    }

    @Override
    public void setAutoSortFlag(boolean autoSortFlag) {
        this.autoSortFlag = autoSortFlag;
    }

    @Override
    public boolean getAutoSortFlag() {
        return this.autoSortFlag;
    }

    //=========== Recommendation System =============================================================
    @Override
    public RecommendationSystem getRecommendationSystem() {
        return RecommendationSystem.getInstance();
    }

    @Override
    public void setRecommendationSystemInUse(boolean inUse) {
        RecommendationSystem.getInstance().setInUse(inUse);
    }

    @Override
    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        RecommendationSystem.getInstance().addLikes(categoryList, tagList, locationList);
    }

    @Override
    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        RecommendationSystem.getInstance().addDislikes(categoryList, tagList, locationList);
    }

    @Override
    public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        RecommendationSystem.getInstance().removeLikes(categoryList, tagList, locationList);
    }

    @Override
    public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        RecommendationSystem.getInstance().removeDislikes(categoryList, tagList, locationList);
    }

    @Override
    public void clearLikes() {
        RecommendationSystem.getInstance().clearLikes();
    }

    @Override
    public void clearDislikes() {
        RecommendationSystem.getInstance().clearDislikes();
    }

    @Override
    public void addToSavings(Savings savings) {
        requireNonNull(savings);
        savingsAccount.addSavings(savings);
    }


    /**
     * Returns an unmodifiable Savings Account of the user.
     */
    @Override
    public ReadOnlySavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    @Override
    public void setSavingsAccount(ReadOnlySavingsAccount savingsAccount) {
        this.savingsAccount.resetData(savingsAccount);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return menu.equals(other.menu)
                && userPrefs.equals(other.userPrefs)
                && filteredFoods.equals(other.filteredFoods)
                && purchaseHistory.equals(other.purchaseHistory)
                && wallet.equals(other.wallet)
                && customSorter.equals(other.customSorter)
                && savingsAccount.equals(other.savingsAccount);
    }

    @Override
    public List<String> getCommandHistory() {
        return CommandHistory.getInstance().getCommandHistory();
    }
}

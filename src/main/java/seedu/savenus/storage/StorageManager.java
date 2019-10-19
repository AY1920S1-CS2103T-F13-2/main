package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyPurchaseHistory;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;

/**
 * Manages storage of $aveNUS Menu Food data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MenuStorage menuStorage;
    private UserPrefsStorage userPrefsStorage;
    private RecsStorage userRecsStorage;
    private PurchaseHistoryStorage purchaseHistoryStorage;

    public StorageManager(MenuStorage menuStorage, UserPrefsStorage userPrefsStorage, RecsStorage userRecsStorage,
                          PurchaseHistoryStorage purchaseHistoryStorage) {
        super();
        this.menuStorage = menuStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userRecsStorage = userRecsStorage;
        this.purchaseHistoryStorage = purchaseHistoryStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Menu methods ==============================

    @Override
    public Path getMenuFilePath() {
        return menuStorage.getMenuFilePath();
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(menuStorage.getMenuFilePath());
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return menuStorage.readMenu(filePath);
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, menuStorage.getMenuFilePath());
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        menuStorage.saveMenu(menu, filePath);
    }

    // =============== Recommendation methods ========================
    @Override
    public Path getRecsFilePath() {
        return userRecsStorage.getRecsFilePath();
    }

    @Override
    public Optional<UserRecommendations> readRecs() throws DataConversionException, IOException {
        return readRecs(userRecsStorage.getRecsFilePath());
    }

    @Override
    public Optional<UserRecommendations> readRecs(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read recommendations data from file: " + filePath);
        return userRecsStorage.readRecs(filePath);
    }

    @Override
    public void saveRecs(UserRecommendations recs) throws IOException {
        saveRecs(recs, userRecsStorage.getRecsFilePath());
    }

    @Override
    public void saveRecs(UserRecommendations recs, Path filePath) throws IOException {
        logger.fine("Attempting to write recommendations to data file: " + filePath);
        userRecsStorage.saveRecs(recs, filePath);
    }

    // =============== PurchaseHistory methods ========================
    @Override
    public Path getPurchaseHistoryFilePath() {
        return purchaseHistoryStorage.getPurchaseHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory() throws DataConversionException, IOException {
        return readPurchaseHistory(purchaseHistoryStorage.getPurchaseHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read purchase history data from file: " + filePath);
        return purchaseHistoryStorage.readPurchaseHistory(filePath);
    }

    @Override
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) throws IOException {
        savePurchaseHistory(purchaseHistory, purchaseHistoryStorage.getPurchaseHistoryFilePath());
    }

    @Override
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory, Path filePath) throws IOException {
        logger.fine("Attempting to write purchase history to data file: " + filePath);
        purchaseHistoryStorage.savePurchaseHistory(purchaseHistory, filePath);
    }
}

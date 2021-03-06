package seedu.savenus.storage.recommend;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.recommend.UserRecommendations;

//@@author jon-chua
/**
 * Represents a storage for {@link UserRecommendations}.
 */
public interface RecsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRecsFilePath();

    /**
     * Returns Recommendation data as a {@link UserRecommendations}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserRecommendations> readRecs() throws DataConversionException, IOException;

    /**
     * @see #getRecsFilePath()
     */
    Optional<UserRecommendations> readRecs(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UserRecommendations} to the storage.
     * @param recs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecs(UserRecommendations recs) throws IOException;

    /**
     * @see #saveRecs(UserRecommendations)
     */
    void saveRecs(UserRecommendations recs, Path filePath) throws IOException;

}

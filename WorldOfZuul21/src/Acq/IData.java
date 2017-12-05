package Acq;

import java.util.Map;

/**
 * Data Facade
 */
public interface IData {
    /**
     * Get a map containing the loaded game.
     *
     * @return map of strings.
     */
    Map<String, String> load();

    /**
     * Save a map of strings.
     *
     * @param map containging variable names and their values.
     */
    void save(Map<String, String> map);

    /**
     * Get the highscore as a map with name as key, and point as value.
     *
     * @return map of highscores.
     */
    Map<String, String> loadHighScore();

    /**
     * Save a map of highscores.
     *
     * @param map, playername as key, point as value.
     */
    void saveHighScore(Map<String, String> map);

    /**
     * Delete the save game file
     *
     * @return true if the file was successfully deleted
     */
    boolean deleteFile();

    /**
     * Does the highscore file exist?
     *
     * @return true if the file exists
     */
    boolean doesHighScoreFileExist();

    /**
     * does the save game file exist?
     *
     * @return true if the save game file exists
     */
    boolean doesGameSaveFileExist();

    /**
     * Save the seen status of minimap items
     *
     * @param mapToSave, item name as key, seen status as value
     */
    void saveSeenStatus(Map<String, String> mapToSave);

    /**
     * get the saved seen status
     *
     * @return a map, item name as key, seen status as value
     */
    Map<String, String> loadSeenStatus();
}

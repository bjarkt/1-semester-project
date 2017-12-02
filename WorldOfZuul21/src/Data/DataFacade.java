package Data;

import Acq.IData;

import java.util.Map;

public class DataFacade implements IData {
    private LoadableSavable highScoreSaverLoader;
    private LoadableSavable gameSaverLoader;

    public DataFacade() {
        highScoreSaverLoader = new XMLUtilities("highscore.xml");
        gameSaverLoader = new XMLUtilities("savegame.xml");
    }

    /**
     * Save a game
     *
     * @param mapToSave the map to save
     */
    public void save(Map<String, String> mapToSave) {
        gameSaverLoader.save(mapToSave);
    }

    /**
     * save a highscore
     *
     * @param mapToSave the map to save
     */
    public void saveHighScore(Map<String, String> mapToSave) {
        highScoreSaverLoader.save(mapToSave);
    }

    /**
     * delete the game save file
     *
     * @return if the file was deleted successfully
     */
    @Override
    public boolean deleteFile() {
        return gameSaverLoader.deleteFile();
    }

    /**
     * @return if the highscore file exists
     */
    @Override
    public boolean doesHighScoreFileExist() {
        return highScoreSaverLoader.doesFileExist();
    }

    /**
     * @return if the game save file exists
     */
    @Override
    public boolean doesGameSaveFileExist() {
        return gameSaverLoader.doesFileExist();
    }

    /**
     * load the saved game
     *
     * @return a map of the saved game
     */
    public Map<String, String> load() {
        if (gameSaverLoader.doesFileExist()) {
            return gameSaverLoader.load();
        } else {
            return null;
        }
    }

    /**
     * load the high score
     *
     * @return a map of the highscore
     */
    public Map<String, String> loadHighScore() {
        if (highScoreSaverLoader.doesFileExist()) {
            return highScoreSaverLoader.load();
        } else {
            return null;
        }
    }
}

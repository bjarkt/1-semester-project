package Data;

import Acq.IData;

import java.util.Map;

public class DataFacade implements IData {
    private LoadableSavable highScoreSaverLoader;
    private LoadableSavable gameSaverLoader;
    private LoadableSavable seenStatusSaverLoader;

    public DataFacade() {
        highScoreSaverLoader = new XMLUtilities("highscore.xml");
        gameSaverLoader = new XMLUtilities("savegame.xml");
        seenStatusSaverLoader = new XMLUtilities("seenStatus.xml");
    }

    public void save(Map<String, String> mapToSave) {
        gameSaverLoader.save(mapToSave);
    }

    public void saveHighScore(Map<String, String> mapToSave) {
        highScoreSaverLoader.save(mapToSave);
    }

    @Override
    public boolean deleteFile() {
        return gameSaverLoader.deleteFile();
    }

    @Override
    public boolean doesHighScoreFileExist() {
        return highScoreSaverLoader.doesFileExist();
    }

    @Override
    public boolean doesGameSaveFileExist() {
        return gameSaverLoader.doesFileExist();
    }

    public Map<String, String> load() {
        if (gameSaverLoader.doesFileExist()) {
            return gameSaverLoader.load();
        } else {
            return null;
        }
    }

    public Map<String, String> loadHighScore() {
        if (highScoreSaverLoader.doesFileExist()) {
            return highScoreSaverLoader.load();
        } else {
            return null;
        }
    }

    @Override
    public void saveSeenStatus(Map<String, String> mapToSave) {
        seenStatusSaverLoader.save(mapToSave);
    }

    @Override
    public Map<String, String> loadSeenStatus() {
        if (seenStatusSaverLoader.doesFileExist()) {
            return seenStatusSaverLoader.load();
        } else {
            return null;
        }
    }
}

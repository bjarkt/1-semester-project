package Acq;

import java.util.Map;

public interface IData {
    Map<String, String> load();
    void save(Map<String, String> map);
    Map<String, String> loadHighScore();
    void saveHighScore(Map<String, String> map);
    boolean deleteFile();
    boolean doesFileExist();
}

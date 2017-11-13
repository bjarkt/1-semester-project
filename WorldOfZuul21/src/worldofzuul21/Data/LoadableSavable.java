package worldofzuul21.Data;

import java.io.IOException;
import java.util.Map;

public interface LoadableSavable {
    Map<String, String> load();
    void save(Map<String, String> map);
    boolean deleteFile();
    boolean doesFileExist();

}

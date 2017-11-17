package Data;

import java.util.Map;

public interface LoadableSavable {
    Map<String, String> load();
    void save(Map<String, String> map);
    boolean deleteFile();
    boolean doesFileExist();

}

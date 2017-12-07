package Data;

import java.util.Map;

interface LoadableSavable {
    /**
     * Load some data into a map
     *
     * @return return the file data as a map of String, String
     */
    Map<String, String> load();

    /**
     * save a map.
     *
     * @param map map of strings.
     */
    void save(Map<String, String> map);

    /**
     * Delete the file.
     *
     * @return true if the file was successfully deleted.
     */
    boolean deleteFile();

    /**
     * Does the file exist?
     *
     * @return true if the file exists.
     */
    boolean doesFileExist();

}

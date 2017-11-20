package Acq;

import java.util.HashMap;
import java.util.List;

public interface IBusiness {
    ILocation getCurrentLocation();
    ILocation[] getGuardLocations();

    void updateHighScore();
    List<IHighScore> getHighScores();

    IItem getItem();
    List<IItem> getInventoryList();

    void save();
    HashMap<String, String> load();

    void goDirection(Direction direction);
    void steal();
    void interact();
    void hide();
    void escape();
    HashMap<Integer, IRoom> getRooms();

    String callFriendlyNpc();

    void injectData(IData data);
}

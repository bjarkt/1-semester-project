package Acq;

import java.util.HashMap;
import java.util.List;

public interface IBusiness {
    ILocation getCurrentLocation();
    boolean isAtEntrance();
    ILocation[] getGuardLocations();

    void updateHighScore(String playerName);
    List<IHighScore> getHighScores();

    IItem getItem();
    List<IItem> getInventoryList();

    void save();
    //HashMap<String, String> load();
    void load();

    void goDirection(Direction direction);
    void steal();
    void interact();
    void hide();
    void escape(boolean wantToGoBackInside);
    HashMap<Integer, IRoom> getRooms();

    String callFriendlyNpc();

    void injectData(IData data);
}

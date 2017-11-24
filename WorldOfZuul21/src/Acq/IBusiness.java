package Acq;

import java.util.HashMap;
import java.util.List;

public interface IBusiness {
    ILocation getCurrentLocation();
    boolean isAtEntrance();

    ILocation[] getGuardLocations();
    ILocation[] getPowerRelayLocations();
    ILocation getPowerSwitchLocation();
    ILocation getItemLocation();

    int getRoundsLeftBeforePowerTurnsOn();

    boolean currentRoomContainsItem();
    boolean currentRoomContainsPowerSwitch();
    boolean currentRoomContainsPowerRelay();

    void updateHighScore(String playerName);
    List<IHighScore> getHighScores();

    IItem getItem();
    List<IItem> getInventoryList();

    void save();
    void load();

    void goDirection(Direction direction);
    void steal();
    void interact();
    void hide();
    void escape(boolean wantToGoBackInside);
    List<IRoom> getRooms();

    String callFriendlyNpc();

    void injectData(IData data);
}

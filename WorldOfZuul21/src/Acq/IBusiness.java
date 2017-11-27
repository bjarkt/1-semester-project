package Acq;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    Set<Direction> getExitsForCurrentRoom();

    void updateHighScore(String playerName);
    List<IHighScore> getHighScores();
    int getCurrentHighScore();

    boolean getCheatMode();
    void toggleCheatMode();

    boolean getPowerStatus();
    boolean getPoliceAlerted();
    boolean isGotBusted();
    boolean getPolicedArrived();

    IItem getItem();
    List<IItem> getInventoryList();
    List<IItem> getLootList();

    void save();
    void load();

    boolean goDirection(Direction direction);
    void steal();
    void interact();
    void hide();
    void escape(boolean wantToGoBackInside);
    List<IRoom> getRooms();

    String callFriendlyNpc();

    void injectData(IData data);
}

package Acq;

import Business.BooleanMessage;

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

    boolean deleteSaveGameFile();

    IItem getItemForCurrentRoom();
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
    int getTimeBeforePowerTurnsBackOn();
    int getTimeBeforePoliceArrives();
    String getGlobalMessage();
    void clearGlobalMessage();

    List<IItem> getInventoryList();
    List<IItem> getLootList();

    void save(String playerName);
    void load();
    void restartGame(); 
    String getLoadedPlayerName();
    boolean doesGameSaveFileExist();

    IBooleanMessage goDirection(Direction direction);
    boolean steal();
    IBooleanMessage interact();
    boolean hide();
    void escape(boolean wantToGoBackInside);
    List<IRoom> getRooms();

    String callMasterMindDaniel();
    String callFriendlyNPC();

    void injectData(IData data);  
}

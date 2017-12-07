package Business;

import Acq.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Business facade to facilitate communication between presentation layer and business layer.
 */
public class BusinessFacade implements IBusiness {

    /**
     * The game.
     */
    private Game game;

    /**
     * HighScoreManager handles highscores.
     */
    private HighScoreManager highScoreManager;

    /**
     * Access to the data layer.
     */
    private IData data;

    /**
     * Create a new business facade, and instantiate the game and highscore manager.
     */
    public BusinessFacade() {
        game = new Game(false);
        highScoreManager = new HighScoreManager();
    }

    @Override
    public void restartGame() {
        game = new Game(false);
        game.injectData(data);
    }

    @Override
    public ILocation getCurrentLocation() {
        return game.getCurrentRoom().getLocation();
    }

    @Override
    public boolean isAtEntrance() {
        return game.isAtEntrace();
    }

    @Override
    public ILocation[] getGuardLocations() {
        ILocation g1Location = game.getGuards()[0].getRoom().getLocation();
        ILocation g2Location = game.getGuards()[1].getRoom().getLocation();

        return new ILocation[]{g1Location, g2Location};
    }

    @Override
    public ILocation[] getPowerRelayLocations() {
        List<Room> relayRooms = new ArrayList<>(game.getPowerRelayLocations());
        ILocation[] relayLocations = new ILocation[relayRooms.size()];
        for (int i = 0; i < relayRooms.size(); i++) {
            relayLocations[i] = relayRooms.get(i).getLocation();
        }
        return relayLocations;
    }

    @Override
    public IPowerRelay[] getPowerRelays() {
        return game.getPowerRelays();
    }

    @Override
    public ILocation getPowerSwitchLocation() {
        for (Room room : game.getSwitchSpawnPointRooms()) {
            if (room.getPowerSwitch() != null) {
                return room.getLocation();
            }
        }
        return null;
    }

    @Override
    public ILocation getItemLocation() {
        for (Room room : game.getItemSpawnPointRooms()) {
            if (room.getItems() != null && !room.getItems().isKey()) {
                return room.getLocation();
            }
        }
        return null;
    }

    @Override
    public boolean currentRoomContainsItem() {
        return game.getCurrentRoom().getItems() != null && !game.getCurrentRoom().getItems().isKey();
    }

    @Override
    public boolean currentRoomContainsPowerSwitch() {
        return game.getCurrentRoom().getPowerSwitch() != null;
    }

    @Override
    public boolean currentRoomContainsPowerRelay() {
        return game.getCurrentRoom().getPowerRelay() != null;
    }

    @Override
    public Set<Direction> getExitsForCurrentRoom() {
        return game.getCurrentRoom().getExits().keySet();
    }

    @Override
    public void updateHighScore(String playerName) {
        highScoreManager.updateHighScore(game.getInventory().calculatePoints(), playerName);
    }

    @Override
    public List<IHighScore> getHighScores() {
        return highScoreManager.getHighScores();
    }

    @Override
    public int getCurrentHighScore() {
        return game.getInventory().calculatePoints();
    }

    @Override
    public boolean getPowerStatus() {
        return game.isPowerStatus();
    }

    @Override
    public boolean getPoliceAlerted() {
        return game.isPoliceAlerted();
    }

    @Override
    public boolean isGotBusted() {
        return game.isGotBusted();
    }

    @Override
    public boolean getPolicedArrived() {
        return game.isPoliceArrived();
    }

    @Override
    public int getTimeBeforePowerTurnsBackOn() {
        return game.getTimeBeforePowerTurnsBackOn();
    }

    @Override
    public int getTimeBeforePoliceArrives() {
        return game.getTimeBeforePoliceArrives();
    }

    @Override
    public List<IItem> getInventoryList() {
        return new ArrayList<>(game.getInventory().getInventory());
    }

    public List<IItem> getLootList() {
        return new ArrayList<>(game.getInventory().getLoot());
    }

    @Override
    public void save(String playerName) {
        game.save(playerName);
    }

    @Override
    public void load() {
        game.load();
    }

    @Override
    public String getLoadedPlayerName() {
        Map<String, String> loadedMap = data.load();
        data.deleteFile();
        return loadedMap.get("playerName");
    }

    @Override
    public IBooleanMessage goDirection(Direction direction) {
        Command command = new Command(CommandWord.GO, direction.toString());
        return game.goRoom(command);
    }

    @Override
    public IBooleanMessage steal() {
        return game.stealItem();
    }

    @Override
    public IBooleanMessage interact() {
        return game.interact();
    }

    @Override
    public boolean hide() {
        return game.hide();
    }

    @Override
    public void escape(boolean wantToGoBackInside) {
        Command command;
        if (wantToGoBackInside) {
            command = new Command(CommandWord.YES, "");
        } else {
            command = new Command(CommandWord.NO, "");
        }
        game.escape(command);
    }

    @Override
    public List<IRoom> getRooms() {
        return new ArrayList<>(game.getRooms());
    }

    @Override
    public String callMasterMindDaniel() {
        return game.call();
    }

    @Override
    public String callFriendlyNPC() {
        return game.callFriendlyNpc();
    }

    @Override
    public IItem getItemForCurrentRoom() {
        return game.getCurrentRoom().getItems();
    }

    @Override
    public boolean doesGameSaveFileExist() {
        return data.doesGameSaveFileExist();
    }

    @Override
    public boolean deleteSaveGameFile() {
        return data.deleteFile();
    }

    @Override
    public String getGlobalMessage() {
        return game.getGlobalMessage();
    }

    @Override
    public void clearGlobalMessage() {
        game.setGlobalMessage("");
    }

    @Override
    public ILocation newLocation(double x, double y) {
        return new Location(x, y);
    }

    @Override
    public void saveSeenStatus(Map<String, String> mapToSave) {
        data.saveSeenStatus(mapToSave);
    }

    @Override
    public Map<String, String> loadSeenStatus() {
        return data.loadSeenStatus();
    }

    @Override
    public void injectData(IData data) {
        this.data = data;
        highScoreManager.injectData(data);
        game.injectData(data);
    }
}

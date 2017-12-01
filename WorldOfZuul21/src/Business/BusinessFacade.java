package Business;

import Acq.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BusinessFacade implements IBusiness {

    private Game game;
    private HighScoreManager highScoreManager;
    private IData data;

    public BusinessFacade() {
        game = new Game();
        highScoreManager = new HighScoreManager();

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
    public int getRoundsLeftBeforePowerTurnsOn() {
        return game.getPowerOffTime() / 2;
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
    public boolean getCheatMode() {
        return game.isCheatMode();
    }

    @Override
    public void toggleCheatMode() {
        game.toggleCheatMode();
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
    public int getPowerOffTime() {
        return game.getPowerOffTime();
    }

    @Override
    public int getTimeBeforePowerTurnsBackOn() {
        return game.getTimeBeforePowerTurnsBackOn();
    }

    @Override
    public IItem getItem() {
        for (Room room : game.getItemSpawnPointRooms()) {
            if (room.getItems() != null) {
                return room.getItems();
            }
        }
        return null;
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
        IBooleanMessage message = game.goRoom(command);
        return message;
    }

    @Override
    public boolean steal() {
        return game.stealItem();
    }

    @Override
    public IBooleanMessage interact() {
        return game.interact();
    }

    public boolean hide() {
        return game.hide();
    }

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
    public boolean doesGameSaveFileExist() {
        return data.doesGameSaveFileExist();
    }

    @Override
    public void injectData(IData data) {
        this.data = data;
        highScoreManager.injectData(data);
        game.injectData(data);
    }
}

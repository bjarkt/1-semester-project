package Business;

import Acq.*;

import java.util.HashMap;
import java.util.List;

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
    public void updateHighScore(String playerName) {
        highScoreManager.updateHighScore(game.getInventory().calculatePoints(), playerName);
    }

    @Override
    public List<IHighScore> getHighScores() {
        return highScoreManager.getHighScores();
    }

    @Override
    public IItem getItem() {
        for (IRoom room : game.getItemSpawnPointRooms()) {
            if (room.getItems() != null) {
                return room.getItems();
            }
        }
        return null;
    }

    @Override
    public List<IItem> getInventoryList() {
        return game.getInventory().getInventory();
    }

    @Override
    public void save() {
        game.save();
    }

    @Override
    public void load() {
        game.load();
    }

    @Override
    public void goDirection(Direction direction) {
        Command command = new Command(CommandWord.GO, direction.toString());
        game.goRoom(command);
    }

    @Override
    public void steal() {
        game.stealItem();
    }

    @Override
    public void interact() {
        game.interact();
    }

    public void hide() {
        game.hide();
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
    public HashMap<Integer, IRoom> getRooms() {
        return game.getRooms();
    }

    @Override
    public String callFriendlyNpc() {
        return game.call();
    }

    @Override
    public void injectData(IData data) {
        this.data = data;
        highScoreManager.injectData(data);
        game.injectData(data);
    }
}

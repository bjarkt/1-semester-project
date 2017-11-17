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
    public ILocation[] getGuardLocations() {
        ILocation g1Location = game.getGuards()[0].getRoom().getLocation();
        ILocation g2Location = game.getGuards()[1].getRoom().getLocation();

        return new ILocation[]{g1Location, g2Location};
    }

    @Override
    public void updateHighScore() {
        highScoreManager.updateHighScore(game.getInventory().calculatePoints());
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
    public void save() {
        game.save(); // TODO fix det
    }

    @Override
    public HashMap<String, String> load() {
        game.load(); // TODO fix det
        return null;
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
    }
}

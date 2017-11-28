import Acq.IData;
import Business.Game;
import Data.DataFacade;

public class StartText {
    public static void main(String[] args) {
        IData data = new DataFacade();
        Game game = new Game();
        game.injectData(data);
        game.startGame();
    }
}

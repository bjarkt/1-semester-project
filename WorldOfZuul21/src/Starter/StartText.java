package Starter;

import Acq.IData;
import Business.Game;
import Data.DataFacade;

public class StartText {
    public static void main(String[] args) {
        // Create data
        IData data = new DataFacade();

        // Create the game
        Game game = new Game(true);

        // Inject data facade into the game
        game.injectData(data);

        // Start the game
        game.startGame();
    }
}

package Business;

import Acq.IData;
import Acq.IHighScore;
import Data.LoadableSavable;
import Data.XMLUtilities;

import java.util.*;

class HighScoreManager {

    private IData data;

    HighScoreManager() {
    }

    List<IHighScore> getHighScores() {
        Map<String, String> loadedHighScoreMap = new HashMap<>();
        List<IHighScore> highScoreList = new ArrayList<>();

        if (data.doesHighScoreFileExist()) {
            loadedHighScoreMap = data.loadHighScore();
        }

        for (Map.Entry<String, String> entry : loadedHighScoreMap.entrySet()) {
            highScoreList.add(new HighScore(entry.getKey(), Integer.parseInt(entry.getValue())));
        }

        Collections.sort(highScoreList);
        Collections.reverse(highScoreList);
        if (highScoreList.size() > 5) {
            return highScoreList.subList(0, 5);
        } else {
            return highScoreList;
        }
    }

    void updateHighScore(int points, String playerName) {
        Map<String, String> loadedHighScoreMap = new HashMap<>();

        if (data.doesGameSaveFileExist()) {
            loadedHighScoreMap = data.loadHighScore();
        }
        //System.out.println("Whats your name?");
        //Scanner input = new Scanner(System.in);
        //String name = input.next();

        loadedHighScoreMap.put(playerName, String.valueOf(points));

        data.saveHighScore(loadedHighScoreMap);
        System.out.println(loadedHighScoreMap);
    }

    void injectData(IData data) {
        this.data = data;
    }
}

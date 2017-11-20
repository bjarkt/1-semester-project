package Business;

import Acq.IData;
import Acq.IHighScore;
import Data.LoadableSavable;
import Data.XMLUtilities;

import java.util.*;

public class HighScoreManager {
    //LoadableSavable highScoreSaverLoader; // TODO fix her

    IData data;

    public HighScoreManager() {
        //highScoreSaverLoader = new XMLUtilities("highscore.xml"); // TODO fix det her
    }

    public List<IHighScore> getHighScores() {
        Map<String, String> loadedHighScoreMap = new HashMap<>();
        List<IHighScore> highScoreList = new ArrayList<>();

        if (data.doesFileExist()) {
            loadedHighScoreMap = data.load();
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

    public void updateHighScore(int points, String playerName) {
        Map<String, String> loadedHighScoreMap = new HashMap<>();

        if (data.doesFileExist()) {
            loadedHighScoreMap = data.load();
        }
        //System.out.println("Whats your name?");
        //Scanner input = new Scanner(System.in);
        //String name = input.next();

        loadedHighScoreMap.put(playerName, String.valueOf(points));

        data.save(loadedHighScoreMap);

        //System.out.println(getHighScores());
    }

    public void injectData(IData data) {
        this.data = data;
    }
}

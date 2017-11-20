package Business;

import Acq.IData;
import Acq.IHighScore;
import Data.LoadableSavable;
import Data.XMLUtilities;

import java.util.*;

public class HighScoreManager {
    LoadableSavable highScoreSaverLoader; // TODO fix her

    public HighScoreManager() {
        highScoreSaverLoader = new XMLUtilities("highscore.xml"); // TODO fix det her
    }

    public List<IHighScore> getHighScores() {
        Map<String, String> loadedHighScoreMap = new HashMap<>();
        List<IHighScore> highScoreList = new ArrayList<>();

        if (highScoreSaverLoader.doesFileExist()) {
            loadedHighScoreMap = highScoreSaverLoader.load();
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

        if (highScoreSaverLoader.doesFileExist()) {
            loadedHighScoreMap = highScoreSaverLoader.load();
        }
        //System.out.println("Whats your name?");
        //Scanner input = new Scanner(System.in);
        //String name = input.next();

        loadedHighScoreMap.put(playerName, String.valueOf(points));

        highScoreSaverLoader.save(loadedHighScoreMap);

        //System.out.println(getHighScores());
    }
}

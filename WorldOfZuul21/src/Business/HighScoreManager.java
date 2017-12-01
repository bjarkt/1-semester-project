package Business;

import Acq.IData;
import Acq.IHighScore;

import java.util.*;

class HighScoreManager {

    private IData data;

    HighScoreManager() {
    }

    /**
     *
     * @return A top five list of {@link IHighScore}
     */
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

    /**
     * saves the players highscore
     * @param points the amount of points the player got
     * @param playerName the name of the player
     */
    void updateHighScore(int points, String playerName) {
        Map<String, String> loadedHighScoreMap = new HashMap<>();

        if (data.doesHighScoreFileExist()) {
            loadedHighScoreMap = data.loadHighScore();
        }

        String loadedPlayersHighscore = loadedHighScoreMap.get(playerName);
        if (loadedPlayersHighscore != null) {
            // der findes allerede en spiller med det navn i highscoren
            int currentHighscore = Integer.valueOf(loadedPlayersHighscore);
            if (points > currentHighscore) { // hvis den nye score er højere end den gamle
                loadedHighScoreMap.put(playerName, String.valueOf(points)); // opdater scoren
            }
        } else {
            // der findes ikke en spiller med samme navn
            loadedHighScoreMap.put(playerName, String.valueOf(points));
        }

        data.saveHighScore(loadedHighScoreMap);
    }

    /**
     * injects data facade
     * @param data data facade
     */
    void injectData(IData data) {
        this.data = data;
    }
}

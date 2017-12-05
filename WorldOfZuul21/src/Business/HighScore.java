package Business;

import Acq.IHighScore;

/**
 * The HighScore object holds one highscore and one name. A list of highscores is made up of many of this object.
 */
public class HighScore implements IHighScore {
    private String name;
    private int score;

    /**
     * Creates a new highscore object from a name and a score
     *
     * @param name  name of the player
     * @param score the amount of points the player got
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * toString method for HighScore.
     *
     * @return String representation of the object. Name followed by the score.
     */
    @Override
    public String toString() {
        return name + " - " + score;
    }

    @Override
    public int compareTo(IHighScore other) {
        return this.score - other.getScore();
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getName() {
        return name;
    }
}

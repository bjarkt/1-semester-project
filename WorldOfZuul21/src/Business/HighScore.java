package Business;

import Acq.IHighScore;

public class HighScore implements IHighScore {
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

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

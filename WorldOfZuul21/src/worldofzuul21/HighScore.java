package worldofzuul21;

public class HighScore implements Comparable<HighScore> {
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
    public int compareTo(HighScore other) {
        return this.score - other.score;
    }
}

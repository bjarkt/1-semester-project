package Acq;

public interface IHighScore extends Comparable<IHighScore> {
    /**
     * Compare two highscores.
     *
     * @param other another highscore.
     * @return a negative int if, this < other.
     * 0 if, this == other.
     * a positive int if, this > other.
     */
    int compareTo(IHighScore other);

    /**
     * Get the score of this object.
     *
     * @return a score.
     */
    int getScore();

    /**
     * Get the name associated with this score.
     *
     * @return a name.
     */
    String getName();
}

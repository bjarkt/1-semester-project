package Acq;

public interface IHighScore extends Comparable<IHighScore> {
    int compareTo(IHighScore other);

    int getScore();
    String getName();
}

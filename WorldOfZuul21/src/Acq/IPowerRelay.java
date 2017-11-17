package Acq;

public interface IPowerRelay {
    boolean getStatus();
    void sabotage();
    int getTimeBoost();
    void restore();
    int getID();
    void setStatus(boolean status);
    void setID(int id);
}

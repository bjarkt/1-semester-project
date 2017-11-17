package Acq;

import Business.Spawnable;

public interface IRoom {
    void setSpawn(Spawnable obj);
    void setItem(IItem item);
    IItem getItems();
    IPowerRelay getPowerRelay();
    void setPowerSwitch(IPowerSwitch pw);
    void setPowerRelay(IPowerRelay pr);
    IPowerSwitch getPowerSwitch();
    void removeItem();
    void addGuard(IGuard g);
    IGuard[] getGuards();
    void removeGuard();

    void lock();
    void setLocked(boolean status);

    String getShortDescription();
    String getLongDescription();

    Integer getExit(Direction direction);
    void setExit(Direction direction, Integer neighbor);

    boolean isLocked();
    void unlock();

    ILocation getLocation();
    String getName();
}

package Acq;

public interface ILocation {
    int getXY();
    int getX();
    int getY();
    boolean isNextTo(ILocation loc);
    Direction getDirectionOfAdjacentLocation(ILocation loc);
}

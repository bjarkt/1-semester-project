/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.Direction;
import Acq.ILocation;

/**
 *
 * @author Nikolaj
 */
public class Location implements ILocation {

    private int x;
    private int y;
    private int xy;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        xy = Integer.parseInt("" + x + y);
    }

    public int getX() {
        // Method for getting X
        return x;
    }

    public int getY() {
        // Method for getting Y
        return y;
    }

    public int getXY() {
        // Method for getting XY
        return xy;
    }

    @Override
    public String toString() {
        // Method for returning xy as a string
        return xy + "";
    }

    // Not in use 
    @Override
    public int hashCode() {
        return (Integer.toString(x) + "," + Integer.toString(y)).hashCode();
    }

    public boolean isNextTo(Location loc) {
        if (this.x == loc.getX() + 1 || this.x == loc.getX() - 1) {
            if (this.y == loc.getY()) {
                return true;
            }
        } else if (this.y == loc.getY() + 1 || this.y == loc.getY() - 1) {
            if (this.x == loc.getX()) {
                return true;
            }
        }
        return false;
    }

    public Direction getDirectionOfAdjacentLocation(Location loc) {
        if (!isNextTo(loc)) {
            return null;
        } else {
            if (this.x < loc.getX()) {
                return Direction.EAST;
            } else if (this.x > loc.getX()) {
                return Direction.WEST;
            } else if (this.y < loc.getY()) {
                return Direction.NORTH;
            } else if (this.y > loc.getY()) {
                return Direction.SOUTH;
            } else {
                return null;
            }
        }
    }
}

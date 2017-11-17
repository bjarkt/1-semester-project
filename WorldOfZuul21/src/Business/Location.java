/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Nikolaj
 */
public class Location {

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
        if (this.x == loc.x + 1 || this.x == loc.x - 1) {
            if (this.y == loc.y) {
                return true;
            }
        } else if (this.y == loc.y + 1 || this.y == loc.y - 1) {
            if (this.x == loc.x) {
                return true;
            }
        }
        return false;
    }

    public Direction getDirectionOfAdjacentLocation(Location loc) {
        if (!isNextTo(loc)) {
            return null;
        } else {
            if (this.x < loc.x) {
                return Direction.EAST;
            } else if (this.x > loc.x) {
                return Direction.WEST;
            } else if (this.y < loc.y) {
                return Direction.NORTH;
            } else if (this.y > loc.y) {
                return Direction.SOUTH;
            } else {
                return null;
            }
        }
    }
}

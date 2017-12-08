/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.Direction;
import Acq.ILocation;

import java.awt.geom.Point2D;

/**
 * This class extends Point2D, so we can make our own additional methods
 * @author Nikolaj
 */
public class Location extends Point2D implements ILocation {

    private double x;
    private double y;

    /**
     * Create a new location, from a x and y value
     * @param x x value
     * @param y y value
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param loc another location
     * @return true if the this location is next to the other location
     */
    public boolean isNextTo(Location loc) {
        if (this.x == loc.getX() + 1 || this.x == loc.getX() - 1) {
            return this.y == loc.getY();
        } else if (this.y == loc.getY() + 1 || this.y == loc.getY() - 1) {
            return this.x == loc.getX();
        }
        return false;
    }

    /**
     * @param loc another location
     * @return the direction that the other location is, compared to this
     */
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

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Set the x and y value of this location
     * @param x x value
     * @param y y value
     */
    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }
}

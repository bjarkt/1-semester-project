/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.Direction;
import Acq.ILocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeppe
 */
public class FriendlyNpc {

    public String help(Location currenLocation, Guard[] guards) {
        StringBuilder s = new StringBuilder();
        for (Direction direction : getDirectionOfGuards(currenLocation, guards)) {
            if (s.toString().length() != 0) {
                s.append(System.lineSeparator());
            }
            s.append("Watch out, there is a guard ").append(direction).append(" of you!");
        }
        return s.toString();
    }

    public List<Direction> getDirectionOfGuards(Location currentLocation, Guard[] guards) {
        List<Direction> directions = new ArrayList<>();
        for (Guard guard : guards) {
            if (currentLocation.getX() == guard.getRoom().getLocation().getX()) {
                if (currentLocation.getY() + 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.NORTH);
                } else if (currentLocation.getY() - 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.SOUTH);
                }
            }

            if (currentLocation.getY() == guard.getRoom().getLocation().getY()) {
                if (currentLocation.getX() + 1 == guard.getRoom().getLocation().getX()) {
                    directions.add(Direction.EAST);
                } else if (currentLocation.getX() - 1 == guard.getRoom().getLocation().getX()) {
                    directions.add(Direction.WEST);
                }
            }

            if (currentLocation.getX() - 1 == guard.getRoom().getLocation().getX()) {
                if (currentLocation.getY() + 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.NORTHWEST);
                } else if (currentLocation.getY() - 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.SOUTHWEST);
                }
            }
            if (currentLocation.getX() + 1 == guard.getRoom().getLocation().getX()) {
                if (currentLocation.getY() + 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.NORTHEAST);
                } else if (currentLocation.getY() - 1 == guard.getRoom().getLocation().getY()) {
                    directions.add(Direction.SOUTHEAST);
                }
            }
        }
        return directions;
    }

}

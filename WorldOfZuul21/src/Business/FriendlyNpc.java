/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeppe
 * The friendly npc will tell the player where the guards are, compared to him.
 */
class FriendlyNpc {

    /**
     * Print the help text, which contains information about the guards location.
     *
     * @param currentLocation the location of the player
     * @param guards         an array of guards
     * @return description of the guards location compared to yours
     */
    public String help(Location currentLocation, Guard[] guards) {
        StringBuilder s = new StringBuilder();
        for (Direction direction : getDirectionOfGuards(currentLocation, guards)) {
            if (s.toString().length() != 0) {
                s.append(System.lineSeparator());
            }
            s.append("Watch out, there is a guard ").append(direction).append(" of you!");
        }
        return s.toString();
    }

    /**
     * Figures out where the guards are, compared to the current location of the player
     *
     * @param currentLocation current location of the player
     * @param guards          the guard array
     * @return a list of {@link Direction}s, which contains the direction of the guards
     */
    private List<Direction> getDirectionOfGuards(Location currentLocation, Guard[] guards) {
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

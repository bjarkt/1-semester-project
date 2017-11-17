/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeppe
 */
public class FriendlyNpc {

    public boolean checkForGuard(Location currentLocation, Guard[] guards) {
        for (Guard guard : guards) {
            if (currentLocation.getX() <= guard.getRoom().getLocation().getX() + 1
                    && currentLocation.getX() <= guard.getRoom().getLocation().getX() - 1
                    && currentLocation.getY() <= guard.getRoom().getLocation().getY() + 1
                    && currentLocation.getY() <= guard.getRoom().getLocation().getY() - 1) {
                return true;
            }
        }
        return false;
    }

    public List<Direction> getDirectionOfGuards(Location currentLocation, Guard[] guards) {
        if (checkForGuard(currentLocation, guards) == true) {
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
        return null;
    }

}

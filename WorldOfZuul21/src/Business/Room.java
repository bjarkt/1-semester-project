package Business;

import Acq.Direction;
import Acq.IRoom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room implements IRoom {

    private String name;
    private Location location;
    private String description;
    private String visualDescription;
    private String baseVisualDescription;
    private HashMap<Direction, Location> exits;
    private PowerSwitch powerSwitch;
    private PowerRelay powerRelay;
    private boolean locked;
    private Item[] items;
    private Guard[] guards;

    /**
     * creates a new room, with a name, description and location via x y
     * coordinates.
     *
     * @param name        name of room
     * @param description description of room
     * @param x           x location of room
     * @param y           y location of room
     */
    public Room(String name, String description, String visualDescription, int x, int y) {
        this.name = name;
        this.description = description;
        this.visualDescription = visualDescription;
        this.baseVisualDescription = this.visualDescription;
        location = new Location(x, y);
        exits = new HashMap<>();
        locked = false;
        items = new Item[1]; // only one item per room
        guards = new Guard[2]; // two guards per room
    }

    /**
     * Get the name of the room
     *
     * @return name of room
     */
    public String getName() {
        return name;
    }

    /**
     * adds a possible exit for this room.
     *
     * @param direction which direction the exit is
     * @param neighbor  the next room
     */
    private void setExit(Direction direction, Location neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Get the short description
     *
     * @return short description
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Get the long description
     *
     * @return long description
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + getPowerSwitchToString() + getPowerRelayToString() + getItemToString();
    }

    /**
     * Returns all the exits for the room as a string.
     *
     * @return String of exits
     */
    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        // Get the keys from the exits map (the strings).
        Set<Direction> keys = exits.keySet();
        for (Direction exit : keys) {
            // For each string, called exit, in the Set of keys, 
            // append it to the string.
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    /* Returns room, that has the corresponding direction. 
       Returns null if there is no room, for a certain direction. */

    /**
     * Get the exit location for the direction
     *
     * @param direction a direction
     * @return a location
     */
    public Location getExit(Direction direction) {
        return exits.get(direction);
    }

    /**
     * Get the location
     *
     * @return the location of this room
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Is the room locked?
     *
     * @return true if the room is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * lock the room (locked = true);
     */
    public void lock() {
        locked = true;
    }

    /**
     * unlock the room (locked = false);
     */
    public void unlock() {
        locked = false;
    }

    /**
     * set the locked var to the parameter
     *
     * @param lockStatus new locked status
     */
    public void setLocked(boolean lockStatus) {
        locked = lockStatus;
    }

    /**
     * Get the power switch
     *
     * @return the powerswitch placed in this room. Returns null if there is no
     * powerswitch.
     */
    public PowerSwitch getPowerSwitch() {
        return powerSwitch;
    }

    /**
     * Get the power relay
     *
     * @return return the powerrelay placed in this room, returns null if there is no powerrelay
     */
    public PowerRelay getPowerRelay() {
        return this.powerRelay;
    }

    /**
     * set the powerrelay of this room
     *
     * @param powerRelay a powerrelay object to place in this room
     */
    public void setPowerRelay(PowerRelay powerRelay) {
        if (powerRelay == null) {
            this.visualDescription = this.baseVisualDescription;
        } else {
            this.visualDescription = this.baseVisualDescription + "-" + "PowerRelay";
        }
        this.powerRelay = powerRelay;
    }

    /**
     * Get a string that will tell you, if there a power switch in the current room
     * @return a string, indicating if there is a powerswitch in this room.
     */
    private String getPowerSwitchToString() {
        if (this.powerSwitch
                == null) {
            return "";
        }
        if (!this.powerSwitch.getIsOn()) {
            return "";
        } else if (this.powerSwitch.getIsOn()) {
            // prints here, because there is a powerswitch in the room, and it is turned on
            return "\nThere is a powerswitch in here";
        } else {
            return "";
        }
    }

    /**
     * Sets a powerswitch in this room.
     *
     * @param powerSwitch a powerswitch
     */
    public void setPowerSwitch(PowerSwitch powerSwitch) {
        if (powerSwitch == null) {
            this.visualDescription = this.baseVisualDescription;
        } else {
            this.visualDescription = this.baseVisualDescription + "-" + "PowerSwitch";
        }
        this.powerSwitch = powerSwitch;
    }

    /**
     * Get a string that will tell you, if there a item in the current room
     * @return a string, indicating if there is an item in this room.
     */
    private String getItemToString() {
        if (items[0] != null) {
            return "\nThere is a " + items[0].getName() + " here";
        } else {
            return "";
        }
    }

    /**
     * Get the item for this room
     * @return the first item of the items array.
     */
    public Item getItems() {
        return items[0];
    }

    /**
     * sets the first item of the items array.
     *
     * @param item a item
     */
    public void setItem(Item item) {
        if (item == null) {
            this.visualDescription = this.baseVisualDescription;
        } else {
            this.visualDescription = this.baseVisualDescription + "-" + item.getName();
        }
        items[0] = item;
    }

    /**
     * removes the first item of the items array.
     */
    public void removeItem() {
        this.visualDescription = baseVisualDescription;
        items[0] = null;
    }

    /**
     * Get the guards in this room
     * @return an array containing the guards in this room
     */
    public Guard[] getGuards() {
        return guards;
    }

    /**
     * Adds a guard to this room. The method figures out which array index to
     * place the guard in.
     *
     * @param guard a guard
     */
    public void addGuard(Guard guard) {
        if (guards[0] != null) {
            guards[1] = guard;
        } else {
            guards[0] = guard;
        }
    }

    /**
     * removes guards from the room.
     */
    public void removeGuard() {
        if (guards[0] != null) {
            guards[0] = null;
        } else {
            guards[1] = null;
        }
    }

    /**
     * Get a string that will tell you, if there a power relay in the current room
     * @return a string indicating if there is a powerrelay in this room
     */
    private String getPowerRelayToString() {
        if (this.powerRelay == null) {
            return "";
        }
        if (!this.powerRelay.getStatus()) {
            return "";
        } else if (this.powerRelay.getStatus()) {
            // prints here, because there is a powerswitch in the room, and it is turned on
            return "\nThere is a powerrelay in here";
        } else {
            return "";
        }
    }

    /**
     * Place an item in this room
     * @param item item to set in this room
     */
    public void setSpawn(Item item) {
        this.setItem(item);
    }

    /**
     * Place a guard in this room
     * @param guard guard to set in this room
     */
    public void setSpawn(Guard guard) {
        this.addGuard(guard);
    }

    /**
     * Place a power relay in this room
     * @param pr powerrelay to set in this room
     */
    public void setSpawn(PowerRelay pr) {
        this.setPowerRelay(pr);
    }

    /**
     * Place a power switch in this room
     * @param pw powerswitch to set in this room
     */
    public void setSpawn(PowerSwitch pw) {
        this.setPowerSwitch(pw);
    }

    /**
     * set the exits, and take care of special rooms
     *
     * @param rooms        all the rooms and their locations in a hashmap
     * @param specialRooms the rooms that are special
     */
    public static void setExits(HashMap<Location, Room> rooms, HashSet<Room> specialRooms) {
        // set the rooms' exits
        // The HashMap rooms is expected to contain a series of rooms shaped as a square
        // the HashSet specialRooms contains rooms, which are not allowed to have exits to each other

        // first the max and min values of x and y are found
        Double maxX = Double.MIN_VALUE; // set the max values to the smallest integer, so it will be overwritten atleast once
        Double maxY = Double.MIN_VALUE;
        Double minX = Double.MAX_VALUE; // set the min value to the largest integer, so it will be overwritten atleast once
        Double minY = Double.MAX_VALUE;
        for (Map.Entry<Location, Room> entry : rooms.entrySet()) {
            if (entry.getValue().getLocation().getX() != 9 && entry.getValue().getLocation().getX() != 9) {
                if (maxX < entry.getValue().getLocation().getX()) {
                    maxX = entry.getValue().getLocation().getX();
                }
                if (maxY < entry.getValue().getLocation().getY()) {
                    maxY = entry.getValue().getLocation().getY();
                }
                if (minX > entry.getValue().getLocation().getX()) {
                    minX = entry.getValue().getLocation().getX();
                }
                if (minY > entry.getValue().getLocation().getY()) {
                    minY = entry.getValue().getLocation().getY();
                }
            }
        }

        HashMap<Direction, Boolean> exitsAllowed = new HashMap<>();
        exitsAllowed.put(Direction.NORTH, Boolean.TRUE);
        exitsAllowed.put(Direction.SOUTH, Boolean.TRUE);
        exitsAllowed.put(Direction.EAST, Boolean.TRUE);
        exitsAllowed.put(Direction.WEST, Boolean.TRUE);
        for (Map.Entry<Location, Room> room : rooms.entrySet()) {
            /* if the current room can be found in specialRooms,
            it will not be allowed to have exits to the other rooms in specialRooms */
            if (specialRooms.contains(room.getValue())) {
                for (Room r : specialRooms) {
                    if (room.getValue().getLocation().isNextTo(r.getLocation())) {
                        Direction directionNotAllowed = room.getValue().getLocation().getDirectionOfAdjacentLocation(r.getLocation());
                        exitsAllowed.put(directionNotAllowed, Boolean.FALSE);
                    }
                }

            }
            // the exits are set
            if (exitsAllowed.get(Direction.EAST)) {
                if (room.getValue().getLocation().getX() != maxX) {
                    // the leftmost rooms have an exit to the east
                    Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                    room.getValue().setExit(Direction.EAST, loca);
                }
            }
            if (exitsAllowed.get(Direction.WEST)) {
                if (room.getValue().getLocation().getX() != minX) {
                    // the rightmost rooms have en exit to the west
                    Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                    room.getValue().setExit(Direction.WEST, loca);
                }
            }
            if (exitsAllowed.get(Direction.NORTH)) {
                if (room.getValue().getLocation().getY() != maxY) {
                    // the southmost rooms have an exit to the north
                    Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                    room.getValue().setExit(Direction.NORTH, loca);
                }
            }
            if (exitsAllowed.get(Direction.SOUTH)) {
                if (room.getValue().getLocation().getY() != minY) {
                    // the northmost rooms have an exit to the south
                    Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                    room.getValue().setExit(Direction.SOUTH, loca);
                }
            }
            for (Map.Entry<Direction, Boolean> entry : exitsAllowed.entrySet()) {
                entry.setValue(Boolean.TRUE);
            }
        }
    }

    public static void setExitsTest(HashMap<Location, Room> rooms, HashSet<Room> specialRooms) {
        HashMap<Direction, int[]> directions = new HashMap<>();
        int[] n = {0, 1};
        int[] e = {1, 0};
        int[] s = {0, -1};
        int[] w = {-1, 0};
        directions.put(Direction.NORTH, n);
        directions.put(Direction.EAST, e);
        directions.put(Direction.SOUTH, s);
        directions.put(Direction.WEST, w);

        HashMap<Direction, Boolean> exitsAllowed = new HashMap<>();
        exitsAllowed.put(Direction.NORTH, Boolean.TRUE);
        exitsAllowed.put(Direction.SOUTH, Boolean.TRUE);
        exitsAllowed.put(Direction.EAST, Boolean.TRUE);
        exitsAllowed.put(Direction.WEST, Boolean.TRUE);

        for (Map.Entry<Location, Room> room : rooms.entrySet()) {
            for (HashMap.Entry<Direction, int[]> d : directions.entrySet()) {
                if (exitsAllowed.get(d.getKey())) {
                    Location loca = new Location(room.getValue().getLocation().getX() + d.getValue()[0], room.getValue().getLocation().getY() + d.getValue()[1]);
                    if (rooms.containsKey(loca)) {
                        room.getValue().setExit(d.getKey(), loca);
                    }
                }
            }
        }
    }

    /**
     * Get the visual description. Used to tell how the room looks
     * @return a visual description of the room
     */
    public String getVisualDescription() {
        return visualDescription;
    }

    /**
     * Get all the exits in this room
     * @return map of exits.
     */
    public HashMap<Direction, Location> getExits() {
        return exits;
    }
}

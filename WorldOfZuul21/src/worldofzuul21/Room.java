package worldofzuul21;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room {

    private String name;
    private Location location;
    private String description;
    private HashMap<Direction, Integer> exits;
    private PowerSwitch powerSwitch;
    private PowerRelay powerRelay;
    private Item[] items;
    private Guard[] guards;

    /**
     * creates a new room, with a name, description and location via x y
     * coordinates.
     *
     * @param name
     * @param description
     * @param x
     * @param y
     */
    public Room(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        location = new Location(x, y);
        exits = new HashMap<Direction, Integer>();
        items = new Item[1]; // only one item per room
        guards = new Guard[2]; // two guards per room
    }

    /**
     *
     * @return name of room
     */
    public String getName() {
        return name;
    }

    /**
     * adds a possible exit for this room.
     *
     * @param direction
     * @param neighbor
     */
    public void setExit(Direction direction, Integer neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    /* Returns the description of the room, and exits for the room. */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + getPowerSwitchToString() + getPowerRelayToString() + getItemToString();
    }

    /* Returns all the exits for the room as a string. */
    private String getExitString() {
        String returnString = "Exits:";
        // Get the keys from the exits map (the strings).
        Set<Direction> keys = exits.keySet();
        for (Direction exit : keys) {
            // For each string, called exit, in the Set of keys, 
            // append it to the string.
            returnString += " " + exit;
        }
        return returnString;
    }

    /* Returns room, that has the corresponding direction. 
       Returns null if there is no room, for a certain direction. */
    public Integer getExit(Direction direction) {
        return exits.get(direction);
    }

    /**
     *
     * @return the location of this room
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return the powerswitch placed in this room. Returns null if there is no
     * powerswitch.
     */
    public PowerSwitch getPowerSwitch() {
        return powerSwitch;
    }

    public PowerRelay getPowerRelay() {
        return this.powerRelay;
    }

    public void setPowerRelay(PowerRelay powerRelay) {
        this.powerRelay = powerRelay;
    }

    /**
     *
     * @return a string, indicating if there is a powerswitch in this room.
     */
    public String getPowerSwitchToString() {
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
     * @param powerSwitch
     */
    public void setPowerSwitch(PowerSwitch powerSwitch) {
        this.powerSwitch = powerSwitch;
    }

    /**
     *
     * @return a string, indicating if there is an item in this room.
     */
    public String getItemToString() {
        if (items[0] != null) {
            return "\nThere is a " + items[0].getName() + " here";
        } else {
            return "";
        }
    }

    /**
     *
     * @return the first item of the items array.
     */
    public Item getItems() {
        return items[0];
    }

    /**
     * sets the first item of the items array.
     *
     * @param item
     */
    public void setItem(Item item) {
        items[0] = item;
    }

    /**
     * removes the first item of the items array.
     */
    public void removeItem() {
        items[0] = null;
    }

    /**
     *
     * @return an array containing the guards in this room
     */
    public Guard[] getGuards() {
        return guards;
    }

    /**
     * Adds a guard to this room. The method figures out which array index to
     * place the guard in.
     *
     * @param guard
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

    public String getPowerRelayToString() {
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

    public void setSpawn(Spawnable obj) {
        if (obj instanceof Item) {
            this.setItem((Item) obj);
        }
        if (obj instanceof Guard) {
            this.addGuard((Guard) obj);
        }
        if (obj instanceof PowerRelay) {
            this.setPowerRelay((PowerRelay) obj);
        }
        if (obj instanceof PowerSwitch) {
            this.setPowerSwitch((PowerSwitch) obj);
        }
    }

    public static void setExits(HashMap<Integer, Room> rooms, HashSet<Room> specialRooms) {
        // set the rooms' exits
        // The HashMap rooms is expected to contain a series of rooms shaped as a square
        // the HashSet specialRooms contains rooms, which are not allowed to have exits to each other

        // first the max and min values of x and y are found
        Integer maxX = null;
        Integer maxY = null;
        Integer minX = null;
        Integer minY = null;
        for (Map.Entry<Integer, Room> room : rooms.entrySet()) {
            if (room.getValue().getLocation().getX() != 9 && room.getValue().getLocation().getX() != 9) {
                if (maxX == null) {
                    maxX = room.getValue().getLocation().getX();
                }
                if (maxY == null) {
                    maxY = room.getValue().getLocation().getY();
                }
                if (minX == null) {
                    minX = room.getValue().getLocation().getX();
                }
                if (minY == null) {
                    minY = room.getValue().getLocation().getY();
                }
                if (maxX < room.getValue().getLocation().getX()) {
                    maxX = room.getValue().getLocation().getX();
                }
                if (maxY < room.getValue().getLocation().getY()) {
                    maxY = room.getValue().getLocation().getY();
                }
                if (minX > room.getValue().getLocation().getX()) {
                    minX = room.getValue().getLocation().getX();
                }
                if (minY > room.getValue().getLocation().getY()) {
                    minY = room.getValue().getLocation().getY();
                }
            }
        }

        HashMap<Direction, Boolean> exitsAllowed = new HashMap<>();
        exitsAllowed.put(Direction.NORTH, Boolean.TRUE);
        exitsAllowed.put(Direction.SOUTH, Boolean.TRUE);
        exitsAllowed.put(Direction.EAST, Boolean.TRUE);
        exitsAllowed.put(Direction.WEST, Boolean.TRUE);
        for (Map.Entry<Integer, Room> room : rooms.entrySet()) {
            /* if the current room can be found in specialRooms,
            it will not be allowed to have exits to the other rooms in specialRooms */
            if (specialRooms.contains(room.getValue())) {
                for (Room r : specialRooms) {
                    if (room.getValue().getLocation().isNextTo(r.getLocation())) {
                        Direction directionNotAllowed = room.getValue().getLocation().getDirectionOfAdjacentLocation(r.getLocation());
                        if (exitsAllowed.containsKey(directionNotAllowed)) {
                            for (Map.Entry<Direction, Boolean> entry : exitsAllowed.entrySet()) {
                                if (entry.getKey().equals(directionNotAllowed)) {
                                    entry.setValue(Boolean.FALSE);
                                }
                            }
                        }
                    }
                }

            }
            // the exits are set
            if (exitsAllowed.get(Direction.EAST)) {
                if (room.getValue().getLocation().getX() != maxX) {
                    // the leftmost rooms have an exit to the east
                    Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                    room.getValue().setExit(Direction.EAST, loca.getXY());
                }
            }
            if (exitsAllowed.get(Direction.WEST)) {
                if (room.getValue().getLocation().getX() != minX) {
                    // the rightmost rooms have en exit to the west
                    Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                    room.getValue().setExit(Direction.WEST, loca.getXY());
                }
            }
            if (exitsAllowed.get(Direction.NORTH)) {
                if (room.getValue().getLocation().getY() != maxY) {
                    // the southmost rooms have an exit to the north
                    Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                    room.getValue().setExit(Direction.NORTH, loca.getXY());
                }
            }
            if (exitsAllowed.get(Direction.SOUTH)) {
                if (room.getValue().getLocation().getY() != minY) {
                    // the northmost rooms have an exit to the south
                    Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                    room.getValue().setExit(Direction.SOUTH, loca.getXY());
                }
            }
            for (Map.Entry<Direction, Boolean> entry : exitsAllowed.entrySet()) {
                entry.setValue(Boolean.TRUE);
            }
        }
    }

    public static void setExitsTest(HashMap<Integer, Room> rooms, HashSet<Room> specialRooms) {
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

        for (Map.Entry<Integer, Room> room : rooms.entrySet()) {
            for (HashMap.Entry<Direction, int[]> d : directions.entrySet()) {
                if (exitsAllowed.get(d.getKey())) {
                    Location loca = new Location(room.getValue().getLocation().getX() + d.getValue()[0], room.getValue().getLocation().getY() + d.getValue()[1]);
                    if (rooms.containsKey(loca.getXY())) {
                        room.getValue().setExit(d.getKey(), loca.getXY());
                    }
                }
            }
        }
    }
}

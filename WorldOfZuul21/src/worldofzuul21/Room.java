package worldofzuul21;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room {

    private String name;
    private Location location;
    private String description;
    private HashMap<String, Integer> exits;
    private PowerSwitch powerSwitch;
    private Item[] items;
    private Guard[] guards;

    /**
     * creates a new room, with a name, description and location via x y coordinates.
     * @param name
     * @param description
     * @param x
     * @param y
     */
    public Room(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        location = new Location(x, y);
        exits = new HashMap<String, Integer>();
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
     * @param direction
     * @param neighbor
     */
    public void setExit(String direction, Integer neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    /* Returns the description of the room, and exits for the room. */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + getPowerSwitchToString() + getItemToString();
    }

    /* Returns all the exits for the room as a string. */
    private String getExitString() {
        String returnString = "Exits:";
        // Get the keys from the exits map (the strings).
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            // For each string, called exit, in the Set of keys, 
            // append it to the string.
            returnString += " " + exit;
        }
        return returnString;
    }

    /* Returns room, that has the corresponding direction. 
       Returns null if there is no room, for a certain direction. */
    public Integer getExit(String direction) {
        return exits.get(direction);
    }

    /**
     *
     * @return the location of this room
     */
    Location getLocation() {
        return location;
    }

    /**
     *
     * @return the powerswitch placed in this room. Returns null if there is no powerswitch.
     */
    public PowerSwitch getPowerSwitch() {
        return powerSwitch;
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
     * Adds a guard to this room. The method figures out which array index to place the guard in.
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
}

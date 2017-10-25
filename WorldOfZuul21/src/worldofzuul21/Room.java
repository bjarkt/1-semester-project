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

    public Room(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        location = new Location(x, y);
        exits = new HashMap<String, Integer>();
        items = new Item[1];
        guards = new Guard[2];
    }

    public String getName() {
        return name;
    }
    
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

    Location getLocation() {
        return location;
    }

    public PowerSwitch getPowerSwitch() {
        return powerSwitch;
    }

    public String getPowerSwitchToString() {
        if (this.powerSwitch
                == null) {
            return "";
        }
        if (!this.powerSwitch.getIsOn()) {
            return "";
        } else if (this.powerSwitch.getIsOn()) {
            return "\nThere is a powerswitch in here";
        } else {
            return "";
        }

    }

    public void setPowerSwitch(PowerSwitch powerSwitch) {
        this.powerSwitch = powerSwitch;
    }

    public String getItemToString() {
        if (items[0] != null) {
            return "\nThere is an item here";
        } else {
            return "";
        }
    }

    public Item getItems() {
        return items[0];
    }

    public void setItem(Item item) {
        items[0] = item;
    }

    public void removeItem() {
        items[0] = null;
    }
    
    public Guard[] getGuards() {
        return guards;
    }

    public void addGuard(Guard guard) {
        if (guards[0] != null) {
            guards[1] = guard;
        } else {
            guards[0] = guard;
        }
    }

    public void removeGuard() {
        if (guards[0] != null) {
            guards[0] = null;
        } else {
            guards[1] = null;
        }
    }
}

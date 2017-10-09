package worldofzuul21;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room {

    private String description;
    private HashMap<String, Room> exits;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    /* Returns the description of the room, and exits for the room. */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
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
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}

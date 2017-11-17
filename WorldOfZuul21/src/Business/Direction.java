package Business;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
// Makes a datatype that enables a variable to be set of predifened contants.
public enum Direction {
 NORTH("north"), SOUTH("south"), WEST("west"), EAST("east"), NORTHWEST("northwest"), NORTHEAST("northeast"), SOUTHWEST("southwest"), SOUTHEAST("southeast");
//  Here is a atributes of a string defined.     
    private String commandString;
// Mathod that sets a parameter to another parameter. 

    Direction(String commandString) {
        this.commandString = commandString;
    }

    // Returns the string representation of the enum
    public String toString() {
        return commandString;
    }

    public static boolean isInEnum(String value) {
        for (Direction direction : Direction.values()) {
            if (direction.commandString.equals(value)) {
                return true;
            }
        }
        return false;
    }

}

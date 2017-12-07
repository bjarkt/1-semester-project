package Acq;

/**
 * Direction used to tell direction in game
 */
public enum Direction {
    NORTH("north"), SOUTH("south"), WEST("west"), EAST("east"), NORTHWEST("northwest"), NORTHEAST("northeast"), SOUTHWEST("southwest"), SOUTHEAST("southeast"), NOWHERE("nowhere");
    //  Here is a atributes of a string defined.
    private String commandString;

    /**
     * constructor
     *
     * @param commandString string to create new Direction from
     */
    Direction(String commandString) {
        this.commandString = commandString;
    }

    /**
     * @return the string representation of the enum
     */
    public String toString() {
        return commandString;
    }

    /**
     * @param value string value that may be an instance of this enum
     * @return true if the string has a corresponding enum
     */
    public static boolean isInEnum(String value) {
        for (Direction direction : Direction.values()) {
            if (direction.commandString.equals(value)) {
                return true;
            }
        }
        return false;
    }

}

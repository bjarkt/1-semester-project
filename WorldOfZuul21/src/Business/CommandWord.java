package Business;

/**
 * Makes a datatype that enables a variable to be set of predifened contants.
 */
public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("help"), INTERACT("interact"), UNKNOWN("?"), STEAL("steal"), ESCAPE("escape"),
    YES("yes"), NO("no"), HIDE("hide"), CALL("call"), SAVE("save"), LOAD("load"), NEW("new"), HIGHSCORE("highscore");

    private String commandString;

    /**
     * Constructor for the enum.
     *
     * @param commandString a string.
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    /**
     * the toString method.
     *
     * @return the string representation of the enum.
     */
    @Override
    public String toString() {
        return commandString;
    }
}

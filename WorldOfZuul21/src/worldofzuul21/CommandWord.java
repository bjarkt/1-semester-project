package worldofzuul21;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
// Makes a datatype that enables a variable to be set of predifened contants.
public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("help"), INTERACT("interact"), UNKNOWN("?"), STEAL("steal"), ESCAPE("escape"),
    YES("yes"), NO("no"), HIDE("hide"), CALL("call"), SAVE("save"), LOAD("load"), NEW("new"), HIGHSCORE("highscore");

//  Here is a atributes of a string defined.     
    private String commandString;
// Mathod that sets a parameter to another parameter. 

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    // Returns the string representation of the enum
    @Override
    public String toString() {
        return commandString;
    }
}

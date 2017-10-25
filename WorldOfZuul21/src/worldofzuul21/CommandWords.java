package worldofzuul21;

import java.util.HashMap;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class CommandWords {

    private HashMap<String, CommandWord> validCommands; //A hashmap with the constants from the enom class CommandWords as values.

    public CommandWords() // Constructor
    {
        validCommands = new HashMap<String, CommandWord>(); // Creating an object
        for (CommandWord command : CommandWord.values()) { //For each loop - runs 4 times as CommandWord.values has 4 values.
            if (command != CommandWord.UNKNOWN && command != CommandWord.YES && command != CommandWord.NO) {  //Checks if command isn't UNKNOWN, YES or NO
                validCommands.put(command.toString(), command); //If it isn't, a new entry is added to the validCommands HashMap
            }
        }
    }

    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        if (command != null) { //Checks if command isnt null, so it can be returned, else UNKNOWN ("?") is returned
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }

    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString); //Returns true if the HashMap contains an entry for the key
    }

    public void showAll() {
        for (String command : validCommands.keySet()) { //For each loop, that prints out every key in the HashMap validCommands.
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}

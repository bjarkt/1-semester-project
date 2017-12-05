package Business;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Command {

    /**
     * Defining attrubitese commandWord of the datatype CommandWord, and secondWord of the datatype String
     */
    private CommandWord commandWord;
    private String secondWord;

    /**
     * Creating Constructor for the class Command. The constructer has two parameters,
     * which takes arguments of the datatype CommandWord and String.
     * The parameters is set equal to the attributes of the class.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }


    /**
     * Method that enables the aqquirement of the attribute commandWord.
     *
     * @return the {@link CommandWord} for this command.
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * Method that enables the aqquirement of the attribute secondWord.
     *
     * @return the second word of the command, a string.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Boolean Method that checks if the attribute commandWord is equal to that of CommandWord.UNNKOWN.
     *
     * @return true if the commandWord is CommandWord.UNKNOWN.
     */
    public boolean isUnknown() {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * Method that checks the boolen expression: secondWord != null,
     *
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}

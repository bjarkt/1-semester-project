package worldofzuul21;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Parser 
{
    private CommandWords commands;
    private Scanner reader;
/* No arg constructor */ 
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }
/*Method for getting command from user input*/
    public Command getCommand() 
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();
/*Cheking for 1 or 2 words in input */
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }
/*Method for showing all acceptable commands */
    public void showCommands()
    {
        commands.showAll();
    }
}

package worldofzuul21;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /* zero argument constructor. */
    public Game() 
    {
        createRooms();
        
        // Instantiate the parser used to parse commands.
        parser = new Parser();
    }

    /* Create the rooms and set the current room. */
    private void createRooms()
    {
    
    //    Room room00, room01, room02, room03, room04, room05,
    //         room06, room07, room08, room09, room10, room11;
    
        
        Room outside, theatre, pub, lab, office;
        // Instantiate the rooms, and write their descriptions.
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // Set the exit for each room,
        // a direction and a room object is required.
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        // Set the room, in which the player starts.
        currentRoom = outside;
    
    }

    /* The method in which the main game loop happens. */
    public void play() 
    {            
        printWelcome();

        // Finished is assigned to false at the start, so the while loop
        // will execute atleast once.
        boolean finished = false;
        while (! finished) {
            // Enter a command and parse it.
            Command command = parser.getCommand();
            
            // Process the command, and assign the result to finished.
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /* Print welcome and description of the current room. */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /* Processes the command, returning either false or true.
       If true is returned, the game quits. */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
           
        // Check if the first part of the command is an actual command.
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        // Check the first word of the command against each of the enums,
        // and run the appropriate method.
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    /* Prints the commands. */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /* Updates the currentRoom variable, and prints description of the room. */
    private void goRoom(Command command) 
    {
        // Stop the method if a second word isn't supplied.
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        
        // In the go command, the second word is the direction.
        String direction = command.getSecondWord();

        // Retrieve the room, which is stored in the hashmap of exits.
        // null is assigned to nextRoom, if there is no value for the key (direction).
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /* returns true, if a second word has not been supplied. */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}

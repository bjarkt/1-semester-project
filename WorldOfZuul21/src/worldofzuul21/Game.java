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
    
        Room room00, room01, room02, room03, room04, room05,
        room06, room07, room08, room09, room10, room11, noRoom;
        // Instantiate the rooms, and write their descriptions.
        room00 = new Room("at the entrance of the museum");
        room01 = new Room("in a room");
        room02 = new Room("in a room. There is stairs to the upper floor, to the east");
        room03 = new Room("on the upper floor. There is stairs to the groundfloor, to the west");
        room04 = new Room("in a room");
        room05 = new Room("in a room");
        room06 = new Room("in a room");
        room07 = new Room("in a room");
        room08 = new Room("in a room");
        room09 = new Room("in a room");
        room10 = new Room("in a room");
        room11 = new Room("in a room");
        noRoom = new Room("nowhere");

        // Set the exit for each room,
        // a direction and a room object is required.        
        room00.setExit("east", room01);
        room00.setExit("south", room04);

        room01.setExit("east", room02);
        room01.setExit("west", room00);
        room01.setExit("south", room05);

        room02.setExit("east", room03);
        room02.setExit("west", room01);
        room02.setExit("south", room06);

        room03.setExit("west", room02);
        
        room04.setExit("north", room00);
        room04.setExit("east", room05);
        room04.setExit("south", room08);
        
        room05.setExit("north", room01);
        room05.setExit("east", room06);
        room05.setExit("west", room04);
        room05.setExit("south", room09);
        
        room06.setExit("north", room02);
        room06.setExit("east", room07);
        room06.setExit("west", room05);
        room06.setExit("south", room10);
        
        room07.setExit("west", room06);
        room07.setExit("south", room11);
        
        room08.setExit("north", room04);
        room08.setExit("east", room09);
        
        room09.setExit("north", room05);
        room09.setExit("east", room10);
        room09.setExit("west", room08);
        
        room10.setExit("north", room06);
        room10.setExit("east", room11);
        room10.setExit("west", room09);
        
        room11.setExit("north", room07);
        room11.setExit("west", room10);

        // Set the room, in which the player starts.
        currentRoom = room00;
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

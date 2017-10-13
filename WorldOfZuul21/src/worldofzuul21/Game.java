package worldofzuul21;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game {

    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer, Room> rooms;
    private boolean item;
    private PowerSwitch powerSwitch;
    private Guard[] guards;

    /* zero argument constructor. */
    public Game() {
        createRooms();

        item = false;

        // Instantiate the parser used to parse commands.
        parser = new Parser();
    }

    /* Create the rooms and set the current room. */
    private void createRooms() {

        Room room00, room01, room02, room03, room04, room05,
                room06, room07, room08, room09, room10, room11, noRoom;
        // Instantiate the rooms, and write their descriptions.
        room00 = new Room("in a room", 0, 0);
        room01 = new Room("in a room", 1, 0);
        room02 = new Room("in a room. There is stairs to the upper floor, to the east", 2, 0);
        room03 = new Room("on the upper floor. There is stairs to the groundfloor, to the west", 3, 0);
        room04 = new Room("in a room", 0, 1);
        room05 = new Room("in a room", 1, 1);
        room06 = new Room("in a room", 2, 1);
        room07 = new Room("in a room", 3, 1);
        room08 = new Room("at the entrance of the museum", 0, 2);
        room09 = new Room("in a room", 1, 2);
        room10 = new Room("in a room", 2, 2);
        room11 = new Room("in a room", 3, 2);
        noRoom = new Room("nowhere", 9, 9);

        int number = (int) (Math.random() * 3);

        switch (number) {
            case 0:
                room02.setPowerSwitch(new PowerSwitch());
                room02.getPowerSwitch().turnPowerOn();
                break;
            case 1:
                room04.setPowerSwitch(new PowerSwitch());
                room04.getPowerSwitch().turnPowerOn();
                break;
            case 2:
                room11.setPowerSwitch(new PowerSwitch());
                room11.getPowerSwitch().turnPowerOn();
                break;
        }
        // Add the rooms to an array
        rooms = new HashMap<>();
        rooms.put(room00.getLocation().getXY(), room00);
        rooms.put(room01.getLocation().getXY(), room01);
        rooms.put(room02.getLocation().getXY(), room02);
        rooms.put(room03.getLocation().getXY(), room03);
        rooms.put(room04.getLocation().getXY(), room04);
        rooms.put(room05.getLocation().getXY(), room05);
        rooms.put(room06.getLocation().getXY(), room06);
        rooms.put(room07.getLocation().getXY(), room07);
        rooms.put(room08.getLocation().getXY(), room08);
        rooms.put(room09.getLocation().getXY(), room09);
        rooms.put(room10.getLocation().getXY(), room10);
        rooms.put(room11.getLocation().getXY(), room11);
        rooms.put(noRoom.getLocation().getXY(), noRoom);

        // Set the room, in which the player starts.
        Location loc = new Location(room08.getLocation().getX(), room08.getLocation().getY());
        currentRoom = rooms.get(room08.getLocation().getXY());
        System.out.println(currentRoom == null);

        // Set the exit for each room,
        // a direction and a room object is required.
        for (Map.Entry<Integer, Room> room : rooms.entrySet()) {
            if (room.getValue().getLocation().getX() == 0) {
                Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                room.getValue().setExit("east", loca.getXY());
            }
            if (room.getValue().getLocation().getX() == 1 || room.getValue().getLocation().getX() == 2) {
                Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                room.getValue().setExit("east", loca.getXY());
                Location loca2 = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca2.getXY());
            }
            if (room.getValue().getLocation().getX() == 3) {
                Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 2) {
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                room.getValue().setExit("north", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 1 && room.getValue().getLocation().getX() < 3) {
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                room.getValue().setExit("north", loca.getXY());
                Location loca2 = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                room.getValue().setExit("south", loca2.getXY());
            }
            if (room.getValue().getLocation().getY() == 0 && room.getValue().getLocation().getX() < 3) {
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                room.getValue().setExit("south", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 1 && room.getValue().getLocation().getX() == 3) {
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                room.getValue().setExit("south", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 0 && room.getValue().getLocation().getX() == 3) {
                Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca.getXY());
            }
        }
    }

    /* The method in which the main game loop happens. */
    public void play() {
        printWelcome();

        // Finished is assigned to false at the start, so the while loop
        // will execute atleast once.
        boolean finished = false;
        while (!finished) {
            // Enter a command and parse it.
            Command command = parser.getCommand();

            // Process the command, and assign the result to finished.
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /* Print welcome and description of the current room. */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Night at the Museum");
        System.out.println("Night at the Museum is a new, incredibly amazing strategy game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /* Processes the command, returning either false or true.
       If true is returned, the game quits. */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        // Check if the first part of the command is an actual command.
        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        // Check the first word of the command against each of the enums,
        // and run the appropriate method.
        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.INTERACT) {
                interact();
        }
        return wantToQuit;
    }

    /* Prints the commands. */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /* Updates the currentRoom variable, and prints description of the room. */
    private void goRoom(Command command) {
        // Stop the method if a second word isn't supplied.
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        // In the go command, the second word is the direction.
        String direction = command.getSecondWord();

        // Retrieve the room, which is stored in the hashmap of exits.
        // null is assigned to nextRoom, if there is no value for the key (direction).
        Room nextRoom = rooms.get(currentRoom.getExit(direction));
        System.out.println(nextRoom == null);
        //Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /* returns true, if a second word has not been supplied. */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private void interact() {
        if(currentRoom.getPowerSwitch() == null) {
            return;
        }
        if (!currentRoom.getPowerSwitch().getIsOn()) {
            return;
        } else if (currentRoom.getPowerSwitch().getIsOn()) {
            currentRoom.getPowerSwitch().turnPowerOff();
            System.out.println("The lights will be turned off, for 10 turns");
        }
    }
}

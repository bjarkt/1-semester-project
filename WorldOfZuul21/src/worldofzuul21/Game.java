package worldofzuul21;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game {

    private Parser parser; // the parser is used to get the players command inputs
    private Room currentRoom; // the room that the player is currently in
    private HashMap<Integer, Room> rooms; // a map storing all the rooms
    private PowerSwitch powerSwitch; // a powerswitch
    private Room powerSwitchRoom; // the room with the powerswitch
    private int powerSwitchLocation; // the coordinates of the room with the powerswitch
    private Guard[] guards; // the guards
    private List<Room> itemSpawnPointRooms; // the rooms that items can spawn in
    private String itemName; // the name of the last spawned item
    private Inventory inventory; // the players inventory
    private int timer; // the amount of turns taken
    private boolean powerStatus; // true when the power is on
    private int timerPoint; // the last the powerswitch was turned off
    private int powerOffTime; // the amount of turns that the power is turned off
    private boolean policeAlerted; // true when the police is alerted
    private int alertPoint; // the last time the police was alerted
    private int policeArrivalTime; // the amount of turns it takes for the police to arrive
    private boolean gotBusted; // true if you got busted by a guard
    private boolean policeArrived; // true if the police has arrived
    private FriendlyNpc friendlyNpc;
    /* zero argument constructor. */
    public Game() {
        parser = new Parser(); // Instantiate the parser used to parse commands.
        guards = new Guard[2]; // Create the guards
        guards[0] = new Guard(1);
        guards[1] = new Guard(2);
        itemSpawnPointRooms = new ArrayList<>(); // Instantiate the spawnpoints of items
        inventory = new Inventory(); // Instantiate the inventory
        timer = 0; // Instantiate the timer
        powerStatus = true; // turn on the power
        powerOffTime = 10; // set the time that the power is turned
        policeArrivalTime = 5; // set the time that it takes for the police to arrive
        gotBusted = false; // the player has not been busted yet
        policeArrived = false; // the police has not arrived yet
        friendlyNpc = new FriendlyNpc();
        createRooms(); // create the rooms
    }

    /* Create the rooms and set the current room. */
    private void createRooms() {

        // Create the rooms
        Room room00, room01, room02, room03, room04, room05, room06, room07, room08, room09,
                room10, room11, room12, room13, room14, room15, room16, room17, room18, room19, noRoom;

// Instantiate the rooms, and write their descriptions.
        room00 = new Room("room00", "at the entrance of the museum", 0, 0);
        room01 = new Room("room01", "in room 1", 1, 0);
        room02 = new Room("room02", "in room 2", 2, 0);
        room03 = new Room("room03", "in room 3", 3, 0);
        room04 = new Room("room04", "in room 4", 4, 0);
        room05 = new Room("room05", "in room 5", 0, 1);
        room06 = new Room("room06", "in room 6", 1, 1);
        room07 = new Room("room07", "in room 7", 2, 1);
        room08 = new Room("room08", "in room 8", 3, 1);
        room09 = new Room("room09", "in room 9", 4, 1);
        room10 = new Room("room10", "in room 10", 0, 2);
        room11 = new Room("room11", "in room 11", 1, 2);
        room12 = new Room("room12", "in room 12", 2, 2);
        room13 = new Room("room13", "in room 13", 3, 2);
        room14 = new Room("room14", "in room 14", 4, 2);
        room15 = new Room("room15", "in room 15", 0, 3);
        room16 = new Room("room16", "in room 16", 1, 3);
        room17 = new Room("room17", "in room 17", 2, 3);
        room18 = new Room("room18", "in a room. There are stairs to the upper floor, to the east", 3, 3);
        room19 = new Room("room19", "on the upper floor. There are stairs to the groundfloor, to the west", 4, 3);
        noRoom = new Room("nowhere", "nowhere", 9, 9);

        // spawn the guards
        room04.addGuard(guards[0]);
        room15.addGuard(guards[1]);
        room04.getGuards()[0].setRoom(room04);
        room15.getGuards()[0].setRoom(room15);

        // spawn the powerswitch in one of three rooms
        int number = (int) (Math.random() * 3);
        switch (number) {
            case 0:
                room04.setPowerSwitch(new PowerSwitch());
                room04.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room04;
                break;
            case 1:
                room07.setPowerSwitch(new PowerSwitch());
                room07.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room07;
                break;
            case 2:
                room10.setPowerSwitch(new PowerSwitch());
                room10.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room10;
                break;
        }

        powerSwitchLocation = powerSwitchRoom.getLocation().getXY(); // save the powerswitch's location's coordinates

        Collections.addAll(itemSpawnPointRooms, room02, room13, room16, room19); // save the itemspawnpoints

        itemName = Item.spawnItem(itemSpawnPointRooms); // save the name of the first spawned item

        // Add the rooms to a map, using their locations' coordinates as keys
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
        rooms.put(room12.getLocation().getXY(), room12);
        rooms.put(room13.getLocation().getXY(), room13);
        rooms.put(room14.getLocation().getXY(), room14);
        rooms.put(room15.getLocation().getXY(), room15);
        rooms.put(room16.getLocation().getXY(), room16);
        rooms.put(room17.getLocation().getXY(), room17);
        rooms.put(room18.getLocation().getXY(), room18);
        rooms.put(room19.getLocation().getXY(), room19);
        rooms.put(noRoom.getLocation().getXY(), noRoom);

        // Set the room, in which the player starts.
        currentRoom = rooms.get(room00.getLocation().getXY()); // set the room in which the player starts

        // Set the exits for each room
        for (Map.Entry<Integer, Room> room : rooms.entrySet()) {
            if (room.getValue().getLocation().getX() == 0) {
                // the leftmost rooms have an exit to the east
                Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                room.getValue().setExit("east", loca.getXY());
            }
            if (room.getValue().getLocation().getX() == 1 || room.getValue().getLocation().getX() == 2 || room.getValue().getLocation().getX() == 3) {
                // some rooms have exits to both the east an the west
                Location loca = new Location(room.getValue().getLocation().getX() + 1, room.getValue().getLocation().getY());
                room.getValue().setExit("east", loca.getXY());
                Location loca2 = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca2.getXY());
            }
            if (room.getValue().getLocation().getX() == 4) {
                // the rightmost rooms have en exit to the west
                Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 0) {
                // the southmost rooms have an exit to the north
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                room.getValue().setExit("north", loca.getXY());
            }
            if ((room.getValue().getLocation().getY() == 1 || room.getValue().getLocation().getY() == 2) && room.getValue().getLocation().getX() < 4) {
                // some rooms have exits to both the north and south
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() + 1);
                room.getValue().setExit("north", loca.getXY());
                Location loca2 = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                room.getValue().setExit("south", loca2.getXY());
            }
            if (room.getValue().getLocation().getY() == 3 && room.getValue().getLocation().getX() < 4) {
                // most northmost rooms have an exit to the south
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                room.getValue().setExit("south", loca.getXY());
            }
            if (room.getValue().getLocation().getY() == 2 && room.getValue().getLocation().getX() == 4) {
                // this room have an exit to the south, but not to the north
                Location loca = new Location(room.getValue().getLocation().getX(), room.getValue().getLocation().getY() - 1);
                room.getValue().setExit("south", loca.getXY());
            }

            if (room.getValue().getLocation().getY() == 3 && room.getValue().getLocation().getX() == 4) {
                // this room have an exit to the west, but not to the south
                Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca.getXY());
            }
        }
    }

    // The method in which the main game loop happens.
    public void play() {
        printWelcome();
        // Finished is assigned to false at the start, so the while loop will execute at least once.
        boolean finished = false;
        while (!finished) {
            // Enter a command and parse it.
            Command command = parser.getCommand();
            // Process the command, and assign the result to finished.
            finished = processCommand(command);
        }
        quit();
    }

    // Print welcome and description of the current room.
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Night at the Museum.");
        System.out.println("Night at the Museum is a new, incredibly amazing strategy game.");
        System.out.println("Type '" + CommandWord.HELP + "' for a list of commands.");
        System.out.println("Type '" + CommandWord.CALL + "' if you don't know what to do.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        printGuardLocations();
    }

    /* Processes the command, returning either false or true.
       If true is returned, the game quits. */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        // Check if the first part of the command is an actual command.
        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("What do you mean?");
            return false;
        }

        // Check the first word of the command against each of the enums, and run the appropriate method.
        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            wantToQuit = goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.INTERACT) {
            interact();
        } else if (commandWord == CommandWord.STEAL) {
            wantToQuit = stealItem();
        } else if (commandWord == CommandWord.ESCAPE) {
            wantToQuit = escape();
        } else if (commandWord == CommandWord.HIDE) {
            wantToQuit = hide();
        } else if (commandWord == CommandWord.CALL) {
            call();
        }

        return wantToQuit;
    }

    /* Prints the commands. */
    private void printHelp() {
        System.out.println("You are inside the museum");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /* Updates the currentRoom variable, and prints description of the room. */
    private boolean goRoom(Command command) {
        boolean forcedToQuit = false;

        // Stop the method if a second word isn't supplied.
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return forcedToQuit;
        }

        // In the go command, the second word is the direction.
        String direction = command.getSecondWord();

        // Retrieve the room, which is stored in the hashmap of exits.
        // null is assigned to nextRoom, if there is no value for the key (direction).
        Room nextRoom = rooms.get(currentRoom.getExit(direction));
        //Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!"); // when there is no exit
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            timer += 1;
            moveGuards(); // move guards
            printGuardLocations();
            forcedToQuit = checkForBusted();
            System.out.println(friendlyNpc.getDirectionOfGuards(currentRoom.getLocation(), guards));
            if (checkTimer()) {
                return true;
            }
        }
        return forcedToQuit;
    }

    public boolean checkTimer() {
        // return true, if the player has lost the game
        // check how many turns there are left before power turns back on
        if (timer == timerPoint + powerOffTime / 2 && !powerStatus) {
            System.out.println("You have " + powerOffTime / 2 + " turns left before power turns on.");
        }
        // check if it is time for the power to turn back on
        if (timer >= timerPoint + powerOffTime && !powerStatus) {
            powerStatus = true;
            rooms.get(powerSwitchLocation).getPowerSwitch().turnPowerOn();
            System.out.println("The power is back on");
            // alert the police
            if (!policeAlerted) {
                System.out.println("The police has been alerted");
                policeAlerted = true;
                alertPoint = timer;
            }
        }
        // check if the police has arrived
        if (timer >= alertPoint + policeArrivalTime && policeAlerted) {
            policeArrived = true;
            return true;
        }
        return false;
    }

    // return true, if a second word has not been supplied
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private void interact() {
        // used to turn off the powerswitch
        if (currentRoom.getPowerSwitch() == null) {
            System.out.println("There is no powerswitch in this room");
            return;
        }
        if (!currentRoom.getPowerSwitch().getIsOn()) {
            System.out.println("The power is already turned off ");
            return;
        } else if (currentRoom.getPowerSwitch().getIsOn()) {
            currentRoom.getPowerSwitch().turnPowerOff();
            System.out.println("The power will be turned off, for " + powerOffTime + " turns");
            timerPoint = timer;
            powerStatus = false;
        }
    }

    private boolean stealItem() {
        // used to steal an item
        boolean forcedToQuit = false;
        if (currentRoom.getItems() != null) {
            if (!powerStatus) {
                if (inventory.addToInventory(currentRoom.getItems())) {
                    System.out.println("You have stolen a " + currentRoom.getItems().getName());
                    currentRoom.removeItem();
                } else {
                    System.out.println("Your inventory is full");
                }
            } else {
                System.out.println("The alarm starts ringing");
                printBusted();
                forcedToQuit = true;
            }
        } else {
            System.out.println("There is no item to steal");
        }
        return forcedToQuit;
    }

    public boolean escape() {
        // used to escape the museum through the entrance
        boolean wantToQuit = false;
        if (currentRoom.getLocation().getXY() == 0) {
            wantToQuit = escaped();
        } else {
            System.out.println("There is no door to escape through");
        }
        return wantToQuit;
    }

    public boolean escaped() {
        // reset the game
        reset();
        boolean lootAdded = inventory.addToLoot();
        System.out.println("You hide in the bush outside the museum. The police arrive. For some reason, they don't notice you");
        if (lootAdded) {
            System.out.println("You hide the item you stole, in the bush");
        }
        // either quit the game, or continue
        System.out.println("Do you want to go back inside?");
        boolean choiceMade = false;
        while (!choiceMade) {
            Command command = parser.getCommand();
            CommandWord commandWord = command.getCommandWord();
            if (commandWord == CommandWord.YES) {
                System.out.println(currentRoom.getLongDescription());
                return false;
            } else if (commandWord == CommandWord.NO) {
                return true;
            } else {
                System.out.println("What do you mean?");
            }
        }
        return false;
    }

    public void moveGuards() {
        // move the guards
        for (Guard guard : guards) {
            Room nextRoom = null;
            while (nextRoom == null) {
                String direction = generateRandomDirection();
                nextRoom = rooms.get(guard.getRoom().getExit(direction));
            }
            guard.getRoom().removeGuard();
            guard.setRoom(nextRoom);
            nextRoom.addGuard(guard);
        }
    }

    public String generateRandomDirection() {
        // generate a random direction
        String[] directions = {"north", "south", "east", "west"};
        int number = (int) (Math.random() * directions.length);
        return directions[number];
    }

    public void printGuardLocations() {
        // print the guard's locations
        System.out.println("The guards are located in the following rooms");
        for (Guard guard : guards) {
            System.out.print(guard.getRoom().getName() + "\t");
        }
        System.out.println();
    }

    public boolean hide() {
        // used to hide from the guards
        // return true, if the players has lost the game
        boolean forcedToQuit = false;
        boolean hasCheckedForTime = false;
        timer += 1;
        moveGuards();
        printGuardLocations();
        forcedToQuit = checkTimer();
        System.out.println("You hide.");
        if (forcedToQuit) {
            return forcedToQuit;
        }
        hasCheckedForTime = true;
        while (checkForBusted()) {
            if (!hasCheckedForTime) {
                forcedToQuit = checkTimer();
                if (forcedToQuit) {
                    return forcedToQuit;
                }
            }
            hasCheckedForTime = false;
            boolean fiftyFifty = Math.random() < 0.5;
            // 50% chance to not get busted if a guard enters your room
            if (fiftyFifty) {
                forcedToQuit = checkForBusted();
                return forcedToQuit;
            } else {
                System.out.println("A guard entered your room, but didn't see you.");
                timer += 1;
                moveGuards();
                printGuardLocations();

            }
        }
        System.out.println("You are in the following room: " + currentRoom.getName());
        return forcedToQuit;
    }

    public void call() {
        // call your friend
        // your friend tells you, what your current objective is
        System.out.println("\"Mastermind Daniel here\"");
        if (policeAlerted && inventory.getInventory().isEmpty()) {
            System.out.println("\"The police has been alerted. You need to get out quickly. You can always go back inside later\"");
        } else if (policeAlerted) {
            System.out.println("\"You got the " + itemName + ", but the police are on their way. Get out quickly\"");
        } else if (inventory.getInventory().isEmpty()) {
            System.out.println("\"You need to steal a " + itemName + "\"");
            System.out.println("\"Remember to turn off the power first, or the alarm will trigger\"");
        } else {
            System.out.println("\"You got the item. Get out of here quickly\"");
        }
    }

    public boolean checkForBusted() {
        // return true if there is a guard in the same room as the player
        for (Guard guard : guards) {
            if (currentRoom.getLocation().getXY() == guard.getRoom().getLocation().getXY()) {
                gotBusted = true;
                return true;
            }
        }
        return false;
    }

    public void printBusted() {
        // message that are printed, when the player gets busted by the guards
        System.out.println("Before you are able to scratch your ass, the guards jump you, and beat the shit out of you");
    }

    public void quit() {
        // the game is over
        // the players points and other information are printed
        if (gotBusted) {
            printBusted();
            // busted by the guards
            System.out.println("You got busted. No points for you. Better luck next time");
        } else if (policeArrived) {
            // busted by the police
            System.out.println("The police arrived. You got busted. No points for you. Better luck next time");
        } else {
            int points = inventory.calculatePoints();
            if (points > 0) {
                // won the game
                System.out.println("You grab your loot from the bush, and run. You won the game. You got " + points + " points");
                inventory.printLoot();
            } else {
                // escaped without stealing anything
                System.out.println("You didn't steal anything. You didn't get arrested though. Thumbs up for that");
            }
        }
        System.out.println("Game over");
    }

    public void reset() {
        // reset the guards
        guards[0].setRoom(rooms.get(40));
        guards[1].setRoom(rooms.get(3));
        // turn the power back on
        rooms.get(powerSwitchLocation).getPowerSwitch().turnPowerOn();
        powerStatus = true;
        // set the alertstatus of the police to false
        policeAlerted = false;
        // spawn a new item, if you stole the last one
        inventory.getInventory().trimToSize();
        if (!inventory.getInventory().isEmpty()) {
            itemName = Item.spawnItem(itemSpawnPointRooms);
        }
    }
}

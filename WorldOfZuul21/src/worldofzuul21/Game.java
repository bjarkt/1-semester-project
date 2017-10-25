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

    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer, Room> rooms;
    private PowerSwitch powerSwitch;
    private Guard[] guards;
    private List<Room> itemSpawnPointRooms;
    private Inventory inventory;
    private int timer;
    private boolean powerStatus;
    int timerPoint;
    int powerOffTime;
    boolean gotBusted;
    Room powerSwitchRoom;

    /* zero argument constructor. */
    public Game() {
        itemSpawnPointRooms = new ArrayList<>();
        // Instantiate the parser used to parse commands.
        parser = new Parser();
        inventory = new Inventory();
        timer = 0;
        powerStatus = true;
        powerOffTime = 10;
        guards = new Guard[2];
        guards[0] = new Guard(1);
        guards[1] = new Guard(2);
        createRooms();
        gotBusted = false;
    }

    /* Create the rooms and set the current room. */
    private void createRooms() {

        Room room00, room01, room02, room03, room04, room05,
                room06, room07, room08, room09, room10, room11, noRoom;
        // Instantiate the rooms, and write their descriptions.
        room00 = new Room("room00", "in a room", 0, 0);
        room01 = new Room("room01", "in a room", 1, 0);
        room02 = new Room("room02", "in a room. There is stairs to the upper floor, to the east", 2, 0);
        room03 = new Room("room03", "on the upper floor. There is stairs to the groundfloor, to the west", 3, 0);
        room04 = new Room("room04", "in a room", 0, 1);
        room05 = new Room("room05", "in a room", 1, 1);
        room06 = new Room("room06", "in a room", 2, 1);
        room07 = new Room("room07", "in a room", 3, 1);
        room08 = new Room("room08", "at the entrance of the museum", 0, 2);
        room09 = new Room("room09", "in a room", 1, 2);
        room10 = new Room("room10", "in a room", 2, 2);
        room11 = new Room("room11", "in a room", 3, 2);
        noRoom = new Room("nowhere", "nowhere", 9, 9);

        room00.addGuard(guards[0]);
        room07.addGuard(guards[1]);
        room00.getGuards()[0].setRoom(room00);
        room07.getGuards()[0].setRoom(room07);

        int number = (int) (Math.random() * 3);

        switch (number) {
            case 0:
                room02.setPowerSwitch(new PowerSwitch());
                room02.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room02;
                break;
            case 1:
                room04.setPowerSwitch(new PowerSwitch());
                room04.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room04;
                break;
            case 2:
                room11.setPowerSwitch(new PowerSwitch());
                room11.getPowerSwitch().turnPowerOn();
                powerSwitchRoom = room11;
                break;
        }

        Collections.addAll(itemSpawnPointRooms, room01, room03, room07, room10);
        Item.spawnItem(itemSpawnPointRooms);

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
        currentRoom = rooms.get(room08.getLocation().getXY());

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
                Location loca = new Location(room.getValue().getLocation().getX() - 1, room.getValue().getLocation().getY());
                room.getValue().setExit("west", loca.getXY());
            }
        }
    }

    /* The method in which the main game loop happens. */
    public void play() {
        printWelcome();

        System.out.println("The guards are located in the following rooms");
        printGuardLocations();
        System.out.println("You are in the following room: " + currentRoom.getName());

        // Finished is assigned to false at the start, so the while loop
        // will execute atleast once.
        boolean finished = false;
        while (!finished) {
            // Enter a command and parse it.
            Command command = parser.getCommand();

            // Process the command, and assign the result to finished.
            finished = processCommand(command);
        }
        quit();
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
            System.out.println("What do you mean?");
            return false;
        }

        // Check the first word of the command against each of the enums,
        // and run the appropriate method.
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
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            timer += 1;
            moveGuards();
            System.out.println("The guards are located in the following rooms");
            printGuardLocations();
            System.out.println("You are in the following room: " + currentRoom.getName());
            forcedToQuit = checkForBusted();
            if (timer >= timerPoint + powerOffTime && !powerStatus) {
                powerStatus = true;
                System.out.println("The power is back on");
            }
        }
        return forcedToQuit;
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
        if (currentRoom.getPowerSwitch() == null) {
            System.out.println("There is no powerswitch in this room");
            return;
        }
        if (!currentRoom.getPowerSwitch().getIsOn()) {
            return;
        } else if (currentRoom.getPowerSwitch().getIsOn()) {
            currentRoom.getPowerSwitch().turnPowerOff();
            System.out.println("The power will be turned off, for " + powerOffTime + " turns");
            timerPoint = timer;
            powerStatus = false;
        }
    }

    private boolean stealItem() {
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
        boolean wantToQuit = false;
        if (currentRoom.getLocation().getXY() == 2) {
            wantToQuit = escaped();
        } else {
            System.out.println("There is no door to escape through");
        }
        return wantToQuit;
    }

    public boolean escaped() {
        reset();
        boolean lootAdded = inventory.addToLoot();
        System.out.println("You hide in the bush outside the museum. The police arrive. For some reason, they don't notice you");
        if (lootAdded) {
            System.out.println("You hide the item you stole, in the bush");
        }
        System.out.println("Do you want to go back inside?");
        boolean choiceMade = false;
        while (!choiceMade) {
            Command command = parser.getCommand();
            CommandWord commandWord = command.getCommandWord();
            if (commandWord == CommandWord.YES) {
                currentRoom.getLongDescription();
                return false;
            } else if (commandWord == CommandWord.NO) {
                return true;
            } else {
                System.out.println("What do you mean?");
            }
        }
        return false;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void moveGuards() {
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
        String[] directions = {"north", "south", "east", "west"};
        int number = (int) (Math.random() * directions.length);
        return directions[number];
    }

    public void printGuardLocations() {
        for (Guard guard : guards) {
            System.out.print(guard.getRoom().getName() + "\t");
        }
        System.out.println();
    }

    public boolean checkForBusted() {
        for (Guard guard : guards) {
            if (currentRoom.getLocation().getXY() == guard.getRoom().getLocation().getXY()) {
                printBusted();
                gotBusted = true;
                return true;
            }
        }
        return false;
    }

    public void printBusted() {
        System.out.println("Before you are able to scratch your ass, the guards jump you, and beat the shit out of you");
    }

    public void quit() {
        if (gotBusted) {
            System.out.println("You got busted. No points for you. Better luck next time");
        } else {
            int points = inventory.calculatePoints();
            if (points > 0) {
                System.out.println("You grab your loot from the bush, and run. You won the game. You got " + points + " points");
                inventory.printLoot();
            } else {
                System.out.println("You didn't steal anything. You didn't get arrested though. Thumbs up for that");
            }
        }
        System.out.println("Game over");
    }

    public void reset() {
        guards[0].setRoom(rooms.get(0));
        guards[1].setRoom(rooms.get(31));
        int powerSwitchLocation = powerSwitchRoom.getLocation().getXY();
        rooms.get(powerSwitchLocation).getPowerSwitch().turnPowerOn();
        powerStatus = true;
        inventory.getInventory().trimToSize();
        if (!inventory.getInventory().isEmpty()) {
            Item.spawnItem(itemSpawnPointRooms);
        }
    }
}

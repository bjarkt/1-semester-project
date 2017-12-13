package Business;

import Acq.Direction;
import Acq.IData;
import Acq.IGuard;

import java.util.*;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game {

    private Parser parser; // the parser is used to get the players command inputs
    private IData data;
    private HighScoreManager highScoreManager;
    private boolean saved;
    private String playerName;
    private String globalMessage;

    private Room currentRoom; // the room that the player is currently in
    private Room oldRoom;
    private HashMap<Location, Room> rooms; // a map storing all the rooms

    private PowerRelay[] powerRelays;
    private List<Room> powerRelayLocations;
    private HashSet<Room> lockedRooms;
    private Room powerSwitchRoom; // the room with the powerswitch
    private Location powerSwitchLocation; // the coordinates of the room with the powerswitch
    private boolean powerStatus; // true when the power is on

    private Item key;
    private Room keyLocation;
    private String itemName; // the name of the last spawned item

    private Guard[] guards; // the guards
    private FriendlyNpc friendlyNpc;

    private Inventory inventory; // the players inventory

    private final int startPowerOffTime;
    private int timer; // the amount of turns taken
    private int timerPoint; // the last the powerswitch was turned off
    private int powerOffTime; // the amount of turns that the power is turned off

    private boolean policeAlerted; // true when the police is alerted
    private boolean policeArrived; // true if the police has arrived
    private int policeArrivalTime; // the amount of turns it takes for the police to arrive
    private int alertPoint; // the last time the police was alerted
    private boolean gotBusted; // true if you got busted by a guard

    private boolean textMode;

    // List of rooms, in which Spawnable objects spawn
    private List<Room> guardSpawnPointRooms;
    private List<Room> switchSpawnPointRooms;
    private List<Room> relaySpawnPointRooms;
    private List<Room> itemSpawnPointRooms; // the rooms that items can spawn in


    /**
     * Create a new game
     *
     * @param textMode if the game is run with a GUI - false, or as console game - true.
     */
    public Game(boolean textMode) {
        parser = new Parser(); // Instantiate the parser used to parse commands.
        highScoreManager = new HighScoreManager();
        saved = false;
        globalMessage = "";

        powerRelays = new PowerRelay[3];
        powerRelayLocations = new ArrayList<>();
        lockedRooms = new HashSet<>();
        powerStatus = true; // turn on the power

        key = new Item(true);

        guards = new Guard[2]; // Create the guards
        friendlyNpc = new FriendlyNpc();

        inventory = new Inventory(); // Instantiate the inventory

        startPowerOffTime = 10;// set the time that the power is turned
        timer = 0; // Instantiate the timer
        powerOffTime = startPowerOffTime;

        policeArrived = false; // the police has not arrived yet
        policeArrivalTime = 5; // set the time that it takes for the police to arrive
        gotBusted = false; // the player has not been busted yet

        this.textMode = textMode;

        // spawnpoint rooms
        guardSpawnPointRooms = new ArrayList<>();
        itemSpawnPointRooms = new ArrayList<>(); // Instantiate the spawnpoints of items
        switchSpawnPointRooms = new ArrayList<>();
        relaySpawnPointRooms = new ArrayList<>();

        createRooms(); // create the rooms
    }

    /**
     * Create the rooms, spawn the objects, and set the current room.
     */
    private void createRooms() {

        // Create the rooms
        Room room00, room01, room02, room03, room04, room05, room06, room07, room08, room09,
                room10, room11, room12, room13, room14, room15, room16, room17, room18, room19, noRoom;

        // Instantiate the rooms, and write their descriptions.
        room00 = new Room("room00", "at the entrance of the museum", "NSE", 0, 0);
        room01 = new Room("room01", "in room 1", "NEW", 1, 0);
        room02 = new Room("room02", "in room 2", "NEW", 2, 0);
        room03 = new Room("room03", "in room 3", "NEW", 3, 0);
        room04 = new Room("room04", "in room 4", "NW", 4, 0);

        room05 = new Room("room05", "in room 5", "NSE", 0, 1);
        room06 = new Room("room06", "in room 6", "NSEW", 1, 1);
        room07 = new Room("room07", "in room 7", "NSEW", 2, 1);
        room08 = new Room("room08", "in room 8", "NSEW", 3, 1);
        room09 = new Room("room09", "in room 9", "NSW", 4, 1);

        room10 = new Room("room10", "in room 10", "NSE", 0, 2);
        room11 = new Room("room11", "in room 11", "NSEW", 1, 2);
        room12 = new Room("room12", "in room 12", "NSEW", 2, 2);
        room13 = new Room("room13", "in room 13", "NSEW", 3, 2);
        room14 = new Room("room14", "in room 14", "SW", 4, 2);

        room15 = new Room("room15", "in room 15", "SE", 0, 3);
        room16 = new Room("room16", "in room 16", "SEW", 1, 3);
        room17 = new Room("room17", "in room 17", "SEW", 2, 3);
        room18 = new Room("room18", "in room 18. There are stairs to the upper floor, to the east", "SEW-Stairs", 3, 3);
        room19 = new Room("room19", "in room 19, on the upper floor. There are stairs to the groundfloor, to the west", "W-Stairs", 4, 3);
        noRoom = new Room("nowhere", "nowhere", "nowhere", 9, 9);

        // lock the appropriate doors
        lockedRooms.add(room19);
        for (Room room : lockedRooms) {
            room.lock();
        }

        // place the key
        keyLocation = room15;
        keyLocation.setItem(key);

        Collections.addAll(guardSpawnPointRooms, room04, room15);
        Collections.addAll(itemSpawnPointRooms, room02, room13, room16, room19);
        Collections.addAll(switchSpawnPointRooms, room04, room07, room10);
        Collections.addAll(relaySpawnPointRooms, room03, room05, room09, room11, room14, room18);

        // spawn the guards
        List<Room> guardRooms = Guard.spawn(guardSpawnPointRooms);
        guards[0] = guardRooms.get(0).getGuards()[0];
        guards[1] = guardRooms.get(1).getGuards()[0];

        guards[0].setOldRoom(guardSpawnPointRooms.get(0));
        guards[1].setOldRoom(guardSpawnPointRooms.get(1));

        // spawn the powerswitch in one of three rooms
        powerSwitchRoom = PowerSwitch.spawn(switchSpawnPointRooms).get(0);
        powerSwitchLocation = powerSwitchRoom.getLocation();

        // spawn powerRelays in three out of 6 random rooms
        powerRelayLocations = new ArrayList<>(PowerRelay.spawn(relaySpawnPointRooms));
        int i = 0;
        for (Room relayRoom : powerRelayLocations) {
            powerRelays[i] = relayRoom.getPowerRelay();
            i++;
        }

        // spawn items
        itemName = Item.spawn(itemSpawnPointRooms).get(0).getItems().getName();

        // Add the rooms to a map, using their locations' coordinates as keys
        rooms = new HashMap<>();
        rooms.put(room01.getLocation(), room01);
        rooms.put(room00.getLocation(), room00);
        rooms.put(room02.getLocation(), room02);
        rooms.put(room03.getLocation(), room03);
        rooms.put(room04.getLocation(), room04);
        rooms.put(room05.getLocation(), room05);
        rooms.put(room06.getLocation(), room06);
        rooms.put(room07.getLocation(), room07);
        rooms.put(room08.getLocation(), room08);
        rooms.put(room09.getLocation(), room09);
        rooms.put(room10.getLocation(), room10);
        rooms.put(room11.getLocation(), room11);
        rooms.put(room12.getLocation(), room12);
        rooms.put(room13.getLocation(), room13);
        rooms.put(room14.getLocation(), room14);
        rooms.put(room15.getLocation(), room15);
        rooms.put(room16.getLocation(), room16);
        rooms.put(room17.getLocation(), room17);
        rooms.put(room18.getLocation(), room18);
        rooms.put(room19.getLocation(), room19);
        rooms.put(noRoom.getLocation(), noRoom);

        // Set the room, in which the player starts.
        currentRoom = rooms.get(room00.getLocation()); // set the room in which the player starts
        oldRoom = rooms.get(room00.getLocation());
        // Set the exits for each room
        HashSet<Room> specialRooms = new HashSet<>();
        specialRooms.add(room14);
        specialRooms.add(room19);
        Room.setExits(rooms, specialRooms);
    }

    /**
     * The method in which the main game loop happens.
     */
    private void play() {
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

    /**
     * Print welcome and description of the current room.
     */
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

    /**
     * Processes the command, returning either false or true.
     * If true is returned, the game quits.
     *
     * @param command command to process
     * @return if true, the game will quit.
     */
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
            BooleanMessage message = goRoom(command);
            wantToQuit = message.getABoolean();
            System.out.println(message.getMessage());
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.INTERACT) {
            System.out.println(interact().getMessage());
        } else if (commandWord == CommandWord.STEAL) {
            BooleanMessage stealItemMessage = stealItem();
            System.out.println(stealItemMessage.getMessage());
            wantToQuit = stealItemMessage.getABoolean();
        } else if (commandWord == CommandWord.ESCAPE) {
            System.out.println("Do you want to go back inside?");
            Command escapeCommand = parser.getCommand();
            wantToQuit = escape(escapeCommand);
        } else if (commandWord == CommandWord.HIDE) {
            wantToQuit = hide();
        } else if (commandWord == CommandWord.CALL) {
            System.out.println(call());
        } else if (commandWord == CommandWord.SAVE) {
            save(playerName);
            wantToQuit = true;
        }
        if (gotBusted || policeArrived) {
            wantToQuit = true;
        }

        return wantToQuit;
    }

    /**
     * Start text version of game.
     */
    public void startGame() {
        Command command;
        CommandWord commandWord;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Do you want to start a new game, load a saved game, or print the current highscores? " +
                    "(" + CommandWord.LOAD.toString() + "/" + CommandWord.NEW + "/" + CommandWord.HIGHSCORE + ")");
            command = parser.getCommand();
            commandWord = command.getCommandWord();

            if (commandWord == CommandWord.LOAD) {
                if (data.doesGameSaveFileExist()) {
                    load();
                    playerName = data.load().get("playerName");
                    data.deleteFile();
                    play();
                } else {
                    System.out.println("File does not exist");
                    commandWord = null;
                }
            } else if (commandWord == CommandWord.NEW) {
                data.deleteFile();
                System.out.println("Enter your name: ");
                System.out.print("> ");
                playerName = sc.nextLine().replace(" ", "-");
                play();
            } else if (commandWord == CommandWord.HIGHSCORE) {
                System.out.println(highScoreManager.getHighScores());
            } else if (commandWord == CommandWord.QUIT) {
                System.out.println("The game has been closed.");
            }
        }
        while (!(commandWord == CommandWord.LOAD || commandWord == CommandWord.NEW || commandWord == CommandWord.QUIT));

    }

    /**
     * Prints the commands.
     */
    private void printHelp() {
        System.out.println("You are inside the museum");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Updates the currentRoom variable, and prints description of the room.
     *
     * @param command command that contains the direction to go in.
     * @return BooleanMessage that contains true, if the player was forced to quit.
     */
    BooleanMessage goRoom(Command command) {
        BooleanMessage booleanMessage = new BooleanMessage();
        boolean forcedToQuit = false;

        // Stop the method if a second word isn't supplied.
        if (!command.hasSecondWord()) {
            booleanMessage.setMessage("Go where?");
            booleanMessage.setaBoolean(false);
            return booleanMessage;
        }

        // In the go command, the second word is the direction.
        String direction = command.getSecondWord();

        if (!Direction.isInEnum(direction)) {
            booleanMessage.setMessage("That is not a correct direction");
            booleanMessage.setaBoolean(false);
            return booleanMessage;
        }

        // check if the player has a key
        boolean gotKey = false;
        for (Item item : inventory.getInventory()) {
            if (item.isKey()) {
                gotKey = true;
            }
        }
        // Retrieve the room, which is stored in the hashmap of exits.
        // null is assigned to nextRoom, if there is no value for the key (direction).
        Room nextRoom = rooms.get(currentRoom.getExit(Direction.valueOf(direction.toUpperCase())));

        if (nextRoom == null) {
            booleanMessage.setMessage("There is no door!"); // when there is no exit
        } else if (nextRoom.isLocked() && !gotKey) {
            booleanMessage.setMessage("The door is locked!\n\"You need to find a key.\"");
        } else {
            if (nextRoom.isLocked() && gotKey) {
                booleanMessage.setMessage("The door is locked.\nYou use your key to open it.");
                nextRoom.unlock();
            }
            oldRoom = currentRoom;
            currentRoom = nextRoom;
            if (textMode) System.out.println(currentRoom.getLongDescription());
            timer += 1;
            moveGuards(); // move guards
            printGuardLocations();
            forcedToQuit = checkForBusted();
            BooleanMessage timerMessage = checkTimer();
            if (textMode) System.out.println(timerMessage.getMessage());
            if (timerMessage.getABoolean()) {
                booleanMessage.setaBoolean(true);
                booleanMessage.setMessage("");
                return booleanMessage;
            }
        }
        booleanMessage.setaBoolean(forcedToQuit);
        return booleanMessage;
    }

    /**
     * check how many turns there are left before power turns back on. Alert the police if the timer runs out.
     *
     * @return true, if the player has lost the game
     */
    private BooleanMessage checkTimer() {
        BooleanMessage message = new BooleanMessage();
        String s = "";
        if (timer == timerPoint + powerOffTime / 2 && !powerStatus) {
            s += "You have " + powerOffTime / 2 + " turns left before power turns on.";
        }
        // check if it is time for the power to turn back on
        if (timer >= timerPoint + powerOffTime && !powerStatus) {
            powerStatus = true;
            rooms.get(powerSwitchLocation).getPowerSwitch().turnPowerOn();
            s += "The power is back on\n";
            globalMessage += "The power is back on\n";
            // alert the police
            if (!policeAlerted) {
                s += "The police has been alerted";
                globalMessage += "The police has been alerted. They will arrive in " + policeArrivalTime + " rounds.";
                policeAlerted = true;
                alertPoint = timer;
            }
        }
        // check if the police has arrived
        if (timer >= alertPoint + policeArrivalTime && policeAlerted) {
            policeArrived = true;
            message.setMessage(s);
            message.setaBoolean(true);
            return message;
        }
        message.setMessage(s);
        message.setaBoolean(false);
        return message;
    }

    /**
     * Quit the game if the command is quit and has no second word.
     *
     * @param command
     * @return true, if a second word has not been supplied
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    /**
     * used to turn off the powerswitch and sabotage powerRelays
     *
     * @return BooleanMessage that contains true if the player got busted while interacting.
     */
    BooleanMessage interact() {
        String s = "";
        BooleanMessage booleanMessage = new BooleanMessage();
        if (currentRoom.getPowerRelay() != null) {
            if (!powerStatus) {
                if (currentRoom.getPowerRelay().getStatus()) { // if the power relay has not been sabotaged
                    currentRoom.getPowerRelay().sabotage();
                    this.powerOffTime += currentRoom.getPowerRelay().getTimeBoost();
                    s += "You sabotaged the relay\n";
                    s += "You have got " + currentRoom.getPowerRelay().getTimeBoost() + " more rounds before the power comes back\n";
                } else { // if the powerrelay has been sabotaged
                    s += "This power relay is already sabotaged.";
                }
            } else {
                s += "When you try to sabotage the relay, you trigger the alarm\n";
                this.gotBusted = true;
            }
        } else if (currentRoom.getPowerSwitch() != null) {
            if (!currentRoom.getPowerSwitch().getIsOn()) {
                s += "The power is already turned off\n";
                booleanMessage.setMessage(s);
                return booleanMessage;
            } else if (currentRoom.getPowerSwitch().getIsOn()) {
                currentRoom.getPowerSwitch().turnPowerOff();
                s += "The power will be turned off, for " + powerOffTime + " turns\n";
                timerPoint = timer;
                powerStatus = false;
            }
        } else {
            s += "There is nothing to interact with\n";
        }
        booleanMessage.setMessage(s);
        booleanMessage.setaBoolean(gotBusted);
        return booleanMessage;
    }

    /**
     * Attemp to steal an item
     *
     * @return return a BooleanMessage that contains true if the player got busted while stealing an item.
     */
    BooleanMessage stealItem() {
        // used to steal an item
        boolean forcedToQuit = false;

        // check if the room contains a key
        boolean containsKey = false;

        if (currentRoom.getItems() != null && currentRoom.getItems().isKey()) {
            containsKey = true;
        }

        BooleanMessage message = new BooleanMessage();
        if (currentRoom.getItems() != null) {
            if (!powerStatus || containsKey) {
                if (inventory.addToInventory(currentRoom.getItems())) {
                    message.setMessage("You have stolen a " + currentRoom.getItems().getName());
                    currentRoom.removeItem();
                } else {
                    message.setMessage("You inventory is full");
                }
            } else {
                message.setMessage("The alarm starts ringing");
                printBusted();
                forcedToQuit = true;
            }
        } else {
            message.setMessage("There is no item to steal");
        }
        message.setaBoolean(forcedToQuit);
        return message;
    }

    /**
     * Attempt to escape from the museum
     *
     * @param command command that contains escape command
     * @return false if the player wants to go back inside.
     */
    boolean escape(Command command) {
        // used to escape the museum through the entrance
        boolean wantToQuit = false;
        if (currentRoom.getLocation().equals(new Location(0, 0))) {
            wantToQuit = escaped(command);
        } else {
            if (textMode) System.out.println("There is no door to escape through");
        }
        return wantToQuit;
    }

    /**
     * Add the players inventory to the loot list.
     * Ask the player if they want to go back inside
     *
     * @param command a command
     * @return false if the player wants to go back inside.
     */
    private boolean escaped(Command command) {
        // reset the game
        reset();
        boolean lootAdded = inventory.addToLoot();
        if (textMode)
            System.out.println("You hide in the bush outside the museum. The police arrive. For some reason, they don't notice you");
        if (lootAdded) {
            if (textMode) System.out.println("You hide the item you stole, in the bush");
        }
        // either quit the game, or continue
        boolean choiceMade = false;
        while (!choiceMade) {
            //Command command = parser.getCommand();
            CommandWord commandWord = command.getCommandWord();
            if (commandWord == CommandWord.YES) {
                if (textMode) System.out.println(currentRoom.getLongDescription());
                return false;
            } else if (commandWord == CommandWord.NO) {
                return true;
            } else {
                if (textMode) System.out.println("What do you mean?");
            }
        }
        return false;
    }

    /**
     * Move all the guards
     */
    private void moveGuards() {
        // move the guards
        for (Guard guard : guards) {
            guard.moveGuard(rooms);
        }
    }

    /**
     * Generate a random direction. Direction.NOWHERE is one of the options. This is done to make the guards not move sometimes.
     *
     * @return a {@link Direction}.
     */
    static Direction generateRandomDirection() {
        // generate a random direction
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.NOWHERE};
        int number = (int) (Math.random() * directions.length);
        return directions[number];
    }

    /**
     * Print the location of the guards.
     */
    private void printGuardLocations() {
        // print the guard's locations
        if (textMode) {
            System.out.println("The guards are located in the following rooms");
            for (Guard guard : guards) {
                System.out.print(guard.getRoom().getName() + "\t");
            }
            System.out.println();

            System.out.println(friendlyNpc.help(currentRoom.getLocation(), guards));
        }
    }

    /**
     * Attempt to hide. If a guard enters your room, 50% change to not get busted.
     *
     * @return true if the player got busted.
     */
    boolean hide() {
        // used to hide from the guards
        // return true, if the players has lost the game
        boolean forcedToQuit;
        boolean hasCheckedForTime;
        timer += 1;
        moveGuards();
        printGuardLocations();
        BooleanMessage message = checkTimer();
        forcedToQuit = message.getABoolean();
        if (textMode) System.out.println("You hide.");
        if (forcedToQuit) {
            return forcedToQuit;
        }
        hasCheckedForTime = true;
        while (checkForBusted()) {
            if (!hasCheckedForTime) {
                message = checkTimer();
                forcedToQuit = message.getABoolean();
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
                globalMessage = "A guard entered your room, but didn't see you.";
                if (textMode) System.out.println(globalMessage);
                timer += 1;
                moveGuards();
                printGuardLocations();
                gotBusted = false;
            }
        }
        if (textMode) System.out.println("You are in the following room: " + currentRoom.getName());
        return forcedToQuit;
    }

    /**
     * Call Mastermind Daniel, he tells you what to do.
     *
     * @return a string containing text from Mastermind Daniel.
     */
    String call() {
        // call your friend
        // your friend tells you, what your current objective is
        String s = "";
        s += "\"Mastermind Daniel here\"\n";
        if (policeAlerted && inventory.getInventory().isEmpty()) {
            s += "\"The police has been alerted. You need to get out quickly. You can always go back inside later\"";
        } else if (policeAlerted) {
            s += "\"You got the " + itemName + ", but the police are on their way. Get out quickly\"";
        } else if (inventory.getInventory().isEmpty()) {
            s += "\"You need to steal a " + itemName + "\"\n";
            s += "\"Remember to turn off the power first, or the alarm will trigger\"";
        } else {
            s += "\"You got the item. Get out of here quickly\"";
        }
        return s;
    }

    /**
     * Check if the player got busted.
     * Checks if the guard and player switched rooms.
     *
     * @return true if the player got busted.
     */
    private boolean checkForBusted() {
        // return true if there is a guard in the same room as the player
        for (Guard guard : guards) {
            if (currentRoom.getLocation().equals(guard.getRoom().getLocation())) {
                gotBusted = true;
                return true;
            }
            if (currentRoom.getLocation().equals(guard.getOldRoom().getLocation()) && oldRoom.getLocation().equals(guard.getRoom().getLocation())) {
                gotBusted = true;
                return true;
            }

        }
        return false;
    }

    /**
     * Print busted message.
     */
    private void printBusted() {
        // message that are printed, when the player gets busted by the guards
        if (textMode) System.out.println("Before you are able to scratch your ass, the guards jump you, and beat the shit out of you");
    }

    /**
     * the game is over
     * the players points and other information are printed
     */
    private void quit() {
        if (saved) {
            System.out.println("The game has been saved.");
        } else {

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
    }

    /**
     * Reset the game.
     */
    private void reset() {
        // reset the guards
        guards[0].setRoom(rooms.get(new Location(4, 0)));
        guards[1].setRoom(rooms.get(new Location(0, 3)));

        // turn the power back on
        rooms.get(powerSwitchLocation).getPowerSwitch().turnPowerOn();
        powerStatus = true;
        powerOffTime = startPowerOffTime;
        for (PowerRelay powerRelay : this.powerRelays) {
            powerRelay.restore();
        }
        // lock the appropriate doors
        for (Room room : lockedRooms) {
            room.lock();
        }
        // place the key
        keyLocation.setItem(key);
        // set the alertstatus of the police to false
        policeAlerted = false;
        // spawn a new item, if you stole the last one
        inventory.getInventory().trimToSize();
        if (!inventory.getInventory().isEmpty()) {
            itemName = Item.spawn(itemSpawnPointRooms).get(0).getItems().getName();
        }
    }

    /**
     * Save the state of the game
     *
     * @param playerName the name of the current player.
     */
    public void save(String playerName) {
        LinkedHashMap<String, String> mapToSave = new LinkedHashMap<>();
        mapToSave.put("playerName", playerName);

        mapToSave.put("currentRoom", currentRoom.getName());
        mapToSave.put("powerSwitchStatus", String.valueOf(powerStatus));
        mapToSave.put("powerSwitchRoom", powerSwitchRoom.getName());

        for (int i = 0; i < powerRelays.length; i++) {
            mapToSave.put("powerRelayStatus_" + i, String.valueOf(powerRelays[i].getStatus()));
            mapToSave.put("powerRelayID_" + i, String.valueOf(powerRelays[i].getID()));
            mapToSave.put("powerRelayLocation_" + i, powerRelayLocations.get(i).getName());
        }

        mapToSave.put("itemName", itemName);

        for (int i = 0; i < inventory.getInventory().size(); i++) {
            mapToSave.put("inventory_" + i, inventory.getInventory().get(i).getName());
        }
        for (int i = 0; i < inventory.getLoot().size(); i++) {
            mapToSave.put("loot_" + i, inventory.getLoot().get(i).getName());
        }

        mapToSave.put("timer", String.valueOf(timer));
        mapToSave.put("timerPoint", String.valueOf(timerPoint));
        mapToSave.put("powerOffTime", String.valueOf(powerOffTime));
        mapToSave.put("policeAlerted", String.valueOf(policeAlerted));
        mapToSave.put("alertPoint", String.valueOf(alertPoint));

        mapToSave.put("guard0", guards[0].getRoom().getName());
        mapToSave.put("guard1", guards[1].getRoom().getName());

        for (Room room : rooms.values()) {
            if (room.getItems() != null && !room.getItems().getName().equalsIgnoreCase("key")) {
                mapToSave.put("itemRoom", room.getName());
            }
        }
        int i = 0;
        for (Room lockedRoom : lockedRooms) {
            mapToSave.put("lockedRoomName_" + i, lockedRoom.getName());
            mapToSave.put("lockedRoomStatus_" + i, String.valueOf(lockedRoom.isLocked()));
        }

        mapToSave.put("keyLocationRoom", keyLocation.getName());
        if (keyLocation.getItems() != null) {
            mapToSave.put("doesKeyLocationRoomContainItem", "true");
        } else {
            mapToSave.put("doesKeyLocationRoomContainItem", "false");
        }

        data.save(mapToSave);
        saved = true;
    }

    /**
     * Load the state of the game.
     */
    public void load() {
        Map<String, String> map;
        map = data.load();
        if (map == null) {
            return;
        }

        powerRelayLocations.clear();
        lockedRooms.clear();
        for (Room room : rooms.values()) {
            room.removeItem();
            room.removeGuard();
            room.setPowerSwitch(null);
            room.setPowerRelay(null);
            powerRelayLocations = new ArrayList<>();
        }

        for (Room room : rooms.values()) {
            if (room.getName().equals(map.get("currentRoom"))) {
                currentRoom = room;
            }
        }

        powerStatus = Boolean.parseBoolean(map.get("powerSwitchStatus"));

        for (int i = 0; i < powerRelays.length; i++) {
            powerRelays[i].setStatus(Boolean.parseBoolean(map.get("powerRelayStatus_" + i)));
            powerRelays[i].setID(Integer.parseInt(map.get("powerRelayID_" + i)));
            for (Room room : rooms.values()) {
                if (room.getName().equalsIgnoreCase(map.get("powerRelayLocation_" + i))) {
                    powerRelayLocations.add(room);
                    room.setPowerRelay(powerRelays[i]);
                }
            }
        }


        for (Room room : rooms.values()) {
            if (room.getName().equals(map.get("powerSwitchRoom"))) {
                powerSwitchRoom = room;
                powerSwitchLocation = powerSwitchRoom.getLocation();
                room.setPowerSwitch(new PowerSwitch());
                room.getPowerSwitch().turnPowerOn();
            }
        }

        itemName = map.get("itemName");

        for (String s : map.keySet()) {
            if (s.startsWith("inventory_")) {
                String itemName = map.get(s);
                Item item;
                if (itemName.equalsIgnoreCase("key")) {
                    item = new Item(true);
                } else {
                    item = new Item(itemName);
                }
                inventory.addToInventory(item);
            }
        }

        for (String s : map.keySet()) {
            if (s.startsWith("loot_")) {
                Item item = new Item(map.get(s));
                inventory.getLoot().add(item);
            }
        }

        timer = Integer.parseInt(map.get("timer"));
        timerPoint = Integer.parseInt(map.get("timerPoint"));
        powerOffTime = Integer.parseInt(map.get("powerOffTime"));
        policeAlerted = Boolean.parseBoolean(map.get("policeAlerted"));
        alertPoint = Integer.parseInt(map.get("alertPoint"));

        for (Room room : rooms.values()) {
            if (room.getName().equals(map.get("guard0"))) {
                room.addGuard(guards[0]);
                guards[0].setRoom(room);
            }
            if (room.getName().equals(map.get("guard1"))) {
                room.addGuard(guards[1]);
                guards[1].setRoom(room);
            }

            if (room.getName().equals(map.get("itemRoom"))) {
                Item item = new Item(map.get("itemName"));
                room.setItem(item);
            }
        }

        int i = 0;
        for (String s : map.keySet()) {
            if (s.startsWith("lockedRoomName_")) {
                for (Room room : rooms.values()) {
                    if (room.getName().equals(map.get(s))) {
                        lockedRooms.add(room);
                        boolean status = Boolean.parseBoolean(map.get("lockedRoomStatus_" + i));
                        room.setLocked(status);
                        i++;
                    }
                }
            }
        }

        for (Room room : rooms.values()) {
            if (room.getName().equals(map.get("keyLocationRoom"))) {
                keyLocation = room;
                Item key = null;
                if (Boolean.parseBoolean(map.get("doesKeyLocationRoomContainItem"))) {
                    key = new Item(true);
                }
                keyLocation.setItem(key);
            }
        }

    }

    /**
     * Get the possible rooms, where an item can spawn in.
     *
     * @return List of rooms
     */
    List<Room> getItemSpawnPointRooms() {
        return itemSpawnPointRooms;
    }

    /**
     * Get the possible rooms that a power switch can spawn in.
     *
     * @return list of rooms
     */
    List<Room> getSwitchSpawnPointRooms() {
        return switchSpawnPointRooms;
    }

    /**
     * Get the rooms that a power relay is in
     *
     * @return list of rooms
     */
    List<Room> getPowerRelayLocations() {
        return powerRelayLocations;
    }

    /**
     * Get the inventory
     *
     * @return inventory
     */
    Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the current room
     *
     * @return a room
     */
    Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Get the guards
     *
     * @return array of guards
     */
    IGuard[] getGuards() {
        return guards;
    }

    /**
     * Get the rooms in the game
     *
     * @return list of rooms
     */
    List<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    /**
     * Is the player at the entrance?
     *
     * @return true if the player is at the entrance of the museum
     */
    boolean isAtEntrace() {
        return currentRoom.getLocation().equals(new Location(0, 0));
    }

    /**
     * Is the power turned on?
     *
     * @return the status of the power
     */
    boolean isPowerStatus() {
        return powerStatus;
    }

    /**
     * Is the policed alerted?
     *
     * @return true if the police is alerted
     */
    boolean isPoliceAlerted() {
        return policeAlerted;
    }

    /**
     * Has the player been busted.
     *
     * @return true if the player got busted
     */
    boolean isGotBusted() {
        return gotBusted;
    }

    /**
     * has the police arrived?
     *
     * @return true if the police has arrived
     */
    boolean isPoliceArrived() {
        return policeArrived;
    }

    /**
     * The amount if time left, before the power turns on
     *
     * @return time left before power turns on.
     */
    int getTimeBeforePowerTurnsBackOn() {
        return (timerPoint + powerOffTime) - timer;
    }

    /**
     * Get the amount of time left, before the police arrives.
     *
     * @return time left before the police arrives
     */
    int getTimeBeforePoliceArrives() {
        return (timerPoint + powerOffTime + policeArrivalTime) - timer;
    }

    /**
     * Call the friendly NPC. He tells about guard location
     *
     * @return String containing text from NPC
     */
    String callFriendlyNpc() {
        return friendlyNpc.help(currentRoom.getLocation(), guards);
    }

    /**
     * Get the global message
     *
     * @return String with message
     */
    String getGlobalMessage() {
        return globalMessage;
    }

    /**
     * Set the global message.
     * To clear the message, use empty string ("")
     *
     * @param globalMessage new global message
     */
    void setGlobalMessage(String globalMessage) {
        this.globalMessage = globalMessage;
    }

    /**
     * Get the power relays
     *
     * @return array of power relays
     */
    PowerRelay[] getPowerRelays() {
        return powerRelays;
    }

    /**
     * Inject data layer into the game
     *
     * @param data data facade.
     */
    public void injectData(IData data) {
        this.data = data;
        highScoreManager.injectData(data);
    }
}

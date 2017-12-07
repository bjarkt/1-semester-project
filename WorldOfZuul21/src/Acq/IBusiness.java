package Acq;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Business Facade
 */
public interface IBusiness {
    /**
     * Get the current location of the player.
     *
     * @return players location as a {@link ILocation} object.
     */
    ILocation getCurrentLocation();

    /**
     * Is the player at the entrance of the museum?
     *
     * @return true if the player is at the entrance of the museum.
     */
    boolean isAtEntrance();

    /**
     * Get the location of the guards.
     *
     * @return Location of the guards, as a {@link ILocation} array.
     */
    ILocation[] getGuardLocations();

    /**
     * Get the location of the powerrelays.
     *
     * @return location of the power relays, as a {@link ILocation} array.
     */
    ILocation[] getPowerRelayLocations();

    /**
     * Get the power relays in the game
     *
     * @return an array of {@link IPowerRelay}
     */
    IPowerRelay[] getPowerRelays();

    /**
     * Get the location of the powerswitch.
     *
     * @return location of the power switch.
     */
    ILocation getPowerSwitchLocation();

    /**
     * Get the location of the item.
     *
     * @return location of the item.
     */
    ILocation getItemLocation();

    /**
     * Delete the save game file.
     *
     * @return True if the save game file was deleted successfully.
     */
    boolean deleteSaveGameFile();

    /**
     * Get the item of the current room.
     *
     * @return the item for the current room. If the current room has no item, return null.
     */
    IItem getItemForCurrentRoom();

    /**
     * Does the current room contain an item?
     *
     * @return true if the current room contains an item.
     */
    boolean currentRoomContainsItem();

    /**
     * Does the current room contain a power switch?
     *
     * @return true if the current room contains a power switch.
     */
    boolean currentRoomContainsPowerSwitch();

    /**
     * Does the current room contain a power relay?
     *
     * @return true if the current room contains a power relay.
     */
    boolean currentRoomContainsPowerRelay();

    /**
     * Get all exits for the current room
     *
     * @return set of exits for the current room, as a set of {@link Direction}.
     */
    Set<Direction> getExitsForCurrentRoom();

    /**
     * Update the highscore, and save it.
     *
     * @param playerName name of the player.
     */
    void updateHighScore(String playerName);

    /**
     * Return a top five list of highscores.
     *
     * @return list of {@link IHighScore}
     */
    List<IHighScore> getHighScores();

    /**
     * Get the current score of the player.
     *
     * @return the players current score.
     */
    int getCurrentHighScore();

    /**
     * Get the powerswitch status.
     *
     * @return true if the power is turned on, false if the power is turned off.
     */
    boolean getPowerStatus();

    /**
     * Get the police alerted status.
     *
     * @return true if the police has been alerted.
     */
    boolean getPoliceAlerted();

    /**
     * Get the busted status.
     *
     * @return true if the player has been busted.
     */
    boolean isGotBusted();

    /**
     * Get the police arrived status.
     *
     * @return true if the police has arrived.
     */
    boolean getPolicedArrived();

    /**
     * Get the time before the power turns back on.
     *
     * @return the amount of time remaining, before the power turns back on.
     */
    int getTimeBeforePowerTurnsBackOn();

    /**
     * Get the time before the police arrives.
     *
     * @return the amount of time remaining, before the police arrives.
     */
    int getTimeBeforePoliceArrives();

    /**
     * Get the global message.
     *
     * @return get the current global message.
     */
    String getGlobalMessage();

    /**
     * Clear the current global message.
     */
    void clearGlobalMessage();

    /**
     * Get the inventory list
     *
     * @return the list of {@link IItem} in the players inventory.
     */
    List<IItem> getInventoryList();

    /**
     * Get the loot list.
     *
     * @return the list of {@link IItem} in the players loot (not the inventory).
     */
    List<IItem> getLootList();

    /**
     * Save the game.
     *
     * @param playerName name of the player.
     */
    void save(String playerName);

    /**
     * Load the game.
     */
    void load();

    /**
     * Restart the game.
     */
    void restartGame();

    /**
     * Get the name of the player, from the game that was loaded.
     *
     * @return the name of the player, that saved the game previously.
     */
    String getLoadedPlayerName();

    /**
     * Does the game save file exist?
     *
     * @return true if the savegame file exists.
     */
    boolean doesGameSaveFileExist();

    /**
     * Move the player in a direction.
     *
     * @param direction which direction to move in.
     * @return {@link IBooleanMessage} object, which contains a true boolean value,
     * if the player successfully moved in that direction, and a string, containing a message.
     */
    IBooleanMessage goDirection(Direction direction);

    /**
     * Steal the item of the current room.
     *
     * @return {@link IBooleanMessage} object, which contains a true boolean value,
     * if the player successfully stole the item, and a string, containing a message.
     */
    IBooleanMessage steal();

    /**
     * Interact with a powerrelay or powerswitch in the current room.
     *
     * @return {@link IBooleanMessage} object, which contains a true boolean value,
     * if the player successfully interacted, and a string, containing a message.
     */
    IBooleanMessage interact();

    /**
     * Skip a turn, and hide from the guards.
     *
     * @return true if the hide was unsuccessful, and a guard saw the player.
     */
    boolean hide();

    /**
     * Escape from the museum, with the option of returning.
     *
     * @param wantToGoBackInside true, if the player wants to return to the museum.
     */
    void escape(boolean wantToGoBackInside);

    /**
     * Get the rooms of the game.
     *
     * @return list of rooms in the game.
     */
    List<IRoom> getRooms();

    /**
     * Call Master Mind Daniel, to be reminded of the objective.
     *
     * @return a string, telling the player what to do.
     */
    String callMasterMindDaniel();

    /**
     * Call the friendly NPC, who tells about the guards location.
     *
     * @return a string, telling the player about the location of the guards
     */
    String callFriendlyNPC();

    /**
     * Create a new {@link ILocation}.
     *
     * @param x x value of the location.
     * @param y y value of the location.
     * @return {@link ILocation} object created from the parameters.
     */
    ILocation newLocation(double x, double y);

    /**
     * Save the seen status of the drawables items on the minimap.
     *
     * @param mapToSave map with names of the items as key, and their status as value.
     */
    void saveSeenStatus(Map<String, String> mapToSave);

    /**
     * Get a map, containing the seen status of the minimap items.
     *
     * @return map with names of the items as key, and their status as value.
     */
    Map<String, String> loadSeenStatus();

    /**
     * Inject data into the business layer.
     *
     * @param data a data facade.
     */
    void injectData(IData data);
}

package Business;

import Acq.IItem;

import java.util.ArrayList;
import java.util.List;

public class Item implements IItem {

    private String name;
    //Naming the possible items which can be spawned
    private static String[] itemNames = {"Painting", "Bust", "Jewel", "Vase", "Diamond"};
    //Items which have been stolen are added to this arraylist, so it can't be spawned more than once
    private static List<String> usedItems = new ArrayList<>();
    private final boolean isKey;

    //Constructor for Item.
    public Item(String name) {
        this.name = name;
        isKey = false;
    }

    public Item() {
        isKey = false;
    }

    // constructor for the Key Item
    public Item(boolean isKey) {
        this.isKey = isKey;
        if (isKey) {
            name = "Key";
        }
    }

    //Getter method for "name"
    public String getName() {
        return name;
    }

    //Setter method for "name"
    public void setName(String name) {
        this.name = name;
    }

    public boolean isKey() {
        return isKey;
    }

    /**
     * This method chooses a random item to spawn from the list of items.
     * @param rooms a list of possible rooms, in which the item may spawn
     * @return a list of size 1, containing the room that the item spawned in
     */
    public static List<Room> Spawn(List<Room> rooms) {
        List<Room> rooms_ = new ArrayList<>();
        for (Room room : rooms) {
            room.setItem(null);
        }
        
        int spawnID = (int) (Math.random() * itemNames.length);
        String newName = itemNames[spawnID];

        Item obj = new Item(newName);

        int index = (int) (Math.random() * rooms.size());

        rooms.get(index).setSpawn(obj);
        rooms_.add(rooms.get(index));

        return rooms_;
    }

    @Override
    public String toString() {
        return name;
    }
}

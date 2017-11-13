package worldofzuul21;

import java.util.*;

public class Item implements Spawnable {

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
    
    /* This method chooses a random item to spawn from the list of items. If an item has been spawned before,
    it is added to usedItems, so it cannot be spawned agian  
     */
    @Override
    public List<Room> Spawn(List<Room> rooms) {
        List<Room> rooms_ = new ArrayList<>();
        for (Room room : rooms) {

            room.setItem(null);
        }
        String newName;
        int spawnID;
        int index;
        do {
            spawnID = (int) (Math.random() * itemNames.length);
            newName = itemNames[spawnID];
        } while (usedItems.contains(newName));
        usedItems.add(newName);
        Spawnable obj = new Item(newName);

        index = (int) (Math.random() * rooms.size());

        rooms.get(index).setSpawn(obj);
        rooms_.add(rooms.get(index));

        return rooms_;
    }
}

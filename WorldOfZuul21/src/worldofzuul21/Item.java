package worldofzuul21;

import java.util.*;

public class Item {

    private String name;
    //Naming the possible items which can be spawned
    private static String[] itemNames = {"Painting", "Bust", "Jewel", "Vase", "Diamond"};
    //Items which have been stolen are added to this arraylist, so it can't be spawned more than once
    private static List<String> usedItems = new ArrayList<>();
    
    //Constructor for Item.
    public Item(String name) {
        this.name = name;
    }
    
    //Getter method for "name"
    public String getName() {
        return name;
    }
    //Setter method for "name"
    public void setName(String name) {
        this.name = name;
    }
    
    /* This method chooses a random item to spawn from the list of items. If an item has been spawned before,
    it is added to usedItems, so it cannot be spawned agian  
    */
    public static String spawnItem(List<Room> rooms) {
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
        
        index = (int) (Math.random() * rooms.size());
        rooms.get(index).setItem(new Item(newName));
        return newName;
    }
}

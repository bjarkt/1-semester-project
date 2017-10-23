package worldofzuul21;

import java.util.*;

public class Item {

    private String name;

    private static String[] itemNames = {"Painting", "Bust", "Jewel", "Vase", "Diamond"};
    private static List<String> usedItems = new ArrayList<>();

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void spawnItem(List<Room> rooms) {
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
    }
}

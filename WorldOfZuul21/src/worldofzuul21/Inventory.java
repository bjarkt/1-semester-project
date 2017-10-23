package worldofzuul21;

import java.util.*;

public class Inventory {

    private final int inventorySpace = 1;
    private List<Item> loot;
    private ArrayList<Item> inventory;

    public Inventory() {
        loot = new ArrayList<>();
        inventory = new ArrayList<>(inventorySpace);
    }

    public boolean addToInventory(Item item) {
        inventory.trimToSize();
        if (inventory.size() == inventorySpace) {
            return false;
        } else {
            inventory.add(item);
            return true;
        }
    }

}

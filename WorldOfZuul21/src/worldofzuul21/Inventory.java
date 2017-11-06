package worldofzuul21;

import java.util.*;

public class Inventory {

    // the amount of space in the inventory
    private final int inventorySpace = 1;
    // the list of items, in the bush, outside the museum
    private ArrayList<Item> loot;
    // the list of items in your inventory
    private ArrayList<Item> inventory;


    /**
     * initialises the lists
     */
    public Inventory() {
        loot = new ArrayList<>();
        inventory = new ArrayList<>(inventorySpace);
    }

    /**
     *
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    /**
     *
     * @param item to add to inventory
     * @return returns true if the item was added successfully, false if there is not enough room in the inventory.
     */
    public boolean addToInventory(Item item) {
        // trims the capacity of the list, so it is equal to the size
        inventory.trimToSize();
        if (inventory.size() == inventorySpace) { // if the amount of items is equal to the max number of items allowed, do not add
            return false;
        } else {
            inventory.add(item);
            return true;
        }
    }

    /**
     *
     * @return true if the item added was successfully, false if there is no item in the inventory
     */
    public boolean addToLoot() {
        // trims the capacity of the list, so it is equal to the size
        inventory.trimToSize();
        // if there is an item in the inventory, add it to the loot and clear inventory
        if (inventory.size() > 0) {
            loot.addAll(inventory);
            inventory.clear();
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return the amount of points earned for the amount of items that has been stolen.
     */
    public int calculatePoints() {
        // trims the capacity of the list, so it is equal to the size
        loot.trimToSize();
        int numberOfStolenItems = loot.size();
        if (numberOfStolenItems == 0) {
            return 0;
        } else if (numberOfStolenItems == 1) {
            return 2;
        } else if (numberOfStolenItems == 2) {
            return 6;
        } else {
            return 10;
        }
    }

    /**
     * prints the items in the loot list
     */
    public void printLoot() {
        System.out.print("You grab the following loot: ");
        for (Item item : loot) {
            System.out.println(item.getName() + "\t");
        }
        System.out.println();
    }
}

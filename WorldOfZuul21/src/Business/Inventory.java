package Business;

import Acq.IItem;

import java.util.*;

public class Inventory {

    // the amount of space in the inventory
    private final int inventorySpace = 2;
    // the list of items, in the bush, outside the museum
    private ArrayList<IItem> loot;
    // the list of items in your inventory
    private ArrayList<IItem> inventory;

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
    public ArrayList<IItem> getInventory() {
        return inventory;
    }

    public ArrayList<IItem> getLoot() {
        return loot;
    }

    /**
     *
     * @param item to add to inventory
     * @return returns true if the item was added successfully, false if there
     * is not enough room in the inventory.
     */
    public boolean addToInventory(IItem item) {
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
     * @return true if the item added was successfully, false if there is no
     * item in the inventory
     */
    public boolean addToLoot() {
        removeKeys();
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

    private void removeKeys() {
        for (IItem item : inventory) {
            if (item.isKey()) {
                inventory.remove(item);
            }
        }
    }

    /**
     *
     * @return the amount of points earned for the amount of items that has been
     * stolen.
     */
    public int calculatePoints() {
        removeKeys();
        // trims the capacity of the list, so it is equal to the size
        loot.trimToSize();
        // Sets the score to be 2 times the amount of items in the loot ArrayList
        int score = loot.size() *2;
        return score;
        
    }

    /**
     * prints the items in the loot list
     */
    public void printLoot() {
        System.out.print("You grab the following loot: ");
        for (IItem item : loot) {
            System.out.println(item.getName() + "\t");
        }
        System.out.println();
    }
}

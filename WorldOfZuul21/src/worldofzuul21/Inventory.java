package worldofzuul21;

import java.util.*;

public class Inventory {

    private final int inventorySpace = 1;
    private ArrayList<Item> loot;
    private ArrayList<Item> inventory;

    public Inventory() {
        loot = new ArrayList<>();
        inventory = new ArrayList<>(inventorySpace);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
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

    public boolean addToLoot() {
        inventory.trimToSize();
        if (inventory.size() > 0) {
            loot.addAll(inventory);
            inventory.clear();
            return true;
        } else {
            return false;
        }
    }

    public int calculatePoints() {
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

    public void printLoot() {
        System.out.print("You grab the following loot: ");
        for (Item item : loot) {
            System.out.println(item.getName() + "\t");
        }
        System.out.println();
    }
}

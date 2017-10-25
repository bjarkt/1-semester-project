/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul21;

/**
 *
 * @author Nikolaj
 */
public class Guard {

    private final int ID;
    private Room currentRoom;
    
    Guard(int ID) {
        this.ID = ID;
    }
    
    public int getID() {
        return ID;
    }
    
    public Room getRoom() {
        return currentRoom;
    }
    
    public void setRoom(Room room) {
        currentRoom = room;
    }
}

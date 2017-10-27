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
        // ID for Guard objekt
        this.ID = ID;
    }
    
    public int getID() {
        // metohod for getting ID
        return ID;
    }
    
    public Room getRoom() {
        //metodhod for getting currentRoom
        return currentRoom;
    }
    
    public void setRoom(Room room) {
        // set metodhod for room
        currentRoom = room;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.IGuard;
import Acq.IRoom;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikolaj
 */
public class Guard implements IGuard {

    private final int ID;
    private IRoom currentRoom;
    
    public Guard(int ID) {
        // ID for Guard objekt
        this.ID = ID;
    }
    
    public int getID() {
        // metohod for getting ID
        return ID;
    }
    
    public IRoom getRoom() {
        //metodhod for getting currentRoom
        return currentRoom;
    }
    
    public void setRoom(IRoom room) {
        // set metodhod for room
        currentRoom = room;
    }

    public static List<IRoom> Spawn(List<IRoom> rooms) {
        Guard[] guards = new Guard[2];
        List<IRoom> rooms_ = new ArrayList<>();
        for (int i = 0; i < guards.length; i++) {
            guards[i] = new Guard(i+1);
            guards[i].setRoom(rooms.get(i));
            rooms.get(i).addGuard(guards[i]);
            rooms_.add(rooms.get(i));
        }

        return rooms_;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.Direction;
import Acq.IGuard;

import java.util.*;

/**
 * @author Nikolaj
 */
public class Guard implements IGuard {

    private final int ID;
    private Room currentRoom;
    private Room oldRoom;

    public Guard(int ID) {
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

    public static List<Room> Spawn(List<Room> rooms) {
        Guard[] guards = new Guard[2];
        List<Room> rooms_ = new ArrayList<>();
        for (int i = 0; i < guards.length; i++) {
            guards[i] = new Guard(i + 1);
            guards[i].setRoom(rooms.get(i));
            rooms.get(i).addGuard(guards[i]);
            rooms_.add(rooms.get(i));
        }

        return rooms_;
    }

    public void moveGuard(HashMap<Location, Room> rooms) {
        Room nextRoom;
        Direction direction;

        Set<Direction> validDirections = new HashSet<>(this.getRoom().getExits().keySet());
        validDirections.add(Direction.NOWHERE); // An extra option, that will make the guard not move, sometimes.
        do {
            direction = Game.generateRandomDirection();
            nextRoom = rooms.get(this.getRoom().getExit(direction));
        }
        while (!validDirections.contains(direction));

        if (nextRoom != null) {
            this.setOldRoom(this.getRoom());
            this.getRoom().removeGuard();
            this.setRoom(nextRoom);
            nextRoom.addGuard(this);
        }
    }

    public void setOldRoom(Room oldRoom) {
        this.oldRoom = oldRoom;
    }

    public Room getOldRoom() {
        return oldRoom;
    }

}

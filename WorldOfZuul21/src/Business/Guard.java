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

    /**
     * Create a new guard object
     *
     * @param ID id of the guard.
     */
    private Guard(int ID) {
        this.ID = ID;
    }

    /**
     * Get the id for the guard
     *
     * @return id
     */
    public int getID() {
        return ID;
    }

    @Override
    public Room getRoom() {
        return currentRoom;
    }

    /**
     * Set the current room of the guard
     *
     * @param room new current room.
     */
    public void setRoom(Room room) {
        currentRoom = room;
    }

    /**
     * spawn the guard the guard in one of the places specified by the rooms parameter.
     *
     * @param rooms a list of possible spawn places.
     * @return a list containing the room, that the guard was spawned in.
     */
    public static List<Room> spawn(List<Room> rooms) {
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

    /**
     * Move the guard randomly. Will sometimes stand still.
     *
     * @param rooms All the rooms of the game, in a map.
     */
    public void moveGuard(Map<Location, Room> rooms) {
        Room nextRoom;
        Direction direction;

        direction = Game.generateRandomDirection();
        // If Direction.NOWHERE is generated, nextRoom will be null.
        nextRoom = rooms.get(this.getRoom().getExit(direction));

        if (nextRoom != null) {
            this.setOldRoom(this.getRoom());
            this.getRoom().removeGuard();
            this.setRoom(nextRoom);
            nextRoom.addGuard(this);
        }
    }

    /**
     * Set the old room of the guard.
     *
     * @param oldRoom new old room.
     */
    public void setOldRoom(Room oldRoom) {
        this.oldRoom = oldRoom;
    }

    /**
     * Get the old room of the guard.
     *
     * @return the old room.
     */
    public Room getOldRoom() {
        return oldRoom;
    }

}

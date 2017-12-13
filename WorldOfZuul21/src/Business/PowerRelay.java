/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.IPowerRelay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolaj
 */
public class PowerRelay implements IPowerRelay {

    private int ID;
    private boolean status; // false when sabotaged
    private int timeBoost;

    /**
     * Creates a new PowerRelay with an id, and how long the time should be boosted
     *
     * @param ID        id
     * @param timeBoost how much to increase the time if interacted with
     */
    private PowerRelay(int ID, int timeBoost) {
        this.ID = ID;
        this.status = true;
        this.timeBoost = timeBoost;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    /**
     * true if turned on, false if turned off.
     *
     * @return the status.
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Get the amount of time this relay can boost the time.
     *
     * @return the amount of time boosted.
     */
    public int getTimeBoost() {
        return this.timeBoost;
    }

    /**
     * sabotage the relay, turning the power off (status to false)
     */
    public void sabotage() {
        this.status = false;
    }

    /**
     * restore the relay, turning the power on (status to true)
     */
    public void restore() {
        this.status = true;
    }


    /**
     * Set the status
     *
     * @param status the status to set (on=true, off=false)
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Set the ID
     *
     * @param ID new id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @param rooms list of possible spawn rooms
     * @return a list of rooms, that has been spawned in
     */
    public static List<Room> spawn(List<Room> rooms) {
        List<Room> rooms_ = new ArrayList<>();
        while (rooms_.size() < 3) {
            int randomIndex = (int) (Math.random() * rooms.size());
            if (!rooms_.contains(rooms.get(randomIndex))) {
                rooms.get(randomIndex).setSpawn(new PowerRelay(rooms_.size(), 2));
                rooms_.add(rooms.get(randomIndex));
            }
        }
        return rooms_;
    }
}

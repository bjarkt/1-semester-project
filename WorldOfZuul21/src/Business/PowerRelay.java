/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.IPowerRelay;
import Acq.IRoom;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikolaj
 */
public class PowerRelay implements IPowerRelay{
    
    private int ID;
    private boolean status; // false when sabotaged
    private int timeBoost;
    
    public PowerRelay(int ID, int timeBoost) {
        this.ID = ID;
        this.status = true;
        this.timeBoost = timeBoost;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public boolean getStatus() {
        return this.status;
    }
    
    public int getTimeBoost() {
        return this.timeBoost;
    }
    
    public void sabotage() {
        this.status = false;
    }
    
    public void restore() {
        this.status = true;
    }
    
    public void setTimeBoost(int timeBoost) {
        this.timeBoost = timeBoost;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static List<IRoom> Spawn(List<IRoom> rooms) {
        List<IRoom> rooms_ = new ArrayList<>();
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

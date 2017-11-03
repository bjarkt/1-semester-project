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
public class PowerRelay {
    
    private final int ID;
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
}

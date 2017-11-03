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
public class Location {
    
    private int x;
    private int y;
    private int xy;
    
   public Location(int x, int y) {
        this.x = x;
        this.y = y;
        xy = Integer.parseInt("" + x + y);
    }
    
   public int getX() {
        // Method for getting X
        return x;
    }
    
   public int getY() {
         // Method for getting Y
        return y;
    }
    
   public int getXY() {
         // Method for getting XY
        return xy;
    }
    @Override
    public String toString() {
        // Method for returning xy as a string
        return xy + "";
    }
    // Not in use 
    @Override
    public int hashCode()
    {
        return (Integer.toString(x) + "," + Integer.toString(y)).hashCode();
    }
}

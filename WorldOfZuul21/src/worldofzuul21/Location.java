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
    
    Location(int x, int y) {
        this.x = x;
        this.y = y;
        xy = Integer.parseInt("" + x + y);
    }
    
    int getX() {
        return x;
    }
    
    int getY() {
        return y;
    }
    
    int getXY() {
        return xy;
    }
    
    @Override
    public int hashCode()
    {
        return (Integer.toString(x) + "," + Integer.toString(y)).hashCode();
    }
}

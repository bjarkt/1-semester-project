
package Business;

import java.util.ArrayList;
import java.util.List;

public class PowerSwitch {

    private boolean isOn;

    /**
     * Create a new PowerSwitch.
     */
    PowerSwitch() {
    }


    /**
     * Set the isOn variable to false
     * Turn off the power
     */
    public void turnPowerOff() {
        isOn = false;

    }

    /**
     * Set the isOn variable to true
     * Turn on the power.
     */
    public void turnPowerOn() {
        isOn = true;

    }

    /**
     * Is the power switch turned on?
     *
     * @return true if the power switch is on, false if off.
     */
    public boolean getIsOn() {
        return isOn;
    }

    /**
     * Spawn a PowerSwitch in one of the rooms in the parameter room
     *
     * @param rooms list of possible rooms to spawn in
     * @return a list of rooms that have been spawned in
     */
    public static List<Room> Spawn(List<Room> rooms) {
        int number = (int) (Math.random() * 3);
        List<Room> rooms_ = new ArrayList<>();

        rooms.get(number).setSpawn(new PowerSwitch());
        rooms.get(number).getPowerSwitch().turnPowerOn();
        rooms_.add(rooms.get(number));

        return rooms_;
    }


}
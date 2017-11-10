
package worldofzuul21;

import java.util.ArrayList;
import java.util.List;

public class PowerSwitch implements Spawnable {

    private boolean isOn;
/* No arg constructer */
    PowerSwitch() {
    }
/* Method to set IsOn to false */
    public void turnPowerOff() {
        isOn = false;

    }
/* Method to set IsOn to true */
    public void turnPowerOn() {
        isOn = true;

    }
/* Method to return the state of IsON */
    public boolean getIsOn() {
        return isOn;
    }

    @Override
    public List<Room> Spawn(List<Room> rooms) {
        int number = (int) (Math.random() * 3);
        List<Room> rooms_ = new ArrayList<>();

        rooms.get(number).setSpawn(new PowerSwitch());
        rooms.get(number).getPowerSwitch().turnPowerOn();
        rooms_.add(rooms.get(number));

        return rooms_;
    }


}
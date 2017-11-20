
package Business;

import Acq.IPowerSwitch;
import Acq.IRoom;

import java.util.ArrayList;
import java.util.List;

public class PowerSwitch implements IPowerSwitch {

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

    public static List<IRoom> Spawn(List<IRoom> rooms) {
        int number = (int) (Math.random() * 3);
        List<IRoom> rooms_ = new ArrayList<>();

        rooms.get(number).setSpawn(new PowerSwitch());
        rooms.get(number).getPowerSwitch().turnPowerOn();
        rooms_.add(rooms.get(number));

        return rooms_;
    }


}
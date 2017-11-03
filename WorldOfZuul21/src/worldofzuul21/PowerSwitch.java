
package worldofzuul21;

public class PowerSwitch {

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


}
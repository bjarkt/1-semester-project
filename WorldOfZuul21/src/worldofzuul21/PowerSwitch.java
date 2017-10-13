
package worldofzuul21;

public class PowerSwitch {

    private boolean isOn;

    PowerSwitch() {
    }

    void turnPowerOff() {
        isOn = false;

    }

    void turnPowerOn() {
        isOn = true;

    }

    public boolean getIsOn() {
        return isOn;
    }


}

/* Så hvis man tilføjer en attribut til Room: Powerswtich powerswitch = new Powerswitch(); 
,så kan man lave en switch i game metoden.
int number = (int)(Math.random() * 3); 

switch (number){
    case 1;
       room02.powerswtich.turnPowerOn
    case 2;
       room04.powerswtich.turnPowerOn 
    case 3;
       room11.powerswtich.turnPowerOn
}

Så skal vi bare have lavet en måde hvorpå vi kan interact. Det gøres nok ved at 
tjekke statement. if(currentroom.powerswitch.status) {
    Turnoflights(); (Det er så metoden fra interact)
} else {
    break;
}



    
}
*/
package Presentation.Drawables;

import Acq.ILocation;
import javafx.scene.paint.Color;

public class PowerSwitch extends VisibleDrawable {

    public PowerSwitch(ILocation location) {
        super(location);
        this.color = Color.YELLOW;
        this.rectangle.setFill(this.color);
    }

}

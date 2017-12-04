package Presentation.Drawables;

import Acq.ILocation;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PowerRelay extends VisibleDrawable {

    public PowerRelay(ILocation location) {
        super(location);
        this.color = Color.BROWN;
        this.rectangle.setFill(this.color);
    }
}

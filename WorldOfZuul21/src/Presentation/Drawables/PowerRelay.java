package Presentation.Drawables;

import Acq.ILocation;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PowerRelay extends VisibleDrawable {
    private ILocation location;

    public PowerRelay(ILocation location) {
        this.color = Color.BROWN;
        this.rectangle.setFill(this.color);
        this.location = location;
    }

    public ILocation getLocation() {
        return location;
    }
}

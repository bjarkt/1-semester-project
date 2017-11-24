package Presentation.Drawables;

import Presentation.Drawables.PowerSwitch;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerRelay extends VisibleDrawable {
    private Point2D location;
    public PowerRelay(Point2D location) {
        this.color = Color.BROWN;
        this.rectangle.setFill(this.color);
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }
}

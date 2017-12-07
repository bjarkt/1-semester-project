package Presentation.Drawables;

import Acq.ILocation;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PowerRelay extends VisibleDrawable {
    private final int ID;
    public PowerRelay(ILocation location, int id) {
        super(location);
        this.color = Color.BROWN;
        this.rectangle.setFill(this.color);
        this.ID = id;
    }

    public int getID() {
        return ID;
    }
}

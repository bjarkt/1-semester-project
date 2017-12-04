package Presentation.Drawables;

import Acq.ILocation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Guard extends Drawable {

    public Guard(ILocation location) {
        super(location);
        this.color = Color.RED;
        super.rectangle.setFill(this.color);
    }
}

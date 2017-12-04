package Presentation.Drawables;

import Acq.ILocation;
import javafx.scene.paint.Color;

public class Item extends VisibleDrawable {
    public Item(ILocation location) {
        super(location);
        this.color = Color.BLUE;
        this.rectangle.setFill(this.color);
    }
}

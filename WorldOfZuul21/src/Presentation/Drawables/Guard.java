package Presentation.Drawables;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Guard extends Drawable{

    public Guard(Color color) {
        this.rectangle = new Rectangle(0, 25, 10, 10);
        this.color = color;
        this.rectangle.setFill(this.color);
    }
}

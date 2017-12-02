package Presentation.Drawables;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Drawable {
    public Player() {
        this.rectangle = new Rectangle(0, 25, 10, 10);
        this.color = Color.GREEN;
        this.rectangle.setFill(this.color);
    }
}

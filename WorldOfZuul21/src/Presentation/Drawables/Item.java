package Presentation.Drawables;

import javafx.scene.paint.Color;

public class Item extends VisibleDrawable{
    public Item() {
        this.color = Color.BLUE;
        this.rectangle.setFill(this.color);
    }
}

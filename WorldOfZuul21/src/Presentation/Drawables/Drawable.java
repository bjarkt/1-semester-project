package Presentation.Drawables;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Drawable {
    Rectangle rectangle;
    Color color;

    public void draw(Pane paneToDrawOn) {
        rectangle = new Rectangle(0, 25, 10, 10);
        int rectangleBufferWidth = 15;
        rectangle.setFill(color);
        this.rectangle.setX(0);
        for (Node node : paneToDrawOn.getChildren()) {
            if (node instanceof Rectangle) {
                this.rectangle.setX(rectangle.getX()+ rectangleBufferWidth);
            }
        }
        paneToDrawOn.getChildren().add(rectangle);
    }

}

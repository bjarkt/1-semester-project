package Presentation.Drawables;

import Acq.ILocation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The parent class of all drawables
 */
public abstract class Drawable {
    /**
     * The rectangle to draw
     */
    Rectangle rectangle;

    /**
     * The color of the rectangle
     */
    Color color;

    /**
     * The location on the minimap
     */
    private ILocation location;

    public Drawable(ILocation location) {
        rectangle = new Rectangle(0, 25, 10, 10);
        color = Color.BLACK;
        this.location = location;
    }

    /**
     * Draw the rectangle on a {@link Pane}. Makes sure not to draw two rectangles on top of eachother.
     *
     * @param paneToDrawOn a pane
     */
    public void draw(Pane paneToDrawOn) {
        int rectangleBufferWidth = 15;
        rectangle.setFill(color);

        // Increase the x value, to make sure two rectangles dont get drawn on top of eachother
        this.rectangle.setX(0);
        for (Node node : paneToDrawOn.getChildren()) {
            if (node instanceof Rectangle) {
                this.rectangle.setX(rectangle.getX() + rectangleBufferWidth);
            }
        }
        paneToDrawOn.getChildren().add(rectangle);
    }

    public ILocation getLocation() {
        return location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }
}

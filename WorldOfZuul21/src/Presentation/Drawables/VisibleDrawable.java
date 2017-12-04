package Presentation.Drawables;

import Acq.ILocation;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * VisibleDrawable extends Drawable and be visible or not visisble
 */
public abstract class VisibleDrawable extends Drawable {
    private boolean seen;

    public VisibleDrawable(ILocation location) {
        super(location);
        this.rectangle = new Rectangle(20, 5, 10, 10);
        seen = false;
    }


    public boolean hasSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }


    /**
     * Only draw player has seen object. This method does not check for other rectangles, like the parent class does.
     *
     * @param paneToDrawOn a pane
     */
    @Override
    public void draw(Pane paneToDrawOn) {
        if (this.hasSeen()) {
            rectangle.setFill(this.color);
            paneToDrawOn.getChildren().add(rectangle);
        }
    }

}

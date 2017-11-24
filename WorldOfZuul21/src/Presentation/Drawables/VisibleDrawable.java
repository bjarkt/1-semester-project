package Presentation.Drawables;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class VisibleDrawable extends Drawable {
    private boolean seen;

    public VisibleDrawable() {
        this.rectangle = new Rectangle(20, 5, 10, 10);
        seen = false;
    }


    public boolean hasSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }


    @Override
    public void draw(Pane paneToDrawOn) {
        if (this.hasSeen()) {
            rectangle.setFill(this.color);
            paneToDrawOn.getChildren().add(rectangle);
        }
    }

}

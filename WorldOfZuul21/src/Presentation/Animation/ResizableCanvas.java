package Presentation.Animation;

import Presentation.PrimaryWindowController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
// taget fra https://stackoverflow.com/questions/24533556/how-to-make-canvas-resizable-in-javafx
public class ResizableCanvas extends Canvas {
    private PrimaryWindowController controller;
    public ResizableCanvas(PrimaryWindowController controller) {
        this.controller = controller;

        // When the window is resized, call the draw method
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    /**
     * redraws the scene, and positions the sprite.
     */
    private void draw() {
        double width = getWidth();
        double height = getHeight();
        controller.updateSpritePosition();
        controller.positionExitsAndDisableButtons();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
    }
    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}

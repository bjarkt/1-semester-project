package Presentation.Animation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
// taget fra https://stackoverflow.com/questions/24533556/how-to-make-canvas-resizable-in-javafx
public class ResizableCanvas extends Canvas {
    public ResizableCanvas() {
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();

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

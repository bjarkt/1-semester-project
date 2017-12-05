package Presentation.Animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.util.Duration;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// taget fra https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development
public class Sprite {
    /**
     * The current image of the sprite.
     */
    private Image image;
    /**
     * When using a spritesheet, images contain all the possible images for the sprite.
     */
    private Image[] images;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private Timeline northTimeline;
    private Timeline southTimeline;
    private Timeline eastTimeline;
    private Timeline westTimeline;

    /**
     * Determines which row of the sprite sheet, that contains the animation, that shows the character walking north.
     */
    private int northRow;
    /**
     * Determines which row of the sprite sheet, that contains the animation, that shows the character walking south.
     */
    private int southRow;
    /**
     * Determines which row of the sprite sheet, that contains the animation, that shows the character walking east.
     */
    private int eastRow;
    /**
     * Determines which row of the sprite sheet, that contains the animation, that shows the character walking west.
     */
    private int westRow;
    private List<Timeline> timelines;

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        timelines = new ArrayList<>();
    }

    /**
     * Set the image for the sprite, from a {@link Image}.
     *
     * @param i image
     */
    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    /**
     * Set the image for the sprite, from a {@link String}.
     *
     * @param filename the path to the image
     */
    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    /**
     * Set the image for the sprite, from a spritesheet.
     *
     * @param filename              filename of the spritesheet
     * @param amountOfImagesInSheet how many images are in the sprite sheet
     * @param rows                  how many rows are in the sprite sheet
     * @param cols                  how many columns are in the sprite sheet
     * @param spriteWidth           width of the individual sprite
     * @param spriteHeight          height of the individual sprite
     * @param northRow              the row in which the sprite walks north
     * @param southRow              the row in which the sprite walks south
     * @param eastRow               the row in which the sprite walks east
     * @param westRow               the row in which the sprite walks west
     */
    public void setImage(String filename, int amountOfImagesInSheet, int rows, int cols, int spriteWidth, int spriteHeight, int northRow, int southRow, int eastRow, int westRow) {
        images = new Image[amountOfImagesInSheet];
        Image fullImage = new Image(filename);
        int i = 0;
        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < cols; k++) {
                // Crop the image, based on the current row, column, sprite height and sprite width
                Image tmp = crop(fullImage, k, j, spriteWidth, spriteHeight);
                images[i] = tmp;
                i++;
            }
        }

        this.northRow = northRow;
        this.southRow = southRow;
        this.eastRow = eastRow;
        this.westRow = westRow;

        initKeyFrames();

        Collections.addAll(timelines, northTimeline, southTimeline, eastTimeline, westTimeline);

        setImage(images[0]);
    }

    /**
     * Set the position of the sprite.
     *
     * @param x x value.
     * @param y y value.
     */
    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    /**
     * Set the velocity of the sprite.
     *
     * @param x x value.
     * @param y y value.
     */
    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    /**
     * Add to the current velocity of the sprite.
     *
     * @param x x value.
     * @param y y value.
     */
    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    /**
     * Update the sprites position, according to the time that has passed.
     *
     * @param time time since last update.
     */
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    /**
     * Draw the sprite to the canvas
     *
     * @param gc GraphicsContext of the canvas to draw on.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    /**
     * Initialize all the timelines and key frames, one for each direction.
     */
    private void initKeyFrames() {
        initNorthKeyframes();
        initSouthKeyframes();
        initEastKeyframes();
        initWestKeyframes();
    }

    private void initNorthKeyframes() {
        northTimeline = new Timeline();
        Collection<KeyFrame> frames = northTimeline.getKeyFrames();
        // How long should each frame be displayed.
        Duration frameGap = Duration.millis(256);
        Duration frameTime = Duration.ZERO;
        for (int i = northRow * 4; i < 4 + (northRow * 4); i++) {
            Image img = images[i];
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> setImage(img)));
        }
    }

    private void initSouthKeyframes() {
        southTimeline = new Timeline();
        Collection<KeyFrame> frames = southTimeline.getKeyFrames();
        Duration frameGap = Duration.millis(256);
        Duration frameTime = Duration.ZERO;
        for (int i = southRow * 4; i < 4 + (southRow * 4); i++) {
            Image img = images[i];
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> setImage(img)));
        }
    }

    private void initEastKeyframes() {
        eastTimeline = new Timeline();
        Collection<KeyFrame> frames = eastTimeline.getKeyFrames();
        Duration frameGap = Duration.millis(256);
        Duration frameTime = Duration.ZERO;
        for (int i = eastRow * 4; i < 4 + (eastRow * 4); i++) {
            Image img = images[i];
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> setImage(img)));
        }
    }

    private void initWestKeyframes() {
        westTimeline = new Timeline();
        Collection<KeyFrame> frames = westTimeline.getKeyFrames();
        Duration frameGap = Duration.millis(256);
        Duration frameTime = Duration.ZERO;
        for (int i = westRow * 4; i < 4 + (westRow * 4); i++) {
            Image img = images[i];
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> setImage(img)));
        }
    }


    /**
     * Get the boundary of the sprite.
     *
     * @return a {@link Rectangle2D} of the sprite.
     */
    private Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    /**
     * Check if the sprite is intersecting with another sprite.
     *
     * @param s another sprite
     * @return true if the sprites are intersecting.
     */
    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    /**
     * Get the x position of the sprite.
     *
     * @return x position of sprite.
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Get the y position of the sprite.
     *
     * @return y position of sprite.
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Get the width of the sprite.
     *
     * @return width of sprite.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of the sprite.
     *
     * @return height of sprite.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Crop the image according to the parameters
     *
     * @param src         source image
     * @param col         which column of the source image
     * @param row         which row of the source image
     * @param imageWidth  width of the individual sprite
     * @param imageHeight height of the individual sprite
     * @return a new cropped image.
     * @author https://stackoverflow.com/a/36073509
     */
    private static Image crop(Image src, int col, int row, int imageWidth, int imageHeight) {
        PixelReader r = src.getPixelReader();
        WritablePixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbInstance();
        int[] pixels = new int[imageWidth * imageHeight];
        r.getPixels(col * imageWidth, row * imageHeight, imageWidth, imageHeight, pixelFormat,
                pixels, 0, imageWidth);
        WritableImage out = new WritableImage(imageWidth, imageHeight);
        PixelWriter w = out.getPixelWriter();
        w.setPixels(0, 0, imageWidth, imageHeight, pixelFormat,
                pixels, 0, imageWidth);
        return out;
    }

    /**
     * Stop all the timelines, except currentTimeline
     *
     * @param currentTimeline the timeline that will not be stopped.
     */
    private void stopTimelines(Timeline currentTimeline) {
        for (Timeline timeline : timelines) {
            if (currentTimeline != timeline)
                timeline.stop();
        }
    }

    /**
     * Returns the timeline, and stops the others
     *
     * @return a timeline
     */
    public Timeline getWestTimeline() {
        stopTimelines(westTimeline);
        return westTimeline;
    }

    /**
     * Returns the timeline, and stops the others
     *
     * @return a timeline
     */
    public Timeline getEastTimeline() {
        stopTimelines(eastTimeline);
        return eastTimeline;
    }

    /**
     * Returns the timeline, and stops the others
     *
     * @return a timeline
     */
    public Timeline getNorthTimeline() {
        stopTimelines(northTimeline);
        return northTimeline;
    }

    /**
     * Returns the timeline, and stops the others
     *
     * @return a timeline
     */
    public Timeline getSouthTimeline() {
        stopTimelines(southTimeline);
        return southTimeline;
    }
}
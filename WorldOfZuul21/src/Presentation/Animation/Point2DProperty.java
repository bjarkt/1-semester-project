package Presentation.Animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Similar to Point2D class, but with properties, so they can bind() to other properties.
 */
public class Point2DProperty {
    private DoubleProperty propertyX;
    private DoubleProperty propertyY;

    /**
     * Constructor with simple data types.
     *
     * @param x x value.
     * @param y y value.
     */
    public Point2DProperty(double x, double y) {
        this.propertyX = new SimpleDoubleProperty(x);
        this.propertyY = new SimpleDoubleProperty(y);
    }

    /**
     * Default constructor, x=0, y=0.
     */
    public Point2DProperty() {
        propertyX = new SimpleDoubleProperty(0);
        propertyY = new SimpleDoubleProperty(0);
    }

    /**
     * Get the actual value of the property.
     *
     * @return x double value.
     */
    public double getPropertyX() {
        return propertyX.get();
    }

    /**
     * Get the DoubleProperty of x.
     *
     * @return DoubleProperty of x.
     */
    public DoubleProperty propertyXProperty() {
        return propertyX;
    }

    /**
     * Get the actual value of the property.
     *
     * @return y double value.
     */
    public double getPropertyY() {
        return propertyY.get();
    }

    /**
     * Get the DoubleProperty of y.
     *
     * @return DoubleProperty of y.
     */
    public DoubleProperty propertyYProperty() {
        return propertyY;
    }

    /**
     * Set the property, from a simple type.
     *
     * @param propertyX double value to set.
     */
    public void setPropertyX(double propertyX) {
        this.propertyX.set(propertyX);
    }

    /**
     * Set the property, from a simple type.
     *
     * @param propertyY double value to set.
     */
    public void setPropertyY(double propertyY) {
        this.propertyY.set(propertyY);
    }
}

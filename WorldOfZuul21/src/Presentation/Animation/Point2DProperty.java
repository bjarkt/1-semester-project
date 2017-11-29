package Presentation.Animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point2DProperty {
    private DoubleProperty propertyX;
    private DoubleProperty propertyY;

    public Point2DProperty(DoubleProperty propertyX, DoubleProperty propertY) {
        this.propertyX = propertyX;
        this.propertyY = propertY;
    }

    public Point2DProperty(double x, double y) {
        this.propertyX = new SimpleDoubleProperty(x);
        this.propertyY = new SimpleDoubleProperty(y);
    }

    public Point2DProperty() {
        propertyX = new SimpleDoubleProperty(0);
        propertyY = new SimpleDoubleProperty(0);
    }

    public double getPropertyX() {
        return propertyX.get();
    }

    public DoubleProperty propertyXProperty() {
        return propertyX;
    }

    public double getPropertyY() {
        return propertyY.get();
    }

    public DoubleProperty propertyYProperty() {
        return propertyY;
    }

    public void setPropertyX(double propertyX) {
        this.propertyX.set(propertyX);
    }

    public void setPropertyY(double propertyY) {
        this.propertyY.set(propertyY);
    }
}

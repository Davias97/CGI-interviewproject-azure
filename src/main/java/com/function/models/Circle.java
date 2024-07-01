package com.function.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Circle {

    private Point centre;
    private Double radius;

    public Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public boolean isPointInside(Point point) {
        return calculateDistance(point) <= radius;
    }

    public double calculateDistance(Point point) {
        double distanceX = point.getX() - centre.getX();
        double distanceY = point.getY() - centre.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}

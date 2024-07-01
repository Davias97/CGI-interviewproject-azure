package com.function.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {

    private Double x;
    private Double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

package com.epam.rd.autotasks.figures;

import java.util.Objects;

class Circle extends Figure{

    private final Point center;
    private final double radius;
    public Circle(Point center, double radius){
        if(center == null || radius <= 0){
            throw new IllegalArgumentException();
        }
        if(area(radius) == 0){
            throw new IllegalArgumentException();
        }
        this.center = center;
        this.radius = radius;
    }

    private double area(double radius) {
        return Math.PI * radius * radius;
    }

    @Override
    public Point centroid() {
        return center;
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;
        return Math.abs(this.centroid().getY() - figure.centroid().getY()) < 0.0000000000001 &&
                Math.abs(this.centroid().getX() - figure.centroid().getX()) < 0.0000000000001 &&
                Math.abs(this.radius - ((Circle)figure).radius) < 0.0000000000001;
    }

}

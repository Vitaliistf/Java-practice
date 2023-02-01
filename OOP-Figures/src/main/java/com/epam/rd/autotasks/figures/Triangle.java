package com.epam.rd.autotasks.figures;

class Triangle extends Figure {

    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(Point a, Point b, Point c) {
        if(a != null && b != null && c != null && area(a,b,c) != 0){
            this.a = a;
            this.b = b;
            this.c = c;
        } else {
            throw new IllegalArgumentException();
        }
    }
    private double area(Point a, Point b, Point c) {
        return Math.abs(a.getX()*(b.getY()-c.getY())+b.getX()*(c.getY()-a.getY())+c.getX()*(a.getY()-b.getY()))/2;
    }


    @Override
    public Point centroid() {
        return new Point((a.getX() + b.getX() + c.getX())/3, (a.getY() + b.getY() + c.getY())/3);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;
        return Math.abs(this.centroid().getY() - figure.centroid().getY()) < 0.0000000000001 &&
                Math.abs(this.centroid().getX() - figure.centroid().getX()) < 0.0000000000001;
    }
}


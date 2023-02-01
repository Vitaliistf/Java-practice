package com.epam.rd.autotasks.figures;

class Quadrilateral extends Figure {

    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;
    public Quadrilateral(Point a, Point b, Point c, Point d){
        if (a == null || b == null || c == null || d == null) {
            throw new IllegalArgumentException();
        }
        if (!isConvex(a, b, c, d) || isDegenerate(a,b,c)) {
            throw new IllegalArgumentException();
        }
        if(area(a, b, c, d) > 0.000000000001) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private double area(Point a, Point b, Point c, Point d) {
        return Math.abs((a.getX()-c.getX())*(b.getY()-d.getY())-((b.getX()-d.getX())*(a.getY()-c.getY())))/2;
    }

    @Override
    public Point centroid() {

        double[][] v = new double[][] {
                {a.getX(), a.getY()},
                {b.getX(), b.getY()},
                {c.getX(), c.getY()},
                {d.getX(), d.getY()}
        };

        double signedArea = 0;
        double resultX = 0;
        double resultY = 0;

        for (int i = 0; i < v.length; i++) {
            double x0 = v[i][0], y0 = v[i][1];
            double x1 = v[(i + 1) % v.length][0], y1 = v[(i + 1) % v.length][1];

            double A = (x0 * y1) - (x1 * y0);
            signedArea += A;

            resultX += (x0 + x1) * A;
            resultY += (y0 + y1) * A;
        }

        signedArea *= 0.5;
        resultX = (resultX) / (6 * signedArea);
        resultY = (resultY) / (6 * signedArea);

        return new Point(resultX,resultY);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (this == figure) return true;
        if (figure == null || getClass() != figure.getClass()) return false;
        return Math.abs(this.centroid().getY() - figure.centroid().getY()) < 0.0000000000001 &&
                Math.abs(this.centroid().getX() - figure.centroid().getX()) < 0.0000000000001;
    }

    private boolean isConvex(Point a, Point b, Point c, Point d) {
        Point[] polygon = new Point[]{a,b,c,d};

        Point p, v, u;

        double result1 = 0;

        for (int i = 0; i < polygon.length; i++) {

            p = polygon[i];
            Point tmp = polygon[(i + 1) % polygon.length];
            v = new Point(tmp.getX() - p.getX(),tmp.getY() - p.getY());
            u = polygon[(i + 2) % polygon.length];

            if (i == 0) {
                result1 = u.getX() * v.getY() - u.getY() * v.getX() + v.getX() * p.getY() - v.getY() * p.getX();
            } else {
                double result2 = u.getX() * v.getY() - u.getY() * v.getX() + v.getX() * p.getY() - v.getY() * p.getX();
                if ( (result2 > 0 && result1 < 0) || (result2 < 0 && result1 > 0) )
                    return false;
            }
        }
        return true;
    }
    private boolean isDegenerate(Point a, Point b, Point c) {
        double n = a.getX() * (b.getY() - c.getY()) +
                b.getX() * (c.getY() - a.getY()) +
                c.getX() * (a.getY() - b.getY());

        return n == 0;
    }
}

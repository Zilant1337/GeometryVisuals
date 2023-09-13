package Geometry;

public class Point2D extends Point {
        public Point2D() { super(2); }

        public Point2D(double[] x) { super(2, x); }

        public static Point2D rot(Point2D p, double phi) {
            return new Point2D(new double[]{
                p.x[0]*Math.cos(phi)-p.x[1]*Math.sin(phi), p.x[0]*Math.sin(phi)+p.x[1]*Math.cos(phi)
            });
        }
        public Point2D rot(double phi)
        {
            Point2D newPoint = rot(this, phi);
            x = newPoint.x;
            dim = newPoint.dim;
            return newPoint;
        }
    }

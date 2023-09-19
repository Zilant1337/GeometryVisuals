package Geometry;
public class Circle implements IShape {
    Point2D p;
    double r;

    public Circle(Point2D p, double r) {
        this.p = p;
        this.r = r;
    }

    public Point2D getP() {
        return p;
    }

    public double getR() {
        return r;
    }

    public void setP(Point2D p) {
        this.p = p;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double square() {
        return Math.PI * r * r;
    }

    public double length() {
        return 2 * Math.PI * r;
    }

    public IShape shift(Point2D a) {
        Point newP = Point.add(a, p);
        p = new Point2D(newP.getX());
        return this;
    }

    public IShape rot(double phi) {
        p = p.rot(phi);
        return this;
    }

    public IShape symAxis(int i) {
        p.symAxis(i);
        return this;
    }

    public boolean cross(IShape i) {
        if (i instanceof Circle) {
            if (Point2D.sub(getP(), ((Circle)i).getP()).abs() <= getR() + ((Circle)i).getR()
                    && Point2D.sub(getP(), ((Circle)i).getP()).abs() > Math.abs(getR() - ((Circle)i).getR()))
                return true;
            else
                return false;
        } else return i.cross(this);
    }

    public String toString() {
        return "Circle: (Center:" + p.toString() + ";Radius:" + r + ")";
    }
}
package Geometry;

public class Polyline extends OpenFigure{
    int n;
    Point2D[] p;
    public Polyline(Point2D[] p) { n = p.length; this.p = p; }
    public int getN() { return n; }
    public Point2D[] getP() { return p;}
    public Point2D getP(int i) { return p[i]; }
    public void setP(Point2D[] p) { this.p = p; n = p.length; }
    public void setP(Point2D p, int i) { this.p[i] = p;}
    @Override public double length() {
        double l = 0;
        Point2D prevp = p[0];
        for (Point2D point : p) { l += new Segment(prevp, point).length(); prevp = point; }
        return l;
    }
    @Override public Polyline shift(Point2D a) { for (Point2D e : p) e.add(a); return this; }
    @Override public Polyline rot(double phi) { for (Point2D e : p) e.rot(phi); return this; }
    @Override public Polyline symAxis(int i) { for (Point2D e : p) e.symAxis(i); return this; }
    @Override public boolean cross(IShape i) {
        Point2D prev = p[0];
        for (Point2D pt : p)
        {
            if (new Segment(prev, pt).cross(i)) return true;
            prev = pt;
        }
        return false;
    }
    @Override public String toString() {
        StringBuilder sb = new StringBuilder(); sb.append("Polyline: Polyline([");
        for (Point2D e : p)
            if (e != p[p.length - 1])
                sb.append(e.toString() + ", ");
            else
                sb.append(e.toString());
        sb.append("])");
        return sb.toString();
    }
}

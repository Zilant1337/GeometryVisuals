package Geometry;

public class NGon implements IShape {

    public int n;
    public Point2D[] p;
    public NGon(Point2D[] p) { this.p = p; n = p.length; }
    public int getN() { return n; }
    public Point2D[] getP() { return p; }
    public void setP(Point2D[] p) { this.p = p; n = p.length; }
    public void setP(Point2D p, int i) { this.p[i] = p;}
    public double square()
    {
        if (n > 3)
        {
            Point2D[] a = java.util.Arrays.copyOfRange(p, 0, n - 1);
            Point2D[] b = new Point2D[] { p[0], p[n - 2], p[n - 1] };
            return new NGon(a).square() + new TGon(b).square();
        }
        else if (n == 3) return new TGon(p).square();
        else return 0;
    }

    public double length()
    {
        double l = 0;
        Point2D prevp = p[0];
        for (Point2D point : p) { l += new Segment(prevp, point).length(); prevp = point; }
        l += new Segment(p[0], p[n - 1]).length();
        return l;
    }

    public IShape shift(Point2D a) { for (int i = 0; i < n; i++) p[i].add(a); return this; }
    public IShape rot(double phi) { for (int i = 0; i < n; i++) p[i].rot(phi); return this; }
    public IShape symAxis(int i) { for (int j = 0; j < n; j++) p[j].symAxis(i); return this; }
    public boolean cross(IShape i)
    {
        Point2D prev = p[0];
        for (Point2D pt : p)
        {
            if (new Segment(prev, pt).cross(i)) return true;
            prev = pt;
        }
        if (new Segment(p[0], p[n - 1]).cross(i)) return true;
        return false;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder(); sb.append("Polygon: NGon([");
        for (Point2D e : p)
        if (e != p[p.length - 1])
            sb.append(e.toString() + ", ");
        else
            sb.append(e.toString());
        sb.append("])");
        return sb.toString();
    }
}

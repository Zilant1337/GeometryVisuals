package Geometry;

public class QGon extends NGon {
    public QGon(Point2D[] p) { super(p); }
    @Override public double square() {
        Point2D[] a = new Point2D[] { p[0], p[1], p[2] };
        Point2D[] b = new Point2D[] { p[2], p[3], p[0] };
        return new TGon(a).square() + new TGon(b).square();
    }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(); sb.append("Quadrilateral: {");
        for (Point2D e : p)
        if (e != p[p.length - 1])
            sb.append(e.toString() + ";");
        else
            sb.append(e.toString());
        sb.append("}");
        return sb.toString();
    }
}

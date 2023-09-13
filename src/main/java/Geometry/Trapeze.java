package Geometry;

public class Trapeze extends QGon {

    public Trapeze(Point2D[] p) { super(p); }
    @Override public double square() { return super.square(); }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(); sb.append("Trapeze: Trapeze(super=NGon([");
        for (Point2D e : p)
            if (e != p[p.length - 1])
                sb.append(e.toString() + ", ");
            else
                sb.append(e.toString());
        sb.append("]))");
        return sb.toString();
    }
}

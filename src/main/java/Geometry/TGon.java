package Geometry;

public class TGon extends NGon{
    public TGon(Point2D[] p) { super(p); }
    @Override public double square()
    {
        double ab = Point2D.sub(p[1], p[0]).abs(),
                bc = Point2D.sub(p[2], p[1]).abs(),
                ca = Point2D.sub(p[0], p[2]).abs(),
                pp = (ab + bc + ca)/2;
        return Math.sqrt(pp * (pp - ab)*(pp - bc)*(pp - ca));
    }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(); sb.append("Triangle: {");
        for (Point2D e : p)
        if (e != p[p.length - 1])
            sb.append(e.toString() + ";");
        else
            sb.append(e.toString());
        sb.append("}");
        return sb.toString();
    }
}

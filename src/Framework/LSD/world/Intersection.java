package Framework.LSD.world;

public class Intersection {


    private point A, B, C, D;


    private double dx1;
    private double dy1;
    private double dx2;
    private double dy2;
    private double dx3;
    private double dy3;
    private double t1;
    private double t2;


    //TODO give it a refresh method to recalculate, so that it can store temporary result
    public Intersection(double v1, double v2, double v3, double v4,
                        double v5, double v6, double v7, double v8) {

        A = new point(v1, v2);
        B = new point(v3, v4);
        C = new point(v5, v6);
        D = new point(v7, v8);

        init();
    }

    public void refreshLight(double v1, double v2, double v3, double v4) {
        A = new point(v1, v2);
        B = new point(v3, v4);

        init();
    }

    public void refreshObject(double v5, double v6, double v7, double v8) {
        C = new point(v5, v6);
        D = new point(v7, v8);

        init();

    }

    public void init() {
        dx1 = B.x - A.x;
        dy1 = B.y - A.y;
        dx2 = D.x - C.x;
        dy2 = D.y - C.y;
        dx3 = C.x - A.x;
        dy3 = C.y - A.y;

        t1 = (dx2 * dy1) - (dx1 * dy2); // for later calculation
        t2 = (dx1 * dy3) - (dx3 * dy1); // for later calculation
    }

    public boolean haveAnIntersectionPoint() {
        return !isParallel() && isInSameArea() && isInLineSegments();
    }


    public boolean isParallel() {
        return calculateVectorProduct(A, B, C, D) == 0;
    }

    public boolean isInSameArea() {

        return !(Math.max(A.y, B.y) < Math.min(C.y, D.y) ||
                Math.max(C.y, D.y) < Math.min(A.y, B.y) ||
                Math.max(A.x, B.x) < Math.min(C.x, D.x) ||
                Math.max(C.x, D.y) < Math.min(A.x, B.y));
    }

    public double calculateVectorProduct(point A, point B, point C, point D) {
        return (B.x - A.x) * (D.y - C.y) - (B.y - A.y) * (D.x - C.x);
    }

    public point calculateIntersectionPoint() {
        double x = C.x + (D.x - C.x) * t2 / t1;
        double y = C.y + (D.y - C.y) * t2 / t1;

        return new point(x, y);
    }

    public boolean isInBetween(double v, double v1, double v2) {
        double deviation = 0.001;
        return (v >= Math.min(v1, v2) - deviation && v <= Math.max(v1, v2) + deviation);
    }

    private boolean isInLineSegment(point intersection, point start, point end) {
        return (isInBetween(intersection.x, start.x, end.x)) && (isInBetween(intersection.y, start.y, end.y));
    }

    public boolean isInLineSegments() {
        return isInLineSegment(calculateIntersectionPoint(), A, B) && isInLineSegment(calculateIntersectionPoint(), C, D);
    }

    public double reflectedDirection() {

        double seita1 = Math.atan2(dy1, dx1);
        double seita2 = Math.atan2(dy2, dx2);

        return 2 * seita2 + seita1;
    }

    class point {
        double x;
        double y;

        point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}


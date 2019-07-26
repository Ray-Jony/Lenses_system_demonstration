package Framework.LSD.world;

import Framework.LSD.world.Light.LightPath;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;

public class Intersection {


    private point A, B;

    private point C, D;

    private point center;
    private double radius;

    public Intersection(){

    }

    public Intersection(LightPath lightPath, FlatMirror mirror) {
        this(lightPath.getStartPointX(), lightPath.getStartPointY(), lightPath.getEndPointX(), lightPath.getEndPointY(),
                mirror.getStartPointX(), mirror.getStartPointY(), mirror.getEndPointX(), mirror.getEndPointY());
    }

    public Intersection(LightPath lightPath, CircleMirror circleMirror) {
        this(lightPath.getStartPointX(), lightPath.getStartPointY(), lightPath.getEndPointX(), lightPath.getEndPointY(),
                circleMirror.getCenterX(), circleMirror.getCenterY(), circleMirror.getRadius());
    }

    private Intersection(double v1, double v2, double v3, double v4,
                         double centerX, double centerY, double radius) {
        A = new point(v1, v2);
        B = new point(v3, v4);
        center = new point(centerX, centerY);
        this.radius = radius;
    }


    private Intersection(double v1, double v2, double v3, double v4,
                         double v5, double v6, double v7, double v8) {
        A = new point(v1, v2);
        B = new point(v3, v4);
        C = new point(v5, v6);
        D = new point(v7, v8);
    }

    public void refreshLight(double v1, double v2, double v3, double v4) {
        A = new point(v1, v2);
        B = new point(v3, v4);
    }

    public void refreshObject(double v5, double v6, double v7, double v8) {
        C = new point(v5, v6);
        D = new point(v7, v8);
    }


    public boolean haveAnIntersectionPoint() {
        return !isParallel() && isInSameArea() && isInLineSegments();
    }


    public boolean isParallel() {
        return calculateVectorProduct(A, B, C, D) == 0;
    }

    public boolean isVertical() {
        return calculateDotProduct(A, B, C, D) == 0;
    }

    public boolean isInSameArea() {
        return !(Math.max(A.y, B.y) < Math.min(C.y, D.y) ||
                Math.max(C.y, D.y) < Math.min(A.y, B.y) ||
                Math.max(A.x, B.x) < Math.min(C.x, D.x) ||
                Math.max(C.x, D.y) < Math.min(A.x, B.y));
    }

    private double calculateVectorProduct(point A, point B, point C, point D) {
        return (B.x - A.x) * (D.y - C.y) - (B.y - A.y) * (D.x - C.x);
    }

    private double calculateDotProduct(point A, point B, point C, point D) {
        return (B.x - A.x) * (D.x - C.x) + (B.y - A.y) * (D.y - C.y);
    }

    public point calculateIntersectionPoint() {
        if (center != null) {
            //Only call when line is in circle
            point t = nearestPointInLine(center, new lineSegment(A.x, A.y, B.x, B.y));
            double l = center.distanceTo(t);
            double s = Math.sqrt(Math.pow(radius, 2) - Math.pow(l, 2));
            double sp = Math.sqrt(Math.pow(getDx1(), 2) + Math.pow(getDy1(), 2));
            double cos = getDx1() / sp;
            double sin = getDy1() / sp;
            double dx = s * cos;
            double dy = s * sin;
            point point1 = new point(t.getX() - dx, t.getY() - dy);
            point point2 = new point(t.getX() + dx, t.getY() + dy);

            return A.distanceTo(point2) > A.distanceTo(point1) ? point1 : point2;

        } else {

            double x = C.x + (D.x - C.x) * getT2() / getT1();
            double y = C.y + (D.y - C.y) * getT2() / getT1();

            return new point(x, y);
        }
    }

    private boolean isInBetween(double v, double v1, double v2) {
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
        double seita1;
        double seita2;

        if (center != null) {
            seita1 = Math.atan2(getDy1(), getDx1());
            seita2 = calculateIntersectionPoint().directionTo(center) + Math.PI / 2;
        } else {

            seita1 = Math.atan2(getDy1(), getDx1());
            seita2 = Math.atan2(getDy2(), getDx2());
        }
        return 2 * seita2 + seita1;
    }

    public point nearestPointInLine(point p, lineSegment line) {
        double a = -1 / line.getSlope();
        double b = p.getY() - a * p.getX();
        return new Intersection(p.getX(), p.getY(), 5000, 5000 * a + b,
                line.startPointX, line.startPointY, line.endPointX, line.endPointY).calculateIntersectionPoint();
    }

    public boolean isInCircle() {
        point point = nearestPointInLine(center, new lineSegment(A.x, A.y, B.x, B.y));
        if (isInLineSegment(point, A, B))
            return radius >= center.distanceTo(point);
        else return false;
    }


    public void setA(point a) {
        A = a;
    }

    public void setB(point b) {
        B = b;
    }

    public void setC(point c) {
        C = c;
    }

    public void setD(point d) {
        D = d;
    }

    public void setCenter(point center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    public double getDx1() {
        return B.x - A.x;
    }

    public double getDy1() {
        return B.y - A.y;
    }

    public double getDx2() {
        return D.x - C.x;
    }

    public double getDy2() {
        return D.y - C.y;
    }

    public double getDx3() {
        return C.x - A.x;
    }

    public double getDy3() {
        return C.y - A.y;
    }

    public double getT1() {
        return (getDx2() * getDy1()) - (getDx1() * getDy2());
    }

    public double getT2() {
        return (getDx1() * getDy3()) - (getDx3() * getDy1());
    }

    public point getA() {
        return A;
    }

    public point getB() {
        return B;
    }

    public point getC() {
        return C;
    }

    public point getD() {
        return D;
    }

    public point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public class point {
        double x;
        double y;

        point(double x, double y) {
            this.x = x;
            this.y = y;
        }


        public double distanceTo(point p) {
            return Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
        }

        public double directionTo(point p) {
            return Math.atan2(this.getY() - p.getY(), p.getX() - this.getX());
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public class lineSegment {

        double startPointX;
        double startPointY;
        double endPointX;
        double endPointY;

        public lineSegment(double startPointX, double startPointY, double endPointX, double endPointY) {
            this.startPointX = startPointX;
            this.startPointY = startPointY;
            this.endPointX = endPointX;
            this.endPointY = endPointY;
        }

        public double getStartPointX() {
            return startPointX;
        }

        public double getStartPointY() {
            return startPointY;
        }

        public double getEndPointX() {
            return endPointX;
        }

        public double getEndPointY() {
            return endPointY;
        }

        public double getSlope() {
            return (endPointY - startPointY) / (endPointX - startPointX);
        }
    }


}


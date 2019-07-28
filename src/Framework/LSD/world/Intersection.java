package Framework.LSD.world;

import Framework.LSD.world.Lens.CircleLensSurface;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Light.LightPath;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;
import org.jetbrains.annotations.NotNull;


public class Intersection {

    public static final int REFLECTION = 1;
    public static final int REFRACTION = 2;
    public static final int REFRACTION_IN = 21;
    public static final int REFRACTION_OUT = 22;
    public static final int TOTAL_INTERNAL_REFLECTION = 3;

    private int intersectionType;
    private int subIntersectionType;

    private point A, B;

    private point C, D;

    private point center;
    private double radius;
    private double height;
    private int LorR_ID;
    private point currentPoint;
    private point anotherPoint; //point that been calculated to be far away from start point

    public Intersection() {
        intersectionType = 0;
    }

    public Intersection(LightPath lightPath, FlatMirror mirror) {
        this(lightPath.getStartPointX(), lightPath.getStartPointY(), lightPath.getEndPointX(), lightPath.getEndPointY(),
                mirror.getStartPointX(), mirror.getStartPointY(), mirror.getEndPointX(), mirror.getEndPointY());
        this.intersectionType = REFLECTION;
    }

    public Intersection(LightPath lightPath, CircleMirror circleMirror) {
        this(lightPath.getStartPointX(), lightPath.getStartPointY(), lightPath.getEndPointX(), lightPath.getEndPointY(),
                circleMirror.getCenterX(), circleMirror.getCenterY(), circleMirror.getRadius());
        this.intersectionType = REFLECTION;
    }

    public Intersection(int LorR_ID, LightPath lightPath, CircleLensSurface circleLensSurface, double height) {
        this(lightPath.getStartPointX(), lightPath.getStartPointY(), lightPath.getEndPointX(), lightPath.getEndPointY(),
                circleLensSurface.getCenterX(), circleLensSurface.getCenterY(), circleLensSurface.getRadius());
        this.intersectionType = REFRACTION;
        this.height = height;
        this.LorR_ID = LorR_ID;
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

            double d1 = A.distanceTo(point1);
            double d2 = A.distanceTo(point2);
            double d3 = point1.distanceTo(point2);

            if (d1 < d3 && d2 < d3) {
                if (isInLineSegment(point1, new point(A.x, A.y), new point(B.x, B.y))) {
                    return point1;
                } else return point2;
            } else {
                return d1 < d2 ? point1 : point2;
            }


//
//            if (A.distanceTo(point2) > A.distanceTo(point1)) {
//                anotherPoint = point2;
//                currentPoint = point1;
//                return point1;
//            } else {
//                currentPoint = point2;
//                anotherPoint = point1;
//                return point2;
//            }
//            return A.distanceTo(point2) > A.distanceTo(point1) ? point1 : point2;

        } else {

            double x = C.x + (D.x - C.x) * getT2() / getT1();
            double y = C.y + (D.y - C.y) * getT2() / getT1();
            point point = new point(x, y);
            currentPoint = point;
            return point;
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
        //L is the vector of the light
        //N is the normal vector of mirror
        //R is the vector of the reflected light
        // R - L = 2 * (N dot (-L)) * N
        vector2D N;
        if (center != null) {
            point point = calculateIntersectionPoint();
            N = new vector2D(point.getX() - center.getX(), point.getY() - center.getY()).normalize();
        } else {
            N = getNormalVectorL(C, D).normalize();
        }
        vector2D L = new vector2D(getDx1(), getDy1()).normalize();
        vector2D R = L.subtract(N.multiply(2 * N.dotProduct(L))).normalize();
        return R.getRadian();
    }

    public double refractionDirection(double n) {
        //L is the vector of the light
        //N is the normal vector of the lens surface
        //T is the vector of the refracted light
        //cos01 is the radian between L and N
        //cos02 is the radian between T and N

        vector2D N;
        if (center != null) {
            point point = calculateIntersectionPoint();
            N = new vector2D(point.getX() - center.getX(), point.getY() - center.getY()).normalize();
        } else {
            N = getNormalVectorL(C, D).normalize();
        }
        if (subIntersectionType == REFRACTION_OUT) {
            N.multiply(-1);
        }
        vector2D L = new vector2D(getDx1(), getDy1()).normalize();
        double cos01 = -L.dotProduct(N);
        double cos02 = Math.sqrt(1 - (1 / (n * n)) * (1 - cos01 * cos01));

        vector2D T = L.divide(n).add(N.multiply((cos01 / n) - cos02)).normalize();

        return T.getRadian();
    }


    public point nearestPointInLine(point p, lineSegment line) {
        if (line.getSlope() == 0) {
            return new point(p.getX(), line.startPointY);
        } else if (line.getSlope() > 100000) {
            return new point(line.startPointX, p.getY());
        } else {
            double a = -1 / line.getSlope();
            double b = p.getY() - a * p.getX();
            return new Intersection(p.getX(), p.getY(), 5000, 5000 * a + b,
                    line.startPointX, line.startPointY, line.endPointX, line.endPointY).calculateIntersectionPoint();
        }
    }

    public boolean isInCircle() {
        point point = nearestPointInLine(center, new lineSegment(A.x, A.y, B.x, B.y));
        return radius >= center.distanceTo(point);
    }

    public boolean isIntersectsCircle() {
        if (!isInCircle()) {
            return false;
        }
        point point = calculateIntersectionPoint();
        return isInLineSegment(point, A, B);
    }

    public boolean isInArc() {
        if (!isIntersectsCircle()) {
            return false;
        }
        point point = calculateIntersectionPoint();
        if (height / 2 < Math.abs(point.getY() - center.getY()))
            return false;
        if (LorR_ID == CircleLensSurface.LEFT) {
            if (point.getX() > center.getX())
                return false;
        }
        if (LorR_ID == CircleLensSurface.RIGHT) {
            if (point.getX() < center.getX())
                return false;
        }
        return true;
    }

    public vector2D getNormalVectorL(point A, point B) {
        if (B.x - A.x == 0 && B.y - A.y == 0) {
            System.out.println("This two point is the same point, does not have an normal vector");
            return new vector2D();
        } else if (B.x - A.x == 0) {
            if (B.y - A.y >= 0) {
                return new vector2D(-1, 0);
            } else return new vector2D(1, 0);
        } else if (B.y - A.y == 0) {
            if (B.x - A.x >= 0) {
                return new vector2D(0, 1);
            } else return new vector2D(0, -1);
        } else return new vector2D(-(B.y - A.y), B.x - A.x).normalize();
    }

    public vector2D getNormalVectorR(point A, point B) throws Exception {
        if (B.x - A.x == 0 && B.y - A.y == 0) {
            throw new Exception("This two point is the same point, does not have an normal vector");
        } else if (B.x - A.x == 0) {
            if (B.y - A.y >= 0) {
                return new vector2D(1, 0);
            } else return new vector2D(-1, 0);
        } else if (B.y - A.y == 0) {
            if (B.x - A.x >= 0) {
                return new vector2D(0, -1);
            } else return new vector2D(0, 1);
        } else return new vector2D(B.y - A.y, -(B.x - A.x)).normalize();
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

    public int getIntersectionType() {
        return intersectionType;
    }

    public int getSubIntersectionType() {
        return subIntersectionType;
    }

    public void setSubIntersectionType(int subIntersectionType) {
        this.subIntersectionType = subIntersectionType;
    }

    public point getAnotherPoint() {
        return anotherPoint;
    }

    public point getCurrentPoint() {
        return currentPoint;
    }

    public class point {
        double x;
        double y;

        point(double x, double y) {
            this.x = x;
            this.y = y;
        }


        public double distanceTo(@NotNull point p) {
            return Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
        }

        public double directionTo(@NotNull point p) {
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

        private double startPointX;
        private double startPointY;
        private double endPointX;
        private double endPointY;

        lineSegment(double startPointX, double startPointY, double endPointX, double endPointY) {
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

        double getSlope() {
            if (endPointX - startPointX < 0.00001)
                return 100001;
            return (endPointY - startPointY) / (endPointX - startPointX);
        }
    }

    class vector2D {
        private double x;
        private double y;

        public vector2D() {
            this.x = 0;
            this.y = 0;
        }

        public vector2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public vector2D clone() {
            return new vector2D(x, y);
        }

        public double getRadian() {
            return Math.atan2(y, x);
        }

        public double getLength() {
            return Math.sqrt(x * x + y * y);
        }

        public boolean isZeroVector() {
            return x == 0 && y == 0;
        }

        public void setLength(double length) {
            double angle = getRadian();
            x = length * Math.cos(angle);
            y = length * Math.sin(angle);
        }

        public vector2D normalize() {
            double len = getLength();
            x = x / len;
            y = y / len;
            return this;
        }

        public boolean isNormalized() {
            return getLength() == 1.0;
        }

        public double dotProduct(vector2D vector) {
            return x * vector.x + y * vector.y;
        }

        public double crossProduct(vector2D vector) {
            return x * vector.y - y * vector.x;
        }

        public double radianTo(vector2D vector) {
            // v1 * v2 / |v1| *|v2|= cos<v1,v2>

            vector2D v1, v2;

            v1 = this.clone().normalize();

            v2 = vector.clone().normalize();

            return Math.acos(v1.dotProduct(v2));
        }

        public vector2D add(vector2D vector) {
            x = x + vector.x;
            y = y + vector.y;
            return this;
        }

        public vector2D subtract(vector2D vector) {
            x = x - vector.x;
            y = y - vector.y;
            return this;
        }

        public vector2D multiply(double value) {
            x = x * value;
            y = y * value;
            return this;
        }

        public vector2D divide(double value) {
            x = x / value;
            y = y / value;
            return this;
        }


        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }
    }


}


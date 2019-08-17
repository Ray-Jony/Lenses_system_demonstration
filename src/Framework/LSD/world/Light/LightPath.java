package Framework.LSD.world.Light;

import Framework.LSD.world.Intersection;
import Framework.LSD.world.Lens.CircleLensSurface;
import Framework.LSD.world.Lens.ConcaveLens;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Lens.Lens;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;

import static Framework.Framework.*;

public class LightPath {

    Light light;

    private Line lightDraw;

    private boolean detected;

//    private boolean inMedium;

    private Intersection.point intersectionPoint;

    private int lightPathID = 0;

    public static final double RED_LIGHT_WAVE_LENGTH = 656.3; //nm
    public static final double BLUE_LIGHT_WAVE_LENGTH = 486.1; //nm
    public static final double GREEN_LIGHT_WAVE_LENGTH = 589.3; //nm
    private static final double MAXIMUM_LENGTH = 2000;

    private double startPointX;
    private double startPointY;

    private double len;

    private double direction;

    private LightInfo lightInfo;

    public LightPath(double startPointX, double startPointY, double direction, LightInfo lightInfo) {
        this(startPointX, startPointY, MAXIMUM_LENGTH, direction, lightInfo);
    }

    LightPath(double startPointX, double startPointY, double len, double direction, LightInfo lightInfo) {
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.len = len;
        this.direction = direction;
        this.lightInfo = lightInfo;
        this.detected = false;
    }


    public double getStartPointX() {
        return startPointX;
    }

    public double getStartPointY() {
        return startPointY;
    }

    public double getLen() {
        return len;
    }


    public double getDirection() {
        return direction;
    }

    public double getEndPointX() {
        return startPointX + len * Math.cos(direction);
    }

    public double getEndPointY() {
        return startPointY + len * Math.sin(direction);
    }

    public LightInfo getLightInfo() {
        return lightInfo;
    }

    public double getWaveLength() {
        return lightInfo.getWaveLength();
    }

    public int getLightPathID() {
        return lightPathID;
    }

    public void setLightPathID(int lightPathID) {
        this.lightPathID = lightPathID;
    }

    public Intersection.point getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(Intersection.point intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }

    void setLen(double len) {
        this.len = len;
    }

    boolean isDetected() {
        return detected;
    }

    void setDetected(boolean detected) {
        this.detected = detected;
    }

//    public boolean isInMedium() {
//        return inMedium;
//    }
//
//    public void setInMedium(boolean inMedium) {
//        this.inMedium = inMedium;
//    }

    public void setStartPointX(double startPointX) {
        this.startPointX = startPointX;
    }

    public void setStartPointY(double startPointY) {
        this.startPointY = startPointY;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }


    public void initLightDraw() {
        lightDraw = new Line(getStartPointX(), getStartPointY(), getEndPointX(), getEndPointY());
        switch (lightInfo) {
            case RED:
                lightDraw.setStroke(Color.RED);
                break;
            case GREEN:
                lightDraw.setStroke(Color.GREEN);
                break;
            case BLUE:
                lightDraw.setStroke(Color.BLUE);
                break;
            default:
                lightDraw.setStroke(Color.BLACK);
                break;
        }


//        if (waveLength == RED_LIGHT_WAVE_LENGTH) {
//            lightDraw.setStroke(Color.RED);
//        } else if (waveLength == GREEN_LIGHT_WAVE_LENGTH) {
//            lightDraw.setStroke(Color.GREEN);
//        } else if (waveLength == BLUE_LIGHT_WAVE_LENGTH) {
//            lightDraw.setStroke(Color.BLUE);
//        }
    }


    public void drawLightPath(Pane pane) {
        initLightDraw();
        lightDraw.setStrokeWidth(1);
        pane.getChildren().addAll(lightDraw);
    }

    public void highlightLightPath(Pane pane) {
        initLightDraw();
        lightDraw.setStrokeWidth(3);
        pane.getChildren().add(lightDraw);
    }


    public void addNewIntersectionDetection(Mirror mirror) {

        if (mirror.getClass().equals(FlatMirror.class))
            light.FlatMirrorIntersectionList.add(
                    new Intersection(this, (FlatMirror) mirror));
        if (mirror.getClass().equals(CircleMirror.class))
            light.CircleMirrorIntersectionList.add(
                    new Intersection(this, (CircleMirror) mirror));

        //TODO add another type mirror
    }

    public void addNewIntersectionDetection(Lens lens) {

        if (lens.getClass().equals(ConvexLens.class)) {

            light.LeftCircleLensSurfaceIntersectionList.add(
                    new Intersection(
                            CircleLensSurface.LEFT,
                            Lens.CONVEX_LENS,
                            this,
                            lens.getLeftSurface(),
                            lens.getHeight()
                    )
            );

            light.LeftCircleLensSurfaceIntersectionList
                    .get(light.LeftCircleLensSurfaceIntersectionList.size() - 1)
                    .setSubIntersectionType(Intersection.REFRACTION_IN);

            light.RightCircleLensSurfaceIntersectionList.add(
                    new Intersection(
                            CircleLensSurface.RIGHT,
                            Lens.CONVEX_LENS,
                            this,
                            lens.getRightSurface(),
                            lens.getHeight()
                    )
            );

            light.RightCircleLensSurfaceIntersectionList
                    .get(light.RightCircleLensSurfaceIntersectionList.size() - 1)
                    .setSubIntersectionType(Intersection.REFRACTION_OUT);
        }

        if (lens.getClass().equals(ConcaveLens.class)) {

            light.LeftCircleLensSurfaceIntersectionList.add(
                    new Intersection(
                            CircleLensSurface.LEFT,
                            Lens.CONCAVE_LENS,
                            this,
                            lens.getLeftSurface(),
                            lens.getHeight()
                    )
            );

            light.LeftCircleLensSurfaceIntersectionList
                    .get(light.LeftCircleLensSurfaceIntersectionList.size() - 1)
                    .setSubIntersectionType(Intersection.REFRACTION_IN);

            light.RightCircleLensSurfaceIntersectionList.add(
                    new Intersection(
                            CircleLensSurface.RIGHT,
                            Lens.CONCAVE_LENS,
                            this,
                            lens.getRightSurface(),
                            lens.getHeight()
                    )
            );

            light.RightCircleLensSurfaceIntersectionList
                    .get(light.RightCircleLensSurfaceIntersectionList.size() - 1)
                    .setSubIntersectionType(Intersection.REFRACTION_OUT);

        }
        //TODO add another type lens

    }


    public void intersectionDetect() {

        light.LeftCircleLensSurfaceIntersectionList.clear();
        light.RightCircleLensSurfaceIntersectionList.clear();
        light.FlatMirrorIntersectionList.clear();
        light.CircleMirrorIntersectionList.clear();

        ArrayList<Mirror> mirrorsTemp = new ArrayList<>(app.getMirrorMapValues());
        ArrayList<Lens> lensesTemp = new ArrayList<>(app.getLensMapValues());

        for (int i = 0; i < mirrorsTemp.size(); i++) {
            addNewIntersectionDetection(mirrorsTemp.get(i));
        }

        for (int i = 0; i < lensesTemp.size(); i++) {
            addNewIntersectionDetection(lensesTemp.get(i));
        }

        //Clone different list of Intersections
        ArrayList<Intersection> FlatMirrorTemp =
                new ArrayList<>(light.FlatMirrorIntersectionList);
        ArrayList<Intersection> CircleMirrorTemp =
                new ArrayList<>(light.CircleMirrorIntersectionList);
        ArrayList<Intersection> LeftLensSurfaceTemp =
                new ArrayList<>(light.LeftCircleLensSurfaceIntersectionList);
        ArrayList<Intersection> RightLensSurfaceTemp =
                new ArrayList<>(light.RightCircleLensSurfaceIntersectionList);

        HashMap<Integer, Intersection> IntersectionConfirmedTemp = new HashMap<>();

        int i = 0;

        for (Intersection intersection :
                FlatMirrorTemp) {
            if (intersection.isInLineSegments()) {
                IntersectionConfirmedTemp.put(i, intersection);
                i++;
            }
        }

        for (Intersection intersection :
                CircleMirrorTemp) {
            if (intersection.isInCircle()) {
                IntersectionConfirmedTemp.put(i, intersection);
                i++;
            }
        }

        for (Intersection intersection :
                LeftLensSurfaceTemp) {
            if (intersection.isInArc()) {
                IntersectionConfirmedTemp.put(i, intersection);
                i++;
            }
        }

        for (Intersection intersection :
                RightLensSurfaceTemp) {
            if (intersection.isInArc()) {
                IntersectionConfirmedTemp.put(i, intersection);
                i++;
            }
        }

        Intersection.point previousIntersectionPoint;

        int nearestEventIndex = -1;
        double nearestDistance = Double.MAX_VALUE;

        for (int index :
                IntersectionConfirmedTemp.keySet()) {
            Intersection.point currentPoint = IntersectionConfirmedTemp.get(index).calculateIntersectionPoint();
            if (!isInitialLightPath()) {
                previousIntersectionPoint = light.getLightPathMap().get(getLightPathID() - 1).getIntersectionPoint();
                if (isPreviousIntersectionPoint(currentPoint, previousIntersectionPoint)) {
                    continue;
                }
            }
            double distance = IntersectionConfirmedTemp.get(index).getA().distanceTo(currentPoint);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEventIndex = index;
            }
        }

        if (nearestEventIndex != -1) {
            Intersection intersection = IntersectionConfirmedTemp.get(nearestEventIndex);
            this.intersectionPoint = intersection.calculateIntersectionPoint();
            if (intersection.getIntersectionType() == Intersection.REFLECTION) {
                reflectionDetected(intersection);
            }
            if (intersection.getIntersectionType() == Intersection.REFRACTION) {
                if (intersection.getSubIntersectionType() == Intersection.REFRACTION_IN) {
                    refractionInDetected(intersection);
                } else if (intersection.getSubIntersectionType() == Intersection.REFRACTION_OUT) {
                    refractionOutDetected(intersection);
                }
            }
        }
    }

    public boolean isPreviousIntersectionPoint(Intersection.point currentPoint, Intersection.point previousPoint) {
        double deviation = 0.1;//Precision Factor
        double distance = currentPoint.distanceTo(previousPoint);
        return distance < deviation;
    }

    public boolean isInitialLightPath() {
        return getLightPathID() == 0;
    }

    public void reflectionDetected(Intersection intersection) {

        if (!intersection.isOpacity()) {
            setLen(reCalculateLength());
            intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
            light.intersectionListener.intersectionDetected(
                    MAXIMUM_LENGTH, intersection.reflectedDirection(), intersection.getIntersectionType());
        } else setLen(reCalculateLength());
    }

    public void refractionInDetected(Intersection intersection) {
        setLen(reCalculateLength());
        intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
        light.intersectionListener.intersectionDetected(
                MAXIMUM_LENGTH, intersection.refractionDirection(getN(intersection)), intersection.getIntersectionType());
    }

    public void refractionOutDetected(Intersection intersection) {
        setLen(reCalculateLength());
        intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
        light.intersectionListener.intersectionDetected(
                MAXIMUM_LENGTH, intersection.refractionDirection(1 / getN(intersection)), intersection.getIntersectionType());//TODO temp
    }

    public double reCalculateLength() {

        //an intersection point map
        light.getIntersectionPointMap().put(getLightPathID(), this.intersectionPoint);

        double x = this.intersectionPoint.getX();
        double y = this.intersectionPoint.getY();

        return Math.sqrt(Math.pow(Math.abs(startPointX - x), 2) + Math.pow(Math.abs(startPointY - y), 2));

    }

    public double getN(Intersection intersection) {
        switch (lightInfo) {
            case RED:
                return intersection.getLensMaterial().getnC();
            case GREEN:
                return intersection.getLensMaterial().getnD();
            case BLUE:
                return intersection.getLensMaterial().getnF();
            default:
                return 0;
        }

//        if (waveLength == RED_LIGHT_WAVE_LENGTH)
//            return 1.51549;
//        if (waveLength == GREEN_LIGHT_WAVE_LENGTH)
//            return 1.51810;
//        if (waveLength == BLUE_LIGHT_WAVE_LENGTH)
//            return 1.52428;
//        return 0;
    }
}

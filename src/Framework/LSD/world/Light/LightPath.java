package Framework.LSD.world.Light;

import Framework.LSD.world.Intersection;
import Framework.LSD.world.Lens.CircleLens;
import Framework.LSD.world.Lens.Lens;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import static Framework.LSD.Framework.*;

public class LightPath {

    Light light;

    private boolean detected;

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

    private double waveLength;

    public LightPath(double startPointX, double startPointY, double direction, double waveLength) {
        this(startPointX, startPointY, MAXIMUM_LENGTH, direction, waveLength);
    }

    LightPath(double startPointX, double startPointY, double len, double direction, double waveLength) {
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.len = len;
        this.direction = direction;
        this.waveLength = waveLength;
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

    public double getWaveLength() {
        return waveLength;
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

    public void setStartPointX(double startPointX) {
        this.startPointX = startPointX;
    }

    public void setStartPointY(double startPointY) {
        this.startPointY = startPointY;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setWaveLength(double waveLength) {
        this.waveLength = waveLength;
    }

    public void drawLightPath(Pane pane) {

        Line light = new Line(getStartPointX(), getStartPointY(), getEndPointX(), getEndPointY());
        pane.getChildren().addAll(light);

    }


    public void addNewIntersectionDetection(Mirror mirror) {

        if (mirror.getClass().equals(FlatMirror.class))
            light.FlatMirrorIntersectionList.add(new Intersection(this, (FlatMirror) mirror));
        if (mirror.getClass().equals(CircleMirror.class)) {
            light.CircleMirrorIntersectionList.add(new Intersection(this, (CircleMirror) mirror));
        }
        //TODO add another type mirror
    }

    public void addNewIntersectionDetection(Lens lens) {

        if (lens.getClass().equals(CircleLens.class))
            light.CircleLensIntersectionList.add(new Intersection(this, (CircleLens) lens));
        //TODO add another type lens

    }


    public void intersectionDetect() {

        ArrayList<Mirror> mirrorsTemp = new ArrayList<>(app.getMirrorMapValues());
        ArrayList<Lens> lensesTemp = new ArrayList<>(app.getLensMapValues());

        for (int i = 0; i < mirrorsTemp.size(); i++) {
            addNewIntersectionDetection(mirrorsTemp.get(i));
        }

        for (int i = 0; i < lensesTemp.size(); i++) {
            addNewIntersectionDetection(lensesTemp.get(i));
        }

        //Clone different list of Intersections
        ArrayList<Intersection> FlatMirrorTemp = new ArrayList<>(light.FlatMirrorIntersectionList);
        ArrayList<Intersection> CircleMirrorTemp = new ArrayList<>(light.CircleMirrorIntersectionList);
        ArrayList<Intersection> CircleLensTemp = new ArrayList<>(light.CircleLensIntersectionList);

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
                CircleLensTemp) {
            if (intersection.isInCircle()) {
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
            if (intersection.getIntersectionType() == Intersection.REFLECTION)
                reflectionDetected(intersection);
            if (intersection.getIntersectionType() == Intersection.REFRACTION)
                refractionDetected(intersection);
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
        setLen(reCalculateLength());
        intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
        light.intersectionListener.intersectionDetected(MAXIMUM_LENGTH, intersection.reflectedDirection());
    }

    public void refractionDetected(Intersection intersection) {
        setLen(reCalculateLength());
        intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
        light.intersectionListener.intersectionDetected(MAXIMUM_LENGTH, intersection.refractionDirection(1.51549));//TODO temp
    }

    public double reCalculateLength() {

        //an intersection point map
        light.getIntersectionPointMap().put(getLightPathID(), this.intersectionPoint);

        double x = this.intersectionPoint.getX();
        double y = this.intersectionPoint.getY();

        return Math.sqrt(Math.pow(Math.abs(startPointX - x), 2) + Math.pow(Math.abs(startPointY - y), 2));

    }

}

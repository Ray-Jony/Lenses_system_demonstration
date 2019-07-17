package Framework.LSD.world;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import static Framework.LSD.Framework.*;

public class LightPath {

    Light light;

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
        return startPointY - len * Math.sin(direction);
    }

    public double getWaveLength() {
        return waveLength;
    }

    void setLen(double len) {
        this.len = len;
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

    public void intersectionDetect() {
        //Edge detect
        Intersection left = new Intersection(startPointX, startPointY, getEndPointX(), getEndPointY(),
                0, 0, 0, app.getHeight());
        Intersection right = new Intersection(startPointX, startPointY, getEndPointX(), getEndPointY(),
                app.getWidth(), 0, app.getWidth(), app.getHeight());
        Intersection top = new Intersection(startPointX, startPointY, getEndPointX(), getEndPointY(),
                0, 0, app.getWidth(), 0);
        Intersection bottom = new Intersection(startPointX, startPointY, getEndPointX(), getEndPointY(),
                0, app.getHeight(), app.getWidth(), app.getHeight());

        if (left.isInLineSegments()) {
            edgeDetect(left);
        }
        if (right.isInLineSegments()) {
            edgeDetect(right);
        }
        if (top.isInLineSegments()) {
            edgeDetect(top);
        }
        if (bottom.isInLineSegments()) {
            edgeDetect(bottom);
        }
    }

    public void edgeDetect(Intersection intersection) {
        setLen(reCalculateLength(intersection) - 1);
        intersection.refreshLight(startPointX, startPointY, getEndPointX(), getEndPointY());
        light.intersectionListener.intersectionDetected(MAXIMUM_LENGTH,intersection.reflectedDirection());
    }

    public double reCalculateLength(Intersection intersection) {
        double x = intersection.calculateIntersectionPoint().x;
        double y = intersection.calculateIntersectionPoint().y;

        return Math.sqrt(Math.pow(Math.abs(startPointX - x), 2) + Math.pow(Math.abs(startPointY - y), 2));

    }


}

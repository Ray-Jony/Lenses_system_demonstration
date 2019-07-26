package Framework.LSD.world.Mirror;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class CircleMirror extends Mirror {

    private double centerX;
    private double centerY;
    private double radius;

    public CircleMirror(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public void drawMirror(Pane pane) {
        Circle circle = new Circle(centerX,centerY,radius);
        pane.getChildren().addAll(circle);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

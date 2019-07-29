package Framework.LSD.world.Lens;

import javafx.scene.layout.Pane;

public abstract class Lens {

    public static final int CONVEX_LENS = 1;
    public static final int CONCAVE_LENS = 2;

    CircleLensSurface leftSurface;
    CircleLensSurface rightSurface;

    private double centerX;
    private double centerY;
    private double height;

    private double leftRadius;
    private double rightRadius;

    public Lens(double centerX, double centerY, double height, double leftRadius, double rightRadius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.height = height;
        this.leftRadius = leftRadius;
        this.rightRadius = rightRadius;
    }


    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getHeight() {
        return height;
    }

    public double getLeftRadius() {
        return leftRadius;
    }

    public double getRightRadius() {
        return rightRadius;
    }

    public CircleLensSurface getLeftSurface() {
        return leftSurface;
    }

    public CircleLensSurface getRightSurface() {
        return rightSurface;
    }

    public abstract void drawLens(Pane pane);

}

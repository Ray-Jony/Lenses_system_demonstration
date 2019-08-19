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

    private LensMaterial lensMaterial;

    private double nD; //589.3nm GREEN
    private double nF; //486.1nm BLUE
    private double nC; //656.3nm RED

    private double Vd; //Abbe Number Vd = (nD - 1) / (nF - nC)

    public Lens(double centerX, double centerY, double height, double leftRadius, double rightRadius, LensMaterial lensMaterial) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.height = height;
        this.leftRadius = leftRadius;
        this.rightRadius = rightRadius;
        this.lensMaterial = lensMaterial;
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

    public abstract void highlightLens(Pane pane);

}

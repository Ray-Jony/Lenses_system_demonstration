package Framework.LSD.world.Lens;

import javafx.scene.layout.Pane;

public class ConvexLens extends Lens {

    CircleLensSurface leftSurface;
    CircleLensSurface rightSurface;

    private double height;

    private double leftRadius;
    private double rightRadius;


    /**
     * height/2 should << leftSurfaceRadius and rightRadius
     */
    public ConvexLens(double centerX, double centerY, double leftSurfaceRadius, double rightSurfaceRadius, double height) {

        leftSurface = new CircleLensSurface(
                centerX + Math.sqrt(leftSurfaceRadius * leftSurfaceRadius - (height / 2) * (height / 2)),
                centerY, leftSurfaceRadius);
        rightSurface = new CircleLensSurface(
                centerX - Math.sqrt(rightSurfaceRadius * rightSurfaceRadius - (height / 2) * (height / 2)),
                centerY, rightSurfaceRadius);

        this.height = height;
        this.leftRadius = leftSurfaceRadius;
        this.rightRadius = rightSurfaceRadius;

    }

    public CircleLensSurface getLeftSurface() {
        return leftSurface;
    }

    public CircleLensSurface getRightSurface() {
        return rightSurface;
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

    @Override
    public void drawLens(Pane pane) {
        double leftDegree = Math.toDegrees(Math.asin((height / 2) / leftRadius));
        leftSurface.drawLens(pane, leftDegree + 180, -2 * leftDegree);
        double rightDegree = Math.toDegrees(Math.asin((height / 2) / rightRadius));
        rightSurface.drawLens(pane, rightDegree, -2 * rightDegree);
    }


}

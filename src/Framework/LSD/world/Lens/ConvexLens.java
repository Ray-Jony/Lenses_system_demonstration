package Framework.LSD.world.Lens;

import javafx.scene.layout.Pane;

public class ConvexLens extends Lens {


    /**
     * @param centerX
     * @param centerY
     * @param height      is the height of the lens, height/2 should < leftRadius and rightRadius
     * @param leftRadius
     * @param rightRadius
     */
    public ConvexLens(double centerX, double centerY, double leftRadius, double rightRadius, double height) {
        super(centerX, centerY, height, leftRadius, rightRadius);

        super.leftSurface = new CircleLensSurface(
                centerX + Math.sqrt(leftRadius * leftRadius - (height / 2) * (height / 2)),
                centerY,
                leftRadius
        );

        super.rightSurface = new CircleLensSurface(
                centerX - Math.sqrt(rightRadius * rightRadius - (height / 2) * (height / 2)),
                centerY,
                rightRadius
        );
    }




    @Override
    public void drawLens(Pane pane) {
        double leftDegree = Math.toDegrees(Math.asin((getHeight() / 2) / getLeftRadius()));
        leftSurface.drawLens(pane, leftDegree + 180, -2 * leftDegree);
        double rightDegree = Math.toDegrees(Math.asin((getHeight() / 2) / getRightRadius()));
        rightSurface.drawLens(pane, rightDegree, -2 * rightDegree);
    }


}

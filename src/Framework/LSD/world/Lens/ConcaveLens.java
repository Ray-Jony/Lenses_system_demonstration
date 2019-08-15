package Framework.LSD.world.Lens;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Class Concave is created on 29/07/2019 22:08.
 *
 * @author Ray
 * @version 29/07/2019
 **/

public class ConcaveLens extends Lens {

    private double minWidth;

    /**
     * @param centerX
     * @param centerY
     * @param height      is the height of the lens, height/2 should < leftSurfaceRadius and rightRadius
     * @param leftRadius
     * @param rightRadius
     */
    public ConcaveLens(double centerX, double centerY, double leftRadius, double rightRadius, double minWidth, double height, LensInfo lensInfo) {
        super(centerX, centerY, height, leftRadius, rightRadius, lensInfo);

        this.minWidth = minWidth;

        super.leftSurface = new CircleLensSurface(
                centerX - ((minWidth / 2) + leftRadius),
                centerY,
                leftRadius,
                lensInfo
        );

        super.rightSurface = new CircleLensSurface(
                centerX + ((minWidth / 2) + rightRadius),
                centerY,
                rightRadius,
                lensInfo
        );
    }

    public double getMinWidth() {
        return minWidth;
    }


    @Override
    public void drawLens(Pane pane) {
        double leftDegree = Math.toDegrees(Math.asin((getHeight() / 2) / getLeftRadius()));
        leftSurface.drawLens(pane, leftDegree, -2 * leftDegree);
        double rightDegree = Math.toDegrees(Math.asin((getHeight() / 2) / getRightRadius()));
        rightSurface.drawLens(pane, rightDegree + 180, -2 * rightDegree);
        Line upLine = new Line(
                getCenterX() - (getMinWidth() / 2) - (getLeftRadius() -
                        Math.sqrt(getLeftRadius() * getLeftRadius() - (getHeight() / 2) * (getHeight() / 2))),
                getCenterY() + getHeight() / 2,
                getCenterX() + (getMinWidth() / 2) + (getRightRadius() -
                        Math.sqrt(getRightRadius() * getRightRadius() - (getHeight() / 2) * (getHeight() / 2))),
                getCenterY() + getHeight() / 2
        );
        Line downLine = new Line(
                getCenterX() - (getMinWidth() / 2) - (getLeftRadius() -
                        Math.sqrt(getLeftRadius() * getLeftRadius() - (getHeight() / 2) * (getHeight() / 2))),
                getCenterY() - getHeight() / 2,
                getCenterX() + (getMinWidth() / 2) + (getRightRadius() -
                        Math.sqrt(getRightRadius() * getRightRadius() - (getHeight() / 2) * (getHeight() / 2))),
                getCenterY() - getHeight() / 2
        );
        pane.getChildren().addAll(upLine, downLine);
    }
}

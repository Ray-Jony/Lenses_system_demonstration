package Framework.LSD.world.Lens;

import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

/**
 * Class CircleLensSurface defined the Surface information for the lens
 */

public class CircleLensSurface {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private double centerX;
    private double centerY;
    private double radius;

    private LensMaterial lensMaterial;

    //refractive indices of the material at the wavelengths of the Fraunhofer
    // D-, F- and C- spectral lines
//    private double nD; //589.3nm GREEN
//    private double nF; //486.1nm BLUE
//    private double nC; //656.3nm RED
//
//    private double Vd; //Abbe Number Vd = (nD - 1) / (nF - nC)

    //Traditionally the lens with nD>1.60，VD>50 and nD<1.60，VD> will be define with K indicator，all other lens
    // defined with F indicator
    //K lens normally as Convex lens, F lens normally as Concave lens

    public CircleLensSurface(double centerX, double centerY, double radius, LensMaterial lensMaterial) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        this.lensMaterial = lensMaterial;
//        temporary set to H-K10 glass
//        this.nD = 1.51810;
//        this.nF = 1.529;
//        this.nC = 1.51549;
//
//        this.Vd = 58.95;
    }

    public void drawLens(Pane pane, double startDegree, double duration, boolean isHighLighted) {

        Arc arc = new Arc(centerX, centerY, radius, radius, startDegree, duration);

        arc.setStroke(Color.BLACK);
        arc.setStrokeWidth(2);
        arc.setFill(Color.TRANSPARENT);
        if (isHighLighted) {
            arc.setStroke(Color.BLUE);
        }

        pane.getChildren().addAll(arc);
//        Circle circle = new Circle(centerX, centerY, radius);
//        circle.setFill(Color.TRANSPARENT);
//        circle.setStroke(Color.BLACK);
//        pane.getChildren().addAll(circle);
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

    public LensMaterial getLensMaterial() {
        return lensMaterial;
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

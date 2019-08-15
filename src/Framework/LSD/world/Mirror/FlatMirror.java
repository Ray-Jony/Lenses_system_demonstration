package Framework.LSD.world.Mirror;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.swing.text.rtf.RTFEditorKit;

public class FlatMirror extends Mirror {

    private double startPointX;
    private double startPointY;

    private double endPointX;
    private double endPointY;

    private boolean isOpacity;


    public FlatMirror(double startPointX, double startPointY, double endPointX, double endPointY, boolean isOpacity) {
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.endPointX = endPointX;
        this.endPointY = endPointY;
        this.isOpacity = isOpacity;
    }

    @Override
    public void drawMirror(Pane pane) {
        Line mirror = new Line(startPointX, startPointY, getEndPointX(), getEndPointY());
        if (isOpacity)
            mirror.setStrokeWidth(0);
        else mirror.setStrokeWidth(3);
        pane.getChildren().addAll(mirror);
    }


    public double getStartPointX() {
        return startPointX;
    }

    public double getStartPointY() {
        return startPointY;
    }

    public double getEndPointX() {
        return endPointX;
    }

    public double getEndPointY() {
        return endPointY;
    }

    public boolean isOpacity() {
        return isOpacity;
    }
}

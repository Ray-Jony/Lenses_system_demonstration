package test.view;

import Framework.LSD.app.View;
import Framework.LSD.world.Lens.ConcaveLens;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Light.LightPath;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import static Framework.Framework.app;

public class SphericalAberrationDemo extends View {

    private Pane demonstratePane;

    private Button homeBtn;

    private ScrollBar moveLens1Sc;
    private ScrollBar moveLens2Sc;
    private ScrollBar moveLens3Sc;

    private Line standardLine;

    private double position1X = 500;
    private double position2X = 300;
    private double position3X = 400;

    @Override
    public void onLaunch() {
        demonstratePane = new AnchorPane();
        demonstratePane.setLayoutX(10);
        demonstratePane.setLayoutY(50);

        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setLayoutX(10);
        homeBtn.setLayoutY(10);
        homeBtn.setOnAction(e ->
                app.gotoView("Home")
        );

        moveLens1Sc = new ScrollBar();
        moveLens1Sc.setLayoutX(50);
        moveLens1Sc.setLayoutY(50);
        moveLens1Sc.setMinSize(400, 20);
        moveLens1Sc.setMin(-150);
        moveLens1Sc.setMax(600);
        moveLens1Sc.setValue(0);
        moveLens1Sc.setOrientation(Orientation.HORIZONTAL);
        moveLens1Sc.valueProperty().addListener((v, ov, nv) -> {
            position1X = nv.doubleValue() + 300;
        });

        moveLens2Sc = new ScrollBar();
        moveLens2Sc.setLayoutX(550);
        moveLens2Sc.setLayoutY(50);
        moveLens2Sc.setMinSize(400, 20);
        moveLens2Sc.setMin(-350);
        moveLens2Sc.setMax(400);
        moveLens2Sc.setValue(0);
        moveLens2Sc.setOrientation(Orientation.HORIZONTAL);
        moveLens2Sc.valueProperty().addListener((v, ov, nv) -> {
            position2X = nv.doubleValue() + 500;
        });

        moveLens3Sc = new ScrollBar();
        moveLens3Sc.setLayoutX(550);
        moveLens3Sc.setLayoutY(100);
        moveLens3Sc.setMinSize(400, 20);
        moveLens3Sc.setMin(-350);
        moveLens3Sc.setMax(400);
        moveLens3Sc.setValue(0);
        moveLens3Sc.setOrientation(Orientation.HORIZONTAL);
        moveLens3Sc.valueProperty().addListener((v, ov, nv) -> {
            position3X = nv.doubleValue() + 400;
        });


        standardLine = new Line(0, 300, 2000, 300);

        getPane().getChildren().addAll(
                demonstratePane,
                homeBtn,
                moveLens1Sc,
                moveLens2Sc,
                moveLens3Sc
        );


    }

    @Override
    public void onEnter() {
        app.reset();
    }

    @Override
    public void onUpdate(double time) {

        app.unregLight("BlueLight1");
        app.unregLight("BlueLight2");
        app.unregLight("BlueLight3");
        app.unregLight("BlueLight4");
        app.unregLight("BlueLight5");
        app.unregLight("BlueLight6");
        app.unregLight("BlueLight7");
        app.unregLight("BlueLight8");

        app.regLight("BlueLight1", 0, 210, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight2", 0, 220, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight3", 0, 230, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight4", 0, 240, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight5", 0, 360, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight6", 0, 370, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight7", 0, 380, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
        app.regLight("BlueLight8", 0, 390, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);

        app.intersectionDetect();

        app.draw(demonstratePane);
        demonstratePane.getChildren().add(standardLine);
        moveLens2();
        moveLens1();
        moveLens3();

    }


    public void moveLens2() {
        app.unregLens("ConvexLens2");
        app.regLens("ConvexLens2",
                new ConvexLens(position2X, 300, 500, 500, 200));
    }

    public void moveLens1() {
        app.unregLens("ConvexLens1");
        app.regLens("ConvexLens1",
                new ConvexLens(position1X, 300, 500, 500, 200));
    }

    public void moveLens3() {
        app.unregLens("ConcaveLens");
        app.regLens("ConcaveLens",
                new ConcaveLens(position3X, 300, 500, 500, 30, 200));
    }
}

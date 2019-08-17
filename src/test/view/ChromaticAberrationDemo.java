package test.view;

import Framework.LSD.app.View;
import Framework.LSD.world.Lens.ConcaveLens;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Lens.LensMaterial;
import Framework.LSD.world.Light.LightInfo;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import static Framework.Framework.app;

public class ChromaticAberrationDemo extends View {
    private Pane demonstratePane;

    private Button homeBtn;
    private ScrollBar changeLensRadiusSc;

    private Line standardLine;

    private double lensRadius = 300;

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

        changeLensRadiusSc = new ScrollBar();
        changeLensRadiusSc.setLayoutX(50);
        changeLensRadiusSc.setLayoutY(50);
        changeLensRadiusSc.setMinSize(400, 20);
        changeLensRadiusSc.setMin(-130);
        changeLensRadiusSc.setMax(600);
        changeLensRadiusSc.setValue(0);
        changeLensRadiusSc.setOrientation(Orientation.HORIZONTAL);
        changeLensRadiusSc.valueProperty().addListener((v, ov, nv) -> {
            lensRadius = nv.doubleValue() + 300;
        });

        standardLine = new Line(0, 300, 2000, 300);

        getPane().getChildren().addAll(
                demonstratePane,
                homeBtn,
                changeLensRadiusSc
        );


    }

    @Override
    public void onEnter() {
        app.reset();
//        app.regLens("ConvexLens",
//                new ConvexLens(300, 300, 300, 300, 200));
//        app.regLight("BlueLightUp", 0, 210, 0, LightPath.BLUE_LIGHT_WAVE_LENGTH);
    }

    @Override
    public void onUpdate(double time) {

        app.unregLight("BlueLightUp");
        app.unregLight("BlueLightDown");
        app.unregLight("RedLightUp");
        app.unregLight("RedLightDown");
        app.unregLight("GreenLightUp");
        app.unregLight("GreenLightDown");


        app.regLight("BlueLightUp", 0, 210, 0, LightInfo.BLUE);
        app.regLight("BlueLightDown", 0, 390, 0, LightInfo.BLUE);
        app.regLight("RedLightUp", 0, 210, 0, LightInfo.RED);
        app.regLight("RedLightDown", 0, 390, 0, LightInfo.RED);
        app.regLight("GreenLightUp", 0, 210, 0, LightInfo.GREEN);
        app.regLight("GreenLightDown", 0, 390, 0, LightInfo.GREEN);

        app.intersectionDetect();

        app.draw(demonstratePane);
        demonstratePane.getChildren().add(standardLine);
        changeLensRadius();

    }

    public void changeLensRadius() {
        app.unregLens("ConcaveLens");
        app.regLens("ConcaveLens",
                new ConcaveLens(400, 300, lensRadius, lensRadius, 30, 200, LensMaterial.H_K10));
        app.unregLens("ConvexLens");
        app.regLens("ConvexLens",
                new ConvexLens(300, 300, lensRadius, lensRadius, 200, LensMaterial.H_K10));
    }
}

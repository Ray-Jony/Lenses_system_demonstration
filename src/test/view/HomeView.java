package test.view;

import static Framework.Framework.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;


public class HomeView extends View {


    private Button playBtn;
    private Button exitBtn;
    private Button reflectionDemoBtn;
    private Button SphericalAberrationDemoBtn;
    private Button chromaticAberrationDemoBtn;
    private Button controllerTestViewBtn;

    private Pane listPane;

    @Override
    public void onLaunch() {
        listPane = new StackPane();

        playBtn = new Button("Play");
        playBtn.setOnAction(e -> app.gotoView("Play"));

        reflectionDemoBtn = new Button("Reflection Demo");
        reflectionDemoBtn.setOnAction(e -> app.gotoView("Demo"));

        SphericalAberrationDemoBtn = new Button("SphericalAberrationDemo");
        SphericalAberrationDemoBtn.setOnAction(e -> app.gotoView("LensDemo"));

        chromaticAberrationDemoBtn = new Button("Chromatic Aberration Demo");
        chromaticAberrationDemoBtn.setOnAction(e -> app.gotoView("ChromaticAberrationDemo"));

        controllerTestViewBtn = new Button("ControllerTest");
        controllerTestViewBtn.setOnAction(e -> app.gotoView("controllerTestView"));

        exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> app.exit());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(reflectionDemoBtn,
                SphericalAberrationDemoBtn,
                chromaticAberrationDemoBtn,
                controllerTestViewBtn,
                exitBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        listPane.getChildren().addAll(vBox);
        listPane.setLayoutX(400);
        listPane.setLayoutY(300);

        getPane().getChildren().add(listPane);
    }

    @Override
    public void onEnter() {
        System.out.println(app.getMinWidth() + " " + app.getMinHeight());
    }

    @Override
    public void onUpdate(double time) {

//        System.out.println("Mouse position:" + mouseInput.getPointX() +", "+ mouseInput.getPointY());

    }
}

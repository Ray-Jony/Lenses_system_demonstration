package test.view;

import static Framework.Framework.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;


public class HomeView extends View {

    private Button openSideBarBtn;
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

        openSideBarBtn = new Button("open side bar");
        app.addOpenSideBarHandler(openSideBarBtn);

        playBtn = new Button("Play");
        playBtn.setOnAction(e -> app.gotoView("Play"));

        reflectionDemoBtn = new Button("Reflection Demo");
        reflectionDemoBtn.setOnAction(e -> app.gotoView("Reflection"));

        SphericalAberrationDemoBtn = new Button("SphericalAberrationDemo");
        SphericalAberrationDemoBtn.setOnAction(e -> app.gotoView("Spherical Aberration"));

        chromaticAberrationDemoBtn = new Button("Chromatic Aberration Demo");
        chromaticAberrationDemoBtn.setOnAction(e -> app.gotoView("Chromatic Aberration"));

        controllerTestViewBtn = new Button("Zooming");
        controllerTestViewBtn.setOnAction(e -> app.gotoView("Zooming"));

        exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> app.exit());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                openSideBarBtn,
                reflectionDemoBtn,
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
//        System.out.println(app.getMinWidth() + " " + app.getMinHeight());
    }

    @Override
    public void onUpdate(double time) {

//        System.out.println("Mouse position:" + mouseInput.getPointX() +", "+ mouseInput.getPointY());

    }
}

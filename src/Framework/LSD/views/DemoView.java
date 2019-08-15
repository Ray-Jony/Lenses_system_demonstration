package Framework.LSD.views;

import Framework.LSD.views.Controller.DemoViewController;
import Framework.LSD.app.View;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Lens.Lens;
import Framework.LSD.world.Lens.LensInfo;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;

import static Framework.Framework.app;

/**
 * Class DemoView is created on 07/08/2019 23:55.
 *
 * @author Ray
 * @version 07/08/2019
 **/

public class DemoView extends View {

    private BorderPane demoPane;
    private AnchorPane lensControl;
    private AnchorPane mainDemoPane;
    private AnchorPane lightControllerPane;
    private AnchorPane infoPane;

    private Line standardLine;

    private DemoViewController demoViewController;

    @Override
    public void onLaunch() {
        getFxmlLoader().setLocation(getFxmlLoader()
                .getClassLoader()
                .getResource("Framework/LSD/views/FXML/DemoView.fxml"));
        try {
            demoPane = (BorderPane) getFxmlLoader().load();
            demoViewController = getFxmlLoader().getController();
            getPane().getChildren().add(demoPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception in " + getClass().getName());
        }

        getPane().prefWidthProperty().bind(app.widthProperty());
        getPane().prefHeightProperty().bind(app.heightProperty());

        demoPane.prefWidthProperty().bind(app.widthProperty());
        demoPane.prefHeightProperty().bind(app.heightProperty());

        mainDemoPane = getController().getMainDemoPane();
        infoPane = getController().getInfoPane();
        lightControllerPane = getController().getLightControllerPane();
        lensControl = getController().getLensControllerPane();


//        demoPane.setCenter(centerLayout);
//        demoPane.setLeft(lightControllerPane);
//        demoPane.setRight(infoPane);


    }

    @Override
    public void onEnter() {
        app.reset();
    }

    @Override
    public void onUpdate(double time) {
        super.onUpdate(time);
    }


    //*****************************

    public void animatedLight(String LightName, double startPointY, double direction, LightInfo lightInfo) {
        double relativePositionY = (mainDemoPane.getHeight() / 2) - startPointY;

        app.unregLight(LightName);
        app.regLight(LightName, 0, relativePositionY, direction, lightInfo);
    }

    public void animatedConvexLens(String LensName, double positionX, double leftRadius, double rightRadius, double height) {
        app.unregLens(LensName);
        app.regLens(LensName, new ConvexLens(
                positionX, getMainDemoPane().getHeight() / 2,
                leftRadius, rightRadius,
                height, LensInfo.H_K10));
    }


    public void animatedMirror(String MirrorName, Mirror mirror) {
        app.unregMirror(MirrorName);
        app.regMirror(MirrorName, mirror);
    }

    public void animatedBoard(String name, double startPointX, double startPointY, double endPointX, double endPointY) {
        app.unregMirror(name);
        app.regMirror(name, new FlatMirror(startPointX, startPointY, endPointX, endPointY, true));
    }

    public void drawMainDemoPane() {
        app.draw(mainDemoPane);
    }

    public void highLightLight(String LightName) {
        app.highlightLight(mainDemoPane, LightName);
    }

    //*****************

    public void intersectionDetect() {
        app.intersectionDetect();
    }

    //******************

    public void setDemoTitle(String name) {
        getController().getDemoName().setText(name);
    }

    public JFXSlider getLensPositionSlider() {
        return getController().getLensPositionSlider();
    }

    public void addStandardLine() {
        standardLine = new Line(10, getMainDemoPane().getHeight() / 2,
                getMainDemoPane().getWidth() - 10, getMainDemoPane().getHeight() / 2);

        getMainDemoPane().getChildren().add(standardLine);

    }


    //******************
    public BorderPane getDemoPane() {
        return demoPane;
    }

    public AnchorPane getMainDemoPane() {
        return mainDemoPane;
    }

    public AnchorPane getLightControllerPane() {
        return lightControllerPane;
    }

    public AnchorPane getInfoPane() {
        return infoPane;
    }


    public AnchorPane getLensControl() {
        return lensControl;
    }

    public void setMainDemoPane(AnchorPane mainDemoPane) {
        this.mainDemoPane = mainDemoPane;
    }

    public void setLightControllerPane(AnchorPane lightControllerPane) {
        this.lightControllerPane = lightControllerPane;
    }

    public void setInfoPane(AnchorPane infoPane) {
        this.infoPane = infoPane;
    }

    //****************
    public DemoViewController getController() {
        return demoViewController;
    }


}

package Framework.LSD.views;

import Framework.LSD.views.Controller.DemoViewController;
import Framework.LSD.app.View;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Lens.LensInfo;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static Framework.Framework.app;

/**
 * Class DemoView is created on 07/08/2019 23:55.
 *
 * @author Ray
 * @version 07/08/2019
 **/

public class DemoView extends View {

    private BorderPane demoPane;

    private Line standardLine;

    private DemoViewController demoViewController;

    private HashMap<String, ArrayList<Object>> animatedLightMap = new HashMap<>();

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

        getAddNewLightBrn().setOnAction(e -> {
            Dialog<ArrayList<Object>> dialog = new Dialog<>();
            dialog.setTitle("set a new light");
//            dialog.setHeaderText("fill new light info");

            ButtonType addNewLight = new ButtonType("addNewLight", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addNewLight, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 20, 10, 10));

            TextField lightName = new TextField();
            lightName.setPromptText("LightName");
            JFXSlider positionSlider = new JFXSlider();
            positionSlider.setMax(200);
            positionSlider.setMin(-200);
            positionSlider.setValue(0);
            JFXSlider directionSlider = new JFXSlider();
            directionSlider.setMin(-90);
            directionSlider.setMax(90);
            directionSlider.setValue(0);

            JFXRadioButton radioButtonRED = new JFXRadioButton("RED");
            JFXRadioButton radioButtonGREEN = new JFXRadioButton("GREEN");
            JFXRadioButton radioButtonBLUE = new JFXRadioButton("BLUE");

            ToggleGroup group = new ToggleGroup();

            radioButtonRED.setToggleGroup(group);
            radioButtonRED.setUserData(LightInfo.RED);
            radioButtonRED.setSelected(true);
            radioButtonGREEN.setToggleGroup(group);
            radioButtonGREEN.setUserData(LightInfo.GREEN);
            radioButtonBLUE.setToggleGroup(group);
            radioButtonBLUE.setUserData(LightInfo.BLUE);

            gridPane.add(new Label("Light Name:"), 0, 0);
            gridPane.add(lightName, 1, 0);
            gridPane.add(new Label("Light Position"), 0, 1);
            gridPane.add(positionSlider, 1, 1);
            gridPane.add(new Label("Light Direction"), 0, 2);
            gridPane.add(directionSlider, 1, 2);
            gridPane.add(new Label("Light Color"), 0, 4);
            gridPane.add(radioButtonRED, 0, 5);
            gridPane.add(radioButtonGREEN, 0, 6);
            gridPane.add(radioButtonBLUE, 0, 7);

            Node addNewLightBtn = dialog.getDialogPane().lookupButton(addNewLight);
            addNewLightBtn.setDisable(true);

            lightName.textProperty().addListener((o, ov, nv) -> {
                addNewLightBtn.setDisable(nv.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(gridPane);

            Platform.runLater(lightName::requestFocus);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addNewLight) {
                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(lightName.getText());
                    arr.add(positionSlider.getValue());
                    arr.add(directionSlider.getValue());
                    arr.add(group.getSelectedToggle().getUserData());
                    return arr;
                }
                return null;
            });

            Optional<ArrayList<Object>> result = dialog.showAndWait();

            result.ifPresent(lightInfo -> {
                System.out.println(lightInfo.get(0));
                System.out.println(lightInfo.get(1));
                System.out.println(lightInfo.get(2));
                System.out.println(lightInfo.get(3));

                String name = (String) lightInfo.get(0);
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(lightInfo.get(1));
                temp.add(lightInfo.get(2));
                temp.add(lightInfo.get(3));
                animatedLightMap.put(name, temp);
            });
        });


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
        double relativePositionY = (getMainDemoPane().getHeight() / 2) - startPointY;

        app.unregLight(LightName);
        app.regLight(LightName, 0, relativePositionY, -Math.PI * (direction / 180), lightInfo);
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
        app.draw(getMainDemoPane());
    }

    public void highLightLight(String LightName) {
        app.highlightLight(getMainDemoPane(), LightName);
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

    public JFXSlider getLightPositionSlider() {
        return getController().getLightPositionSlider();
    }

    public JFXSlider getLightDirectionSlider() {
        return getController().getLightDirectionSlider();
    }

    public JFXButton getLightDirectionResetBtn() {
        return getController().getLightDirectionResetBtn();
    }

    public JFXToggleButton getSymmetricalRayBtn() {
        return getController().getSymmetricalRayToggleBtn();
    }

    public JFXButton getAddNewLightBrn() {
        return getController().getAddNewLight();
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
        return getController().getMainDemoPane();
    }

    public AnchorPane getLightControllerPane() {
        return getController().getLightControllerPane();
    }

    public AnchorPane getInfoPane() {
        return getController().getInfoPane();
    }


    public AnchorPane getLensControlPane() {
        return getController().getLensControllerPane();
    }


    //****************
    public DemoViewController getController() {
        return demoViewController;
    }

    //***************


    public HashMap<String, ArrayList<Object>> getAnimatedLightMap() {
        return animatedLightMap;
    }
}

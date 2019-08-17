package Framework.LSD.views;

import Framework.LSD.views.Controller.DemoViewController;
import Framework.LSD.app.View;
import Framework.LSD.world.ComponentType;
import Framework.LSD.world.Lens.*;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private String currentSelectedLight = "";
    private String currentSelectedLens = "";
    private String currentSelectedMirror = "";

    private static final String SYMMETRICAL_ID = "[symmetrical]";

    private DemoViewController demoViewController;
    /**
     * HashMap animatedLightMap:
     * |- String: light name
     * |- ArrayList<Object>: contains Light information
     * ---|- get(0): (double) light Y position (relative to standard line)
     * ---|- get(1): (double) light direction
     * ---|- get(2): (LightInfo) light color
     * ---|- get(3): (boolean) has symmetrical light
     */
    private HashMap<String, ArrayList<Object>> animatedLightMap = new HashMap<>();
    /**
     * HashMap animatedLensMap:
     * |- String: lens name
     * |- ArrayList<Object>: contains Lens information
     * ---|- get(0): (LensType) lens type
     * ---|- get(1): (double) lens X position
     * ---|- get(2): (double) lens left radius
     * ---|- get(3): (double) lens right radius
     * ---|- get(4): (double) lens height
     * ---|- get(5): (LensMaterial) lens material
     * ---|- *get(6): (double) concave lens min width (Only exist if LensType == LensType.CONCAVE_LENS)
     */
    private HashMap<String, ArrayList<Object>> animatedLensMap = new HashMap<>();
    /**
     *
     */
    private HashMap<String, ArrayList<Object>> animatedMirrorMap = new HashMap<>();

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

        getLightDirectionSlider().setValue(0);

        getAddNewLightBtn().setOnAction(e -> {
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

            JFXCheckBox SymmetricalRayCheckBox = new JFXCheckBox("Symmetrical Ray");
            SymmetricalRayCheckBox.setSelected(false);

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
            gridPane.add(new Label("Symmetrical Ray"), 0, 8);
            gridPane.add(SymmetricalRayCheckBox, 0, 9);

            Node addNewLightBtn = dialog.getDialogPane().lookupButton(addNewLight);
            addNewLightBtn.setDisable(true);

            lightName.textProperty().addListener((o, ov, nv) ->
                    addNewLightBtn.setDisable(nv.trim().isEmpty() || animatedLightMap.containsKey(nv)));

            dialog.getDialogPane().setContent(gridPane);

            Platform.runLater(lightName::requestFocus);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addNewLight) {
                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(lightName.getText());
                    arr.add(positionSlider.getValue());
                    arr.add(directionSlider.getValue());
                    arr.add(group.getSelectedToggle().getUserData());
                    arr.add(SymmetricalRayCheckBox.isSelected());
                    return arr;
                }
                return null;
            });

            Optional<ArrayList<Object>> result = dialog.showAndWait();

            result.ifPresent(lightInfo -> {
                System.out.println("light name: " + lightInfo.get(0));
                System.out.println("light Position: " + lightInfo.get(1));
                System.out.println("light Direction: " + lightInfo.get(2));
                System.out.println("light Color: " + lightInfo.get(3));
                System.out.println("Symmetrical Light: " + lightInfo.get(4));

                String name = (String) lightInfo.get(0);
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(lightInfo.get(1));
                temp.add(lightInfo.get(2));
                temp.add(lightInfo.get(3));
                temp.add(lightInfo.get(4));
                animatedLightMap.put(name, temp);
                getLightSelector().getItems().add(name);
                getLightSelector().getSelectionModel().select(name);
            });
        });

        getLightSelector().setOnAction(e -> {
            if (getLightSelector().getValue() == null) {
                currentSelectedLight = "";
            } else currentSelectedLight = getLightSelector().getValue();

            ArrayList<Object> defaultValue = new ArrayList<>(Arrays.asList(0D, 0D, LightInfo.RED, false));
            ArrayList<Object> selectedLightInfo =
                    new ArrayList<>(animatedLightMap.getOrDefault(currentSelectedLight, defaultValue));
            double light_Y_position = (double) selectedLightInfo.get(0);
            double light_direction = (double) selectedLightInfo.get(1);
            LightInfo lightInfo = (LightInfo) selectedLightInfo.get(2);
            boolean hasSymmetricalRay = (boolean) selectedLightInfo.get(3);


            getLightPositionSlider().setValue(light_Y_position);
            getLightDirectionSlider().setValue(light_direction);
            getSymmetricalRayBtn().selectedProperty().set(hasSymmetricalRay);

            switch (lightInfo) {
                case RED:
                    getLightColorSelector().selectToggle(getController().getLightColor_Red());
                    break;
                case GREEN:
                    getLightColorSelector().selectToggle(getController().getLightColor_Green());
                    break;
                case BLUE:
                    getLightColorSelector().selectToggle(getController().getLightColor_Blue());
                    break;
                default:
                    break;
            }

            getController().getWaveLength()
                    .setText(String.valueOf(lightInfo.getWaveLength()));
        });

        getLightPositionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!currentSelectedLight.equals("") && animatedLightMap.containsKey(currentSelectedLight)) {
                animatedLightMap.get(currentSelectedLight).set(0, t1);
            }
        });

        getLightDirectionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!currentSelectedLight.equals("") && animatedLightMap.containsKey(currentSelectedLight)) {
                animatedLightMap.get(currentSelectedLight).set(1, t1);
            }
        });

        getLightColorSelector().selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (!currentSelectedLight.equals("") && animatedLightMap.containsKey(currentSelectedLight)) {
                animatedLightMap.get(currentSelectedLight).set(2, t1.getUserData());
            }
            getController().getWaveLength()
                    .setText(String.valueOf(((LightInfo) t1.getUserData()).getWaveLength()));
        });

        getLightDirectionResetBtn().setOnAction(e -> {
            getLightDirectionSlider().setValue(0);
        });

        getDeleteLightBtn().setOnAction(e -> {
            if (!currentSelectedLight.equals("") && animatedLightMap.containsKey(currentSelectedLight)) {
                removeAnimatedComponent(ComponentType.LIGHT, currentSelectedLight);
                getLightSelector().getItems().remove(currentSelectedLight);
                currentSelectedLight = "";
            }
        });

        getSymmetricalRayBtn().selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (currentSelectedLight.equals("")) return;

            if (t1)
                animatedLightMap.get(currentSelectedLight).set(3, true);
            else
                animatedLightMap.get(currentSelectedLight).set(3, false);
        });

        getLensPositionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!currentSelectedLens.equals("") && animatedLensMap.containsKey(currentSelectedLens)) {
                animatedLensMap.get(currentSelectedLens).set(1, t1);
            }
        });


    }

    @Override
    public void onEnter() {
        app.reset();
    }

    @Override
    public void onUpdate(double time) {
        super.onUpdate(time);

        for (String key :
                getAnimatedLightMap().keySet()) {
            ArrayList info = getAnimatedLightMap().get(key);
            animatedLight(key, (double) info.get(0), (double) info.get(1),
                    (LightInfo) info.get(2), (boolean) info.get(3));
        }

        for (String key :
                getAnimatedLensMap().keySet()) {
            ArrayList info = getAnimatedLensMap().get(key);
            LensType lensType = (LensType) info.get(0);
            switch (lensType) {
                case ConvexLens:
                    animatedConvexLens(
                            key, (double) info.get(1),
                            (double) info.get(2), (double) info.get(3),
                            (double) info.get(4), (LensMaterial) info.get(5)
                    );
                    break;
                case ConcaveLens:
                    animatedConcaveLens(
                            key, (double) info.get(1),
                            (double) info.get(2), (double) info.get(3),
                            (double) info.get(4), (LensMaterial) info.get(5),
                            (double) info.get(6)
                    );
                    break;
                default:
                    break;
            }
        }
    }


    //*****************************

    public void animatedLight(String LightName, double startPointY, double direction,
                              LightInfo lightInfo, boolean hasSymmetrical) {
        double relativePositionY = (getMainDemoPane().getHeight() / 2) - startPointY;
        double symmetricalPositionY = (getMainDemoPane().getHeight() / 2) + startPointY;

        app.unregLight(LightName);
        app.regLight(LightName, 0, relativePositionY,
                -Math.PI * (direction / 180), lightInfo);

        if (hasSymmetrical) {
            app.unregLight(LightName + SYMMETRICAL_ID);
            app.regLight(LightName + SYMMETRICAL_ID, 0, symmetricalPositionY,
                    Math.PI * (direction / 180), lightInfo);
        } else {
            app.unregLight(LightName + SYMMETRICAL_ID);
        }
    }

    public void addAnimatedLens(String lensName, ArrayList<Object> lensInfo) {
        animatedLensMap.put(lensName, lensInfo);
    }


    public void animatedConvexLens(String lensName, double positionX,
                                   double leftRadius, double rightRadius,
                                   double height, LensMaterial lensMaterial) {
        app.unregLens(lensName);
        app.regLens(lensName, new ConvexLens(
                positionX, getMainDemoPane().getHeight() / 2,
                leftRadius, rightRadius,
                height, lensMaterial
        ));
    }

    public void animatedConcaveLens(String lensName, double positionX,
                                    double leftRadius, double rightRadius,
                                    double height, LensMaterial lensMaterial, double minWidth) {
        app.unregLens(lensName);
        app.regLens(lensName, new ConcaveLens(
                positionX, getMainDemoPane().getHeight() / 2,
                leftRadius, rightRadius,
                minWidth, height,
                lensMaterial
        ));
    }


    public void animatedMirror(String MirrorName, Mirror mirror) {
        app.unregMirror(MirrorName);
        app.regMirror(MirrorName, mirror);
    }

    public void animatedBoard(String name, double startPointX, double startPointY,
                              double endPointX, double endPointY) {
        app.unregMirror(name);
        app.regMirror(name, new FlatMirror(startPointX, startPointY,
                endPointX, endPointY, true));
    }

    public void removeAnimatedComponent(ComponentType componentType, String name) {
        switch (componentType) {
            case LIGHT:
                animatedLightMap.remove(name);
                app.unregLight(name);
                app.unregLight(name + SYMMETRICAL_ID);
                break;
            case LENS:
                animatedLensMap.remove(name);
                app.unregLens(name);
                break;
            case MIRROR:
                animatedMirrorMap.remove(name);
                app.unregMirror(name);
                break;
            default:
                break;
        }
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

    public JFXComboBox<String> getLightSelector() {
        return getController().getJFxLightSelector();
    }

    public ToggleGroup getLightColorSelector() {
        return getController().getLightColorSelector();
    }

    public JFXButton getAddNewLightBtn() {
        return getController().getAddNewLight();
    }

    public JFXButton getDeleteLightBtn() {
        return getController().getDeleteThisLight();
    }

    public JFXComboBox<String> getLensSelector() {
        return getController().getJFxLensSelector();
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

    public HashMap<String, ArrayList<Object>> getAnimatedLensMap() {
        return animatedLensMap;
    }

    public HashMap<String, ArrayList<Object>> getAnimatedMirrorMap() {
        return animatedMirrorMap;
    }

    public String getCurrentSelectedLight() {
        return currentSelectedLight;
    }

    public String getCurrentSelectedLens() {
        return currentSelectedLens;
    }

    public String getCurrentSelectedMirror() {
        return currentSelectedMirror;
    }
}

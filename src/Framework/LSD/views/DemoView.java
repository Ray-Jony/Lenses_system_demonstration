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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
 * Defined the temple of the demo views, initial basic functionality for the demo views to extends
 *
 * @author Ray
 * @version 07/08/2019
 **/

public abstract class DemoView extends View {

    private BorderPane demoPane;

    private Line standardLine;

    private boolean updateInitializationFinished = false;

    private String currentSelectedLight = "";
    private String currentSelectedLens = "";
    private String currentSelectedMirror = "";

    private boolean enableHighlight = true;
    private boolean enableLensRadius = false;
    private boolean enableLightControl = true;

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

        app.addOpenSideBarHandler(getController().getDemoName());

        JFXSlider leftRadiusSlider = new JFXSlider(220D, 2000D, 300D);
        leftRadiusSlider.setOrientation(Orientation.VERTICAL);
        JFXSlider rightRadiusSlider = new JFXSlider(220D, 2000D, 300D);
        rightRadiusSlider.setOrientation(Orientation.VERTICAL);

        leftRadiusSlider.valueProperty().addListener((observableValue, number, t1) -> {
            if (!getCurrentSelectedLens().equals("") && getAnimatedLensMap().containsKey(getCurrentSelectedLens())) {
                getAnimatedLensMap().get(getCurrentSelectedLens()).set(2, t1);
            }
        });
        rightRadiusSlider.valueProperty().addListener((observableValue, number, t1) -> {
            if (!getCurrentSelectedLens().equals("") && getAnimatedLensMap().containsKey(getCurrentSelectedLens())) {
                getAnimatedLensMap().get(getCurrentSelectedLens()).set(3, t1);
            }
        });

        getLightDirectionSlider().setValue(0);


        getAddNewLightBtn().setOnAction(e -> {
            Dialog<ArrayList<Object>> dialog = new Dialog<>();
            dialog.setTitle("Set a new light");

            ButtonType addNewLight = new ButtonType("Add New Light", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addNewLight, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10, 10, 10, 10));

            TextField lightName = new TextField();
            lightName.setPromptText("Light Name");

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
                animatedLightMap.put(name, new ArrayList<>(temp));
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
//                getLightSelector().getItems().remove(currentSelectedLight);
                currentSelectedLight = "";
            }
        });

        getSymmetricalRayBtn().selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (currentSelectedLight.equals("")) return;
            animatedLightMap.get(currentSelectedLight).set(3, t1);

        });

        getLensSelector().setOnAction(e -> {
            if (getLensSelector().getValue() == null) {
                currentSelectedLens = "";
            } else currentSelectedLens = getLensSelector().getValue();

            ArrayList<Object> defaultValue =
                    new ArrayList<>(Arrays.asList(LensType.ConvexLens, 50d, 250d, 250d, 200d, LensMaterial.H_K10, 50d));
            ArrayList<Object> selectedLensInfo =
                    new ArrayList<>(animatedLensMap.getOrDefault(currentSelectedLens, defaultValue));

            LensType lensType = (LensType) selectedLensInfo.get(0);
            double lens_X_position = (double) selectedLensInfo.get(1);
            double lens_left_radius = (double) selectedLensInfo.get(2);
            double lens_right_radius = (double) selectedLensInfo.get(3);
            double lens_height = (double) selectedLensInfo.get(4);
            LensMaterial lensMaterial = (LensMaterial) selectedLensInfo.get(5);
            double concave_lens_minWidth;
            if (selectedLensInfo.size() > 6) {
                concave_lens_minWidth = (double) selectedLensInfo.get(6);
            }

            if (enableLensRadius) {
                leftRadiusSlider.setValue(lens_left_radius);
                rightRadiusSlider.setValue(lens_right_radius);
                leftRadiusSlider.setMin((lens_height / 2) + 30);
                rightRadiusSlider.setMin((lens_height / 2) + 30);
            }

            getLensPositionSlider().setValue(lens_X_position);
            getController().getChoseLensMaterialBtn()
                    .setText(lensMaterial.getSequenceNumber() + " " + lensMaterial.toString());
            getController().getLensCode().setText(lensMaterial.getLensCode());
            getController().getnD().setText("nD = " + lensMaterial.getnD());
            getController().getnC().setText("nC = " + lensMaterial.getnC());
            getController().getnC2().setText("nC' = " + lensMaterial.getnC2());
            getController().getnF().setText("nF = " + lensMaterial.getnF());
            getController().getnF2().setText("nF' = " + lensMaterial.getnF2());
            getController().getVd().setText("Vd = " + String.format("%.5f", lensMaterial.getVd()));
            getController().getVd2().setText("V'd = " + String.format("%.5f", lensMaterial.getVd2()));

        });

        getController().getChoseLensMaterialBtn().setOnAction(e -> {
            //TODO change to JFXTreeTableView to enable filter function
            Dialog<LensMaterial> dialog = new Dialog<>();
            dialog.setTitle("Chose Lens Material");

            ButtonType chose = new ButtonType("Chose", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(chose, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 10, 10, 10));

            JFXListView<String> materialList = new JFXListView<>();
            materialList.setMinWidth(350);

            HashMap<String, LensMaterial> materialMap = new HashMap<>();
            for (LensMaterial l :
                    LensMaterial.values()) {
                String listCellString = String.format(l.getSequenceNumber() + " %-12s", l.toString())
                        + String.format("%-20s", "[LensCode]: " + l.getLensCode())
                        + "[Vd]: " + String.format("%.5f", l.getVd());
                materialList.getItems().add(listCellString);
                materialMap.put(listCellString, l);
            }

            gridPane.add(new Label("Material List:"), 0, 0);
            gridPane.add(materialList, 0, 1);

            Node choseBtn = dialog.getDialogPane().lookupButton(chose);
            choseBtn.setDisable(true);

            materialList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) ->
                    choseBtn.setDisable(t1 == null || t1.isEmpty()));

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogBtn -> {
                if (dialogBtn == chose)
                    return materialMap.get(materialList.getSelectionModel().getSelectedItem());
                return null;
            });

            Optional<LensMaterial> result = dialog.showAndWait();

            result.ifPresent(lensMaterial -> {
                System.out.println("lens material: " + lensMaterial);
                getController().getChoseLensMaterialBtn()
                        .setText(lensMaterial.getSequenceNumber() + " " + lensMaterial.toString());
                getController().getLensCode().setText(lensMaterial.getLensCode());
                animatedLensMap.get(currentSelectedLens).set(5, lensMaterial);
                String temp = currentSelectedLens;
                getLensSelector().getSelectionModel().clearSelection();
                getLensSelector().getSelectionModel().select(temp);
            });

        });


        getLensPositionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!currentSelectedLens.equals("") && animatedLensMap.containsKey(currentSelectedLens)) {
                animatedLensMap.get(currentSelectedLens).set(1, t1);
            }
        });

        getController().getDeselectLightBtn().setOnAction(e -> {
            getLightSelector().getSelectionModel().select(null);
        });


        launch();

        if (enableLensRadius) {

            Label radiusControl = new Label("Radius Control");
            radiusControl.setMinWidth(80);

            HBox radiusControlHBox = new HBox(leftRadiusSlider, rightRadiusSlider);
            radiusControlHBox.setSpacing(10);
            radiusControlHBox.setPadding(new Insets(0, 10, 0, 10));
            radiusControlHBox.setAlignment(Pos.CENTER);
            getController().getTopMenu().getChildren()
                    .add(1, new VBox(radiusControl, radiusControlHBox));
//            getController().getTopMenu().getChildren().add(2, leftRadiusSlider);
//            getController().getTopMenu().getChildren().add(3, rightRadiusSlider);
        }


        if (!enableLightControl) {
            getAddNewLightBtn().setDisable(true);
            getLightSelector().setDisable(true);
            getController().getLightColor_Red().setDisable(true);
            getController().getLightColor_Green().setDisable(true);
            getController().getLightColor_Blue().setDisable(true);
            getController().getDeleteThisLight().setDisable(true);
            getController().getSymmetricalRayToggleBtn().setDisable(true);
        }


//        getLensSelector().getItems().addAll(getAnimatedLensMap().keySet());
//        getLightSelector().getItems().addAll(getAnimatedLightMap().keySet());
    }

    public abstract void launch();


    @Override
    public void onEnter() {
        app.reset();
    }


    public void updateFrame() {
        intersectionDetect();
        drawMainDemoPane();

        if (enableHighlight) {
            highLightLight(getCurrentSelectedLight());
            highLightLens(getCurrentSelectedLens());
        }
    }

    public void addBorder() {
        animatedBoard("TopBoard", 0, 5,
                10000, 5);
        animatedBoard("BottomBoard",
                0, getMainDemoPane().getHeight() - 20,
                10000, getMainDemoPane().getHeight() - 20);
    }

    public void update() {
        addStandardLine();

        getLensPositionSlider().setMax(getMainDemoPane().getWidth() - 30);
        getLensPositionSlider().setMin(30);

        getLightPositionSlider().setMax((int) (getMainDemoPane().getHeight() / 2 - 25));
        getLightPositionSlider().setMin(-(int) ((getMainDemoPane().getHeight() / 2 - 25)));
        //for subclass to finish
    }

    public void updateInitialization() {
        //for subclass to finish
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

        addBorder();
        updateFrame();
        update();
//        updateInitialization();
//        if (!updateInitializationFinished) {
////            updateInitialization();
//            updateInitializationFinished = true;
//        }
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

    public void addAnimatedLens(String lensName, ArrayList<Object> lensInfo, boolean enableControl) {
        animatedLensMap.put(lensName, lensInfo);
        if (enableControl)
            getLensSelector().getItems().add(lensName);
    }

    public void addAnimatedLight(String lightName, ArrayList<Object> lightInfo, boolean enableControl) {
        animatedLightMap.put(lightName, lightInfo);
        if (enableControl)
            getLightSelector().getItems().add(lightName);
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
                getLightSelector().getItems().remove(name);
                break;
            case LENS:
                animatedLensMap.remove(name);
                app.unregLens(name);
                getLensSelector().getItems().remove(name);
                break;
            case MIRROR:
                animatedMirrorMap.remove(name);
                app.unregMirror(name);
                //TODO if there will be a Mirror selector
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

    public void highLightLens(String LensName) {
        app.highlightLens(getMainDemoPane(), LensName);
    }

    //*****************

    public void intersectionDetect() {
        app.intersectionDetect();
    }

    //******************

    public void SetEnableHighlight(boolean enable) {
        this.enableHighlight = enable;
    }

    public void SetEnableLensRadius(boolean enable) {
        this.enableLensRadius = enable;
    }

    public void setEnableLightControl(boolean enable) {
        this.enableLightControl = enable;
    }

    //*******************

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

    public void setCurrentSelectedLight(String currentSelectedLight) {
        this.currentSelectedLight = currentSelectedLight;
    }

    public String getCurrentSelectedLens() {
        return currentSelectedLens;
    }

    public void setCurrentSelectedLens(String currentSelectedLens) {
        this.currentSelectedLens = currentSelectedLens;
    }

    public String getCurrentSelectedMirror() {
        return currentSelectedMirror;
    }

    public void setCurrentSelectedMirror(String currentSelectedMirror) {
        this.currentSelectedMirror = currentSelectedMirror;
    }

    public void setTopMenuColor(String colorCode) {
        getController().getTopMenu().setStyle("-fx-background-color: " + colorCode);
    }
}

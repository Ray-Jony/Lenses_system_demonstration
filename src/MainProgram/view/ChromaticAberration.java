package MainProgram.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.ComponentType;
import Framework.LSD.world.Lens.LensMaterial;
import Framework.LSD.world.Lens.LensType;
import Framework.LSD.world.Light.LightInfo;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Class ChromaticAberration is created on 19/08/2019 19:19.
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class ChromaticAberration extends DemoView {
    private boolean switchOn = true;

    @Override
    public void launch() {
        setDemoTitle("Chromatic Aberration");
        getController().getDemoName().setFont(Font.font("Roboto Black", 20));
        SetEnableHighlight(false);
        SetEnableLensRadius(true);

        addAnimatedLight("ColoredLight", new ArrayList<>(Arrays.asList(
                150D, 0D, LightInfo.RED, true
        )), true);

        addAnimatedLight("ColoredLight[2]", new ArrayList<>(Arrays.asList(
                150D, 0D, LightInfo.GREEN, true
        )), false);

        addAnimatedLight("ColoredLight[3]", new ArrayList<>(Arrays.asList(
                150D, 0D, LightInfo.BLUE, true
        )), false);

        addAnimatedLens("ConvexLens1", new ArrayList<>(Arrays.asList(
                LensType.ConvexLens, 200D, 1150d, 1150d, 400D, LensMaterial.findViaLensID(247)
        )), true);


        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10,10,10,10));

        JFXButton switchBtn = new JFXButton("Switch");
        switchBtn.setPrefWidth(200);
        switchBtn.setPrefHeight(50);
        String switchBtnDefaultStyle = "-fx-background-color: #b0d8ff;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: black";
        switchBtn.setStyle(switchBtnDefaultStyle);

        switchBtn.hoverProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                switchBtn.setStyle("-fx-text-fill: white;" +
                        "-fx-font-size: 20;" +
                        "-fx-background-color: #4683ff");
            }else {
                switchBtn.setStyle(switchBtnDefaultStyle);
            }
        });

        Label info = new Label();
        info.setWrapText(true);

        info.setMaxWidth(200);
        info.setText("Try to Type the Switch Button!\n");
        info.setStyle("-fx-font-size: 20");

        switchBtn.setOnAction(e -> {
            if (switchOn) {
                switchOn = false;
                removeAnimatedComponent(ComponentType.LENS, "ConvexLens1");

                addAnimatedLens("ConvexLens2", new ArrayList<>(Arrays.asList(
                        LensType.ConvexLens, 200D, 500d, 500d, 400D, LensMaterial.findViaLensID(247)
                )), true);

                addAnimatedLens("ConcaveLens", new ArrayList<>(Arrays.asList(
                        LensType.ConcaveLens, 255d, 500D, 2000D, 400D, LensMaterial.findViaLensID(156), 25D
                )), true);

                getLensSelector().getSelectionModel().select("ConcaveLens");

                info.setText("Explanation:\n" +
                        "   add a concave lens beside with different material, will significantly reduce the" +
                        " chromatic aberration, but it still require non-spherical lens to achieve full range" +
                        "(Any light enter position) eliminating chromatic aberration.");

            } else {
                switchOn = true;
                removeAnimatedComponent(ComponentType.LENS, "ConvexLens2");
                removeAnimatedComponent(ComponentType.LENS, "ConcaveLens");

                addAnimatedLens("ConvexLens1", new ArrayList<>(Arrays.asList(
                        LensType.ConvexLens, 200D, 1150d, 1150d, 400D, LensMaterial.findViaLensID(247)
                )), true);

                getLensSelector().getSelectionModel().select("ConvexLens1");
            }

        });


        box.getChildren().addAll(info, switchBtn);
        box.setPadding(new Insets(10,10,10,10));
        getInfoPane().getChildren().addAll(box);



        getAddNewLightBtn().setOnAction(e -> {
            Dialog<ArrayList<Object>> dialog = new Dialog<>();
            dialog.setTitle("Set a new light");

            ButtonType addNewLight = new ButtonType("Add New Light", ButtonBar.ButtonData.OK_DONE);
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


            JFXCheckBox SymmetricalRayCheckBox = new JFXCheckBox("Symmetrical Ray");
            SymmetricalRayCheckBox.setSelected(false);

            gridPane.add(new Label("Light Name:"), 0, 0);
            gridPane.add(lightName, 1, 0);
            gridPane.add(new Label("Light Position"), 0, 1);
            gridPane.add(positionSlider, 1, 1);
            gridPane.add(new Label("Light Direction"), 0, 2);
            gridPane.add(directionSlider, 1, 2);
            gridPane.add(new Label("Light Color"), 0, 4);
            gridPane.add(new Label("Symmetrical Ray"), 0, 5);
            gridPane.add(SymmetricalRayCheckBox, 0, 6);

            Node addNewLightBtn = dialog.getDialogPane().lookupButton(addNewLight);
            addNewLightBtn.setDisable(true);

            lightName.textProperty().addListener((o, ov, nv) ->
                    addNewLightBtn.setDisable(nv.trim().isEmpty() || getAnimatedLightMap().containsKey(nv)));

            dialog.getDialogPane().setContent(gridPane);

            Platform.runLater(lightName::requestFocus);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addNewLight) {
                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(lightName.getText());
                    arr.add(positionSlider.getValue());
                    arr.add(directionSlider.getValue());
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

                System.out.println("Symmetrical Light: " + lightInfo.get(3));

                String name = (String) lightInfo.get(0);
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(lightInfo.get(1));
                temp.add(lightInfo.get(2));
                temp.add(LightInfo.RED);
                temp.add(lightInfo.get(3));
                addAnimatedLight(name, new ArrayList<>(temp), true);
                temp.set(2, LightInfo.GREEN);
                addAnimatedLight(name + "[2]", new ArrayList<>(temp), false);
                temp.set(2, LightInfo.BLUE);
                addAnimatedLight(name + "[3]", new ArrayList<>(temp), false);
                getLightSelector().getSelectionModel().select(name);
            });

        });

        getController().getLightColor_Blue().setDisable(true);
        getController().getLightColor_Red().setDisable(true);
        getController().getLightColor_Green().setDisable(true);

        getLightDirectionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!getCurrentSelectedLight().equals("") && getAnimatedLightMap().containsKey(getCurrentSelectedLight())) {
                getAnimatedLightMap().get(getCurrentSelectedLight()).set(1, t1);
                getAnimatedLightMap().get(getCurrentSelectedLight() + "[2]").set(1, t1);
                getAnimatedLightMap().get(getCurrentSelectedLight() + "[3]").set(1, t1);
            }
        });

        getLightPositionSlider().valueProperty().addListener((observableValue, number, t1) -> {
            if (!getCurrentSelectedLight().equals("") && getAnimatedLightMap().containsKey(getCurrentSelectedLight())) {
                getAnimatedLightMap().get(getCurrentSelectedLight()).set(0, t1);
                getAnimatedLightMap().get(getCurrentSelectedLight() + "[2]").set(0, t1);
                getAnimatedLightMap().get(getCurrentSelectedLight() + "[3]").set(0, t1);
            }
        });

        getSymmetricalRayBtn().selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (getCurrentSelectedLight().equals("")) return;

            getAnimatedLightMap().get(getCurrentSelectedLight()).set(3, t1);
            getAnimatedLightMap().get(getCurrentSelectedLight() + "[2]").set(3, t1);
            getAnimatedLightMap().get(getCurrentSelectedLight() + "[3]").set(3, t1);

        });

        getDeleteLightBtn().setOnAction(e -> {
            String light = getCurrentSelectedLight();
            removeAnimatedComponent(ComponentType.LIGHT, light);
            removeAnimatedComponent(ComponentType.LIGHT, light + "[2]");
            removeAnimatedComponent(ComponentType.LIGHT, light + "[3]");
        });


    }
}

package MainProgram.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.ComponentType;
import Framework.LSD.world.Lens.LensMaterial;
import Framework.LSD.world.Lens.LensType;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * Class FreeMode is created on 19/08/2019 19:21.
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class FreeMode extends DemoView {

    @Override
    public void launch() {

        setTopMenuColor("#009ACD");
        setDemoTitle("Free Mode");
        SetEnableLensRadius(true);

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        JFXButton addNewLensBtn = new JFXButton("Add New Lens");
        JFXButton deleteThisLensBtn = new JFXButton("Delete This Lens");

        addNewLensBtn.setOnAction(e -> {
            Dialog<ArrayList<Object>> dialog = new Dialog<>();
            dialog.setTitle("Set a new Lens");

            ButtonType addNewLens = new ButtonType("Add New Lens", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addNewLens, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10, 10, 40, 10));

            TextField lensName = new TextField();
            lensName.setPromptText("Lens Name");

            JFXRadioButton ConvexLensRadioButton = new JFXRadioButton("Convex Lens");
            JFXRadioButton ConcaveLensRadioButton = new JFXRadioButton("Concave Lens");

            ToggleGroup toggleGroup = new ToggleGroup();

            ConvexLensRadioButton.setToggleGroup(toggleGroup);
            ConvexLensRadioButton.setUserData(LensType.ConvexLens);
            ConvexLensRadioButton.setSelected(true);
            ConcaveLensRadioButton.setToggleGroup(toggleGroup);
            ConcaveLensRadioButton.setUserData(LensType.ConcaveLens);

            JFXSlider positionSlider = new JFXSlider();
            positionSlider.setMin(100);
            positionSlider.setMax(600);
            positionSlider.setValue(300);

            JFXSlider heightSlider = new JFXSlider();
            heightSlider.setMax(600);
            heightSlider.setMin(100);
            heightSlider.setValue(200);

            JFXSlider leftRadiusSlider = new JFXSlider();
            leftRadiusSlider.setMax(2000);
            leftRadiusSlider.minProperty().bind(heightSlider.valueProperty().divide(2).add(30));
            leftRadiusSlider.setValue(350);

            JFXSlider rightRadiusSlider = new JFXSlider();
            rightRadiusSlider.setMax(2000);
            rightRadiusSlider.minProperty().bind(heightSlider.valueProperty().divide(2).add(30));
            rightRadiusSlider.setValue(350);

            JFXSlider selectMaterialSlider = new JFXSlider();
            selectMaterialSlider.setMax(247);
            selectMaterialSlider.setMin(1);
            selectMaterialSlider.setValue(1);

//            JFXButton selectMaterialBtn = new JFXButton();
//            selectMaterialBtn.setText("Select Lens Material");
//            selectMaterialBtn.setOnAction(event -> {
//                Dialog<LensMaterial> lensMaterialDialog = new Dialog<>();
//                lensMaterialDialog.setTitle("Chose Lens Material");
//
//                ButtonType chose = new ButtonType("Chose", ButtonBar.ButtonData.OK_DONE);
//                lensMaterialDialog.getDialogPane().getButtonTypes().addAll(chose, ButtonType.CANCEL);
//
//                GridPane materialGridPane = new GridPane();
//                materialGridPane.setHgap(10);
//                materialGridPane.setVgap(10);
//                materialGridPane.setPadding(new Insets(20, 10, 10, 10));
//
//                JFXListView<String> materialList = new JFXListView<>();
//                materialList.setMinWidth(350);
//
//                HashMap<String, LensMaterial> materialMap = new HashMap<>();
//                for (LensMaterial l :
//                        LensMaterial.values()) {
//                    String listCellString = String.format(l.getSequenceNumber() + " %-12s", l.toString())
//                            + String.format("%-20s", "[LensCode]: " + l.getLensCode())
//                            + "[Vd]: " + String.format("%.5f", l.getVd());
//                    materialList.getItems().add(listCellString);
//                    materialMap.put(listCellString, l);
//                }
//
//                materialGridPane.add(new Label("Material List:"), 0, 0);
//                materialGridPane.add(materialList, 0, 1);
//
//                Node choseBtn = lensMaterialDialog.getDialogPane().lookupButton(chose);
//                choseBtn.setDisable(true);
//
//                materialList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) ->
//                        choseBtn.setDisable(t1 == null || t1.isEmpty()));
//
//                lensMaterialDialog.getDialogPane().setContent(gridPane);
//
//                lensMaterialDialog.setResultConverter(dialogBtn -> {
//                    if (dialogBtn == chose)
//                        return materialMap.get(materialList.getSelectionModel().getSelectedItem());
//                    return null;
//                });
//
//                Optional<LensMaterial> result = lensMaterialDialog.showAndWait();
//
//                result.ifPresent(selectMaterialBtn::setUserData);
//
//            });

            JFXSlider minWidthSlider = new JFXSlider();
            minWidthSlider.setMax(100);
            minWidthSlider.setMin(10);
            minWidthSlider.setValue(25);

            gridPane.add(new Label("Lens Name: "), 0, 0);
            gridPane.add(lensName, 1, 0);
            gridPane.add(new Label("Lens Type: "), 0, 1);
            gridPane.add(ConvexLensRadioButton, 0, 2);
            gridPane.add(ConcaveLensRadioButton, 0, 3);
            gridPane.add(new Label("Position: "), 0, 4);
            gridPane.add(positionSlider, 1, 4);
            gridPane.add(new Label("Left Radius: "), 0, 5);
            gridPane.add(leftRadiusSlider, 1, 5);
            gridPane.add(new Label("Right Radius: "), 0, 6);
            gridPane.add(rightRadiusSlider, 1, 6);
            gridPane.add(new Label("Height: "), 0, 7);
            gridPane.add(heightSlider, 1, 7);
            gridPane.add(new Label("Lens Material: "), 0, 8);
            gridPane.add(selectMaterialSlider, 1, 8);

//            selectMaterialBtn.setUserData(LensMaterial.H_K10);

            toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
                if (t1.getUserData().equals(LensType.ConcaveLens)) {
                    gridPane.add(new Label("Min Width: "), 0, 9);
                    gridPane.add(minWidthSlider, 1, 9);
                } else {
                    gridPane.getChildren().remove(gridPane.getChildren().size() - 1);
                    gridPane.getChildren().remove(gridPane.getChildren().size() - 1);
                }
            });

            Node addNewLensBtnNode = dialog.getDialogPane().lookupButton(addNewLens);
            addNewLensBtnNode.setDisable(true);

            lensName.textProperty().addListener((observableValue, s, t1) ->
                    addNewLensBtnNode.setDisable(t1.trim().isEmpty() || getAnimatedLensMap().containsKey(t1)));

            dialog.getDialogPane().setContent(gridPane);

            dialog.getDialogPane().minHeightProperty().bind(gridPane.heightProperty());

            Platform.runLater(lensName::requestFocus);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addNewLens) {
                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(lensName.getText());
                    arr.add(toggleGroup.getSelectedToggle().getUserData());
                    arr.add(positionSlider.getValue());
                    arr.add(leftRadiusSlider.getValue());
                    arr.add(rightRadiusSlider.getValue());
                    arr.add(heightSlider.getValue());
                    arr.add(LensMaterial.findViaLensID((int) selectMaterialSlider.getValue()));
                    if (toggleGroup.getSelectedToggle().getUserData().equals(LensType.ConcaveLens))
                        arr.add(minWidthSlider.getValue());
                    return arr;
                }
                return null;
            });

            Optional<ArrayList<Object>> result = dialog.showAndWait();

            result.ifPresent(lensInfo -> {
                //TODO it is able to optimize, for now it is clear to read
                String name = (String) lensInfo.get(0);
                System.out.println("lens name: " + name);
                LensType lensType = (LensType) lensInfo.get(1);
                System.out.println("lensType: " + lensType);
                double lensPosition = (double) lensInfo.get(2);
                System.out.println("lens position: " + lensPosition);
                double lensLeftRadius = (double) lensInfo.get(3);
                System.out.println("lens left radius: " + lensLeftRadius);
                double lensRightRadius = (double) lensInfo.get(4);
                System.out.println("lens right radius: " + lensRightRadius);
                double lensHeight = (double) lensInfo.get(5);
                System.out.println("lens height: " + lensHeight);
                LensMaterial lensMaterial = (LensMaterial) lensInfo.get(6);
                System.out.println("lens material: " + lensMaterial);

                ArrayList<Object> temp = new ArrayList<>(Arrays.asList(
                        lensType, lensPosition, lensLeftRadius, lensRightRadius, lensHeight, lensMaterial
                ));

                if (lensType.equals(LensType.ConcaveLens)) {
                    double lensMinWidth = (double) lensInfo.get(7);
                    System.out.println("lens min width: " + lensMinWidth);
                    temp.add(lensMinWidth);
                }
                addAnimatedLens(name, new ArrayList<>(temp), true);
                getLensSelector().getSelectionModel().select(name);
            });
        });

        deleteThisLensBtn.setOnAction(e -> {
            if (!getCurrentSelectedLens().equals("") && getAnimatedLensMap().containsKey(getCurrentSelectedLens())) {
                removeAnimatedComponent(ComponentType.LENS, getCurrentSelectedLens());
                setCurrentSelectedLens("");
            }
        });

        Label info = new Label();
        info.setWrapText(true);
        info.setMaxWidth(200);
        info.setText("Try to add your own lens!!\n");
        info.setStyle("-fx-font-size: 20");

        Label info2 = new Label();
        info2.setWrapText(true);
        info2.setMaxWidth(200);
        info2.setText("(Note: The lenses are allowed overlap here, but it does not represent the real world condition)");
        info2.setStyle("-fx-font-size: 15");

        addNewLensBtn.setPrefWidth(200);
        addNewLensBtn.setPrefHeight(50);
        String addNewLensBtnDefaultStyle = "-fx-background-color: #77ff6f;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20";
        addNewLensBtn.setStyle(addNewLensBtnDefaultStyle);
        addNewLensBtn.hoverProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                addNewLensBtn.setStyle("-fx-background-color: #1aa40d;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20");
            } else {
                addNewLensBtn.setStyle(addNewLensBtnDefaultStyle);
            }
        });

        deleteThisLensBtn.setPrefWidth(200);
        deleteThisLensBtn.setPrefHeight(50);
        String deleteThisLensBtnDefaultStyle = "-fx-background-color: #ff8a80;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 20";
        deleteThisLensBtn.setStyle(deleteThisLensBtnDefaultStyle);
        deleteThisLensBtn.hoverProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                deleteThisLensBtn.setStyle("-fx-background-color: #ff3c3c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20");
            } else {
                deleteThisLensBtn.setStyle(deleteThisLensBtnDefaultStyle);
            }
        });

        box.getChildren().addAll(info, info2, addNewLensBtn, deleteThisLensBtn);
        box.setPadding(new Insets(10, 10, 10, 10));
        getInfoPane().getChildren().addAll(box);


    }


}

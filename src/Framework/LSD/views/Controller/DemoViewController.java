package Framework.LSD.views.Controller;

import Framework.LSD.world.Light.LightInfo;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class DemoViewController is created on 11/08/2019 14:40.
 * Controller of the DemoView
 *
 * @author Ray
 * @version 11/08/2019
 **/

public class DemoViewController implements Initializable {

    @FXML
    private BorderPane demoPane;

    @FXML
    private HBox TopMenu;

    @FXML
    private Label DemoName;

    @FXML
    private JFXComboBox<String> JFxLensSelector;

    @FXML
    private Label LensCode;

    @FXML
    private Label nD;

    @FXML
    private Label nF;

    @FXML
    private Label nC;

    @FXML
    private Label Vd;

    @FXML
    private Label nF2;

    @FXML
    private Label nC2;

    @FXML
    private Label Vd2;

    @FXML
    private HBox BottomMenu;

    @FXML
    private JFXComboBox<String> JFxLightSelector;

    @FXML
    private Label waveLength;


    @FXML
    private JFXButton addNewLight;

    @FXML
    private JFXButton deleteThisLight;

    @FXML
    private JFXRadioButton lightColor_Red;

    @FXML
    private JFXRadioButton lightColor_Green;

    @FXML
    private JFXRadioButton lightColor_Blue;


    @FXML
    private AnchorPane lensControllerPane;

    @FXML
    private AnchorPane mainDemoPane;

    @FXML
    private AnchorPane lightControllerPane;

    @FXML
    private AnchorPane infoPane;

    @FXML
    private JFXSlider lensPositionSlider;

    @FXML
    private JFXSlider lightPositionSlider;

    @FXML
    private JFXSlider lightDirectionSlider;

    @FXML
    private JFXButton choseLensMaterialBtn;

    @FXML
    private JFXButton lightDirectionReset;

    @FXML
    private JFXToggleButton SymmetricalRayToggleBtn;

    @FXML
    private JFXButton deselectLightBtn;


    private ToggleGroup lightColor;


    public DemoViewController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        JFxLensSelector.getItems().add("H-K10");

        Label RED = new Label("RED");
        RED.setTextFill(Color.RED);
        Label GREEN = new Label("GREEN");
        RED.setTextFill(Color.GREEN);
        Label BLUE = new Label("BLUE");
        RED.setTextFill(Color.BLUE);

        lightColor = new ToggleGroup();

        lightColor_Red.setToggleGroup(lightColor);
        lightColor_Red.setSelected(true);
        lightColor_Red.setUserData(LightInfo.RED);

        lightColor_Green.setToggleGroup(lightColor);
        lightColor_Green.setUserData(LightInfo.GREEN);

        lightColor_Blue.setToggleGroup(lightColor);
        lightColor_Blue.setUserData(LightInfo.BLUE);

    }

    public Label getDemoName() {
        return DemoName;
    }

    public JFXComboBox<String> getJFxLensSelector() {
        return JFxLensSelector;
    }

    public Label getLensCode() {
        return LensCode;
    }

    public Label getnD() {
        return nD;
    }

    public Label getnF() {
        return nF;
    }

    public Label getnC() {
        return nC;
    }

    public Label getVd() {
        return Vd;
    }

    public Label getnF2() {
        return nF2;
    }

    public Label getnC2() {
        return nC2;
    }

    public Label getVd2() {
        return Vd2;
    }

    public JFXComboBox<String> getJFxLightSelector() {
        return JFxLightSelector;
    }

    public Label getWaveLength() {
        return waveLength;
    }

    public JFXSlider getLensPositionSlider() {
        return lensPositionSlider;
    }

    public JFXButton getAddNewLight() {
        return addNewLight;
    }

    public JFXButton getDeleteThisLight() {
        return deleteThisLight;
    }

    public AnchorPane getLensControllerPane() {
        return lensControllerPane;
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

    public JFXSlider getLightPositionSlider() {
        return lightPositionSlider;
    }

    public JFXSlider getLightDirectionSlider() {
        return lightDirectionSlider;
    }

    public JFXButton getChoseLensMaterialBtn() {
        return choseLensMaterialBtn;
    }

    public JFXButton getLightDirectionResetBtn() {
        return lightDirectionReset;
    }

    public JFXToggleButton getSymmetricalRayToggleBtn() {
        return SymmetricalRayToggleBtn;
    }

    public ToggleGroup getLightColorSelector() {
        return lightColor;
    }

    public BorderPane getDemoPane() {
        return demoPane;
    }

    public HBox getTopMenu() {
        return TopMenu;
    }

    public HBox getBottomMenu() {
        return BottomMenu;
    }

    public JFXRadioButton getLightColor_Red() {
        return lightColor_Red;
    }

    public JFXRadioButton getLightColor_Green() {
        return lightColor_Green;
    }

    public JFXRadioButton getLightColor_Blue() {
        return lightColor_Blue;
    }

    public JFXButton getLightDirectionReset() {
        return lightDirectionReset;
    }

    public ToggleGroup getLightColor() {
        return lightColor;
    }

    public JFXButton getDeselectLightBtn() {
        return deselectLightBtn;
    }
}

package Framework.LSD.views.Controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class DemoViewController is created on 11/08/2019 14:40.
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
    private JFXComboBox<Label> JFxLensName;

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
    private JFXComboBox<Label> JFxLightSelector;

    @FXML
    private Label waveLength;

    @FXML
    private Label Frequency;

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


    private ToggleGroup lightColor;


    public DemoViewController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JFxLensName.getItems().add(new Label("H-K10"));

        Label RED = new Label("RED");
        RED.setTextFill(Color.RED);
        Label GREEN = new Label("GREEN");
        RED.setTextFill(Color.GREEN);
        Label BLUE = new Label("BLUE");
        RED.setTextFill(Color.BLUE);

        lightColor = new ToggleGroup();

        lightColor_Red.setToggleGroup(lightColor);
        lightColor_Red.setSelected(true);

        lightColor_Green.setToggleGroup(lightColor);

        lightColor_Blue.setToggleGroup(lightColor);

    }

    public Label getDemoName() {
        return DemoName;
    }

    public JFXComboBox<Label> getJFxLensName() {
        return JFxLensName;
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

    public JFXComboBox<Label> getJFxLightSelector() {
        return JFxLightSelector;
    }

    public Label getWaveLength() {
        return waveLength;
    }

    public JFXSlider getLensPositionSlider() {
        return lensPositionSlider;
    }

    public Label getFrequency() {
        return Frequency;
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
}

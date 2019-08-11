package Framework.LSD.Views.Controller;

import Framework.LSD.Views.DemoView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXRippler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


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
    private JFXRadioButton JFx_Red;

    @FXML
    private JFXRadioButton JFx_Green;

    @FXML
    private JFXRadioButton JFx_Blue;

    @FXML
    private Label waveLength;

    @FXML
    private Label Frequency;

    @FXML
    private JFXButton addNewLight;

    @FXML
    private JFXButton deleteThisLight;


    public DemoViewController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JFxLensName.getItems().add(new Label("H-K10"));


        JFxLightSelector.getItems().add(new Label("RED LIGHT"));
        JFxLightSelector.getItems().add(new Label("GREEN LIGHT"));
        JFxLightSelector.getItems().add(new Label("BLUE LIGHT"));

    }


}

package Framework.LSD.views.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class HomeViewController is created on 19/08/2019 02:49.
 * Controller of the HomeView
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class HomeViewController implements Initializable {

    @FXML
    private VBox chromaticAberrationTextBackground;

    @FXML
    private JFXButton chromaticAberrationBtn;

    @FXML
    private VBox ZoomingEffectTextBackground;

    @FXML
    private JFXButton zoomingEffectBtn;

    @FXML
    private VBox sphericalAberrationTextBackground;

    @FXML
    private JFXButton sphericalAberrationBtn;

    @FXML
    private VBox freeModeTextBackground;

    @FXML
    private JFXButton freeModeBtn;

    @FXML
    private Label descriptionTitle;

    @FXML
    private ImageView descriptionImage;

    @FXML
    private Label description;

    @FXML
    private VBox homeInfoPane;

    @FXML
    private ScrollPane homeInfoScrollPane;

    @FXML
    private JFXButton homeExitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public VBox getChromaticAberrationTextBackground() {
        return chromaticAberrationTextBackground;
    }

    public JFXButton getChromaticAberrationBtn() {
        return chromaticAberrationBtn;
    }

    public VBox getZoomingEffectTextBackground() {
        return ZoomingEffectTextBackground;
    }

    public JFXButton getZoomingEffectBtn() {
        return zoomingEffectBtn;
    }

    public VBox getSphericalAberrationTextBackground() {
        return sphericalAberrationTextBackground;
    }

    public JFXButton getSphericalAberrationBtn() {
        return sphericalAberrationBtn;
    }

    public VBox getFreeModeTextBackground() {
        return freeModeTextBackground;
    }

    public JFXButton getFreeModeBtn() {
        return freeModeBtn;
    }

    public Label getDescriptionTitle() {
        return descriptionTitle;
    }

    public ImageView getDescriptionImage() {
        return descriptionImage;
    }

    public Label getDescription() {
        return description;
    }

    public VBox getHomeInfoPane() {
        return homeInfoPane;
    }

    public ScrollPane getHomeInfoScrollPane() {
        return homeInfoScrollPane;
    }

    public JFXButton getHomeExitBtn() {
        return homeExitBtn;
    }
}

package Framework.LSD.Views;

import Framework.LSD.Views.Controller.DemoViewController;
import Framework.LSD.app.View;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

import static Framework.Framework.app;

/**
 * Class DemoView is created on 07/08/2019 23:55.
 *
 * @author Ray
 * @version 07/08/2019
 **/

public class DemoView extends View {

    private BorderPane demoPane;
    private DemoViewController demoViewController;

    @Override
    public void onLaunch() {
        getFxmlLoader().setLocation(getFxmlLoader()
                .getClassLoader()
                .getResource("Framework/LSD/Views/FXML/DemoView.fxml"));
        try {
            demoPane = getFxmlLoader().load();
            demoViewController = getFxmlLoader().getController();

            getPane().prefWidthProperty().bind(app.widthProperty());
            getPane().prefHeightProperty().bind(app.heightProperty());

            demoPane.prefWidthProperty().bind(app.widthProperty());
            demoPane.prefHeightProperty().bind(app.heightProperty());

            getPane().getChildren().add(demoPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception in " + getClass().getName());
        }

    }


    public BorderPane getDemoPane() {
        return demoPane;
    }

    public DemoViewController getDemoViewController() {
        return demoViewController;
    }
}

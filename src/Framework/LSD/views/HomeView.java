package Framework.LSD.views;

import Framework.LSD.app.View;
import Framework.LSD.views.Controller.HomeViewController;
import javafx.scene.layout.HBox;

import static Framework.Framework.app;

import java.io.IOException;

/**
 * Class HomeView is created on 19/08/2019 02:48.
 *
 * @author Ray
 * @version 19/08/2019
 **/

public abstract class HomeView extends View {

    private HBox homePane;

    private HomeViewController homeViewController;

    @Override
    public void onLaunch() {
        getFxmlLoader().setLocation(getFxmlLoader()
                .getClassLoader()
                .getResource("Framework/LSD/views/FXML/HomeView.fxml"));
        try {
            homePane = getFxmlLoader().load();
            homeViewController = getFxmlLoader().getController();
            getPane().getChildren().add(homePane);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception in " + getClass().getName());
        }

        getPane().prefWidthProperty().bind(app.widthProperty());
        getPane().prefHeightProperty().bind(app.heightProperty());

        homePane.prefWidthProperty().bind(app.widthProperty());
        homePane.prefHeightProperty().bind(app.heightProperty());

        launch();

    }

    public abstract void launch();


    public HomeViewController getController() {
        return homeViewController;
    }

    public void gotoView(String viewName) {
        app.gotoView(viewName);
    }


}

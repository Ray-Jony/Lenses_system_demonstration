package Framework.LSD.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

public abstract class View {

    private final Pane pane;
    private final FXMLLoader fxmlLoader;

    public View() {
        pane = new AnchorPane();
        pane.setBackground(Background.EMPTY);
        fxmlLoader = new FXMLLoader();
    }

    public Pane getPane() {
        return pane;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public abstract void onLaunch();

    public void onEnter() {
        //Subclass request
    }

    public void onStart() {
        //Subclass request
    }

    public void onUpdate(double time) {
        //Subclass request
    }

    public void onStop() {
        //Subclass request
    }

    public void onLeave() {
        //Subclass request
    }

    public void onFinish() {
        //Subclass request
    }

}

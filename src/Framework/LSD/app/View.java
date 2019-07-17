package Framework.LSD.app;

import javafx.scene.layout.*;

public abstract class View {

    private final Pane pane;

    public View() {
        pane = new AnchorPane();
        pane.setBackground(Background.EMPTY);
    }

    public Pane getPane() {
        return pane;
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

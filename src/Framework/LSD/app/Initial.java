package Framework.LSD.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Abstract Class Initial extends from javafx.Application with a start method to initialize the stage of program
 * Give a temple for the Lens demo application
 */

public abstract class Initial extends Application {

    private App app;

    public abstract void onLaunch();

    public void onFinish() {
        //Subclass reference
    }

    public boolean onExit() {
        //Subclass reference
        return true;
    }

    @Override
    public final void start(Stage stage) throws Exception {
        app = new App(stage);
        app.onLaunch = this::onLaunch;
        app.onFinish = this::onFinish;
        app.onExit = this::onExit;

        app.launch();
    }

    @Override
    public final void stop() throws Exception {
        app.finish();
    }
}

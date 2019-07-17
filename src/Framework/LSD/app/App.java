package Framework.LSD.app;

import Framework.LSD.input.MouseInput;
import Framework.LSD.world.Light;
import Framework.LSD.world.LightPath;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Framework.LSD.Framework;
import Framework.LSD.input.KeyInput;

import java.util.HashMap;

public class App {

    private final Stage stage;
    private final Scene scene;
    private final Pane root;

    private final HashMap<String, View> viewMap;
    private final ObjectProperty<View> currentView;

    private final HashMap<String, Light> lightMap;

    private final Engine engine;

    private final KeyInput keyinput;

    private final MouseInput mouseInput;

    OnLaunch onLaunch;
    OnFinish onFinish;
    OnExit onExit;

    public App(Stage stage) {
        this.stage = stage;

        root = new StackPane();
        scene = new Scene(root);
        stage.setScene(scene);

        viewMap = new HashMap<>();
        currentView = new SimpleObjectProperty<>();

        lightMap = new HashMap<>();

        engine = new Engine();

        keyinput = new KeyInput();

        mouseInput = new MouseInput();

        initFramework();
        initApp();
        initEngine();
    }

    private void initFramework() {
        Framework.app = this;
        Framework.engine = engine;
        Framework.keyinput = keyinput;
        Framework.mouseInput = mouseInput;
    }

    private void initApp() {
        scene.setFill(Color.WHITE);
        root.setBackground(Background.EMPTY);

        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            if (onExit != null && !onExit.handle()) {
                event.consume();
            }
        });

        currentView.addListener((o, ov, nv) -> {
            if (ov != null) {
                ov.onLeave();
                root.getChildren().remove(ov.getPane());
            }

            if (nv != null) {
                root.getChildren().add(nv.getPane());
                nv.onEnter();
            }
        });
    }

    private void initEngine() {
        engine.onStart = () -> {
            for (View v :
                    viewMap.values()) {
                v.onStart();
            }

            keyinput.install(stage);
            mouseInput.install(stage);
        };

        engine.onUpdate = t -> {
            View view = getCurrentView();

            if (view != null) {
                view.onUpdate(t);
            }

            keyinput.refresh();
            mouseInput.refresh();
        };

        engine.onStop = () -> {
            mouseInput.uninstall(stage);
            keyinput.uninstall(stage);
            for (View v :
                    viewMap.values()) {
                v.onStop();
            }
        };

        stage.focusedProperty().addListener((o, ov, nv) -> {
            if (nv) {
                engine.start();
            } else {
                engine.stop();
            }
        });
    }

    public void drawLight(Pane pane) {
        pane.getChildren().removeAll();
        for (Light l :
                lightMap.values()) {
            l.drawLight(pane);
        }
    }

    public void intersectionDetect(){
        for (Light l :
                lightMap.values()) {
            l.intersectionDetect();
        }
    }

    public void regLight(String name, double startPointX, double startPointY, double direction, double waveLength) {
        lightMap.put(name, new Light(new LightPath(startPointX,startPointY,direction,waveLength)));
    }

    public void unregLight(String name) {
        lightMap.remove(name);
    }

    public boolean intersectionPoint(){
        //TODO
        return true;
    }


    public void regView(String name, View view) {
        viewMap.put(name, view);
    }

    public void unregView(String name) {
        View view = viewMap.remove(name);

        if (view != null && view == getCurrentView()) {
            currentView.set(null);
        }
        //Should avoid delete currentView,
        //otherwise the window will display nothing

    }

    public View getCurrentView() {
        return currentView.get();
    }

    public ReadOnlyObjectProperty<View> currentViewProperty() {
        return currentView;
    }

    public void gotoView(String name) {
        View view = viewMap.get(name);
        if (view != null) {
            currentView.set(view);
        }
    }

    public View getView(String name) {
        return viewMap.get(name);
    }

    public Light getLight(String name) {
        return lightMap.get(name);
    }

    public void launch() {
        if (onLaunch != null) {
            onLaunch.handle();
        }

        for (View v :
                viewMap.values()) {
            v.onLaunch();
        }
        stage.requestFocus();
        stage.show();
    }

    public void finish() {
        for (View v :
                viewMap.values()) {
            v.onFinish();
        }

        if (onFinish != null) {
            onFinish.handle();
        }
    }

    public void exit() {
        Platform.exit();
    }

    interface OnLaunch {
        void handle();
    }


    interface OnFinish {
        void handle();
    }

    interface OnExit {
        boolean handle();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public String getTitle() {
        return stage.getTitle();
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public StringProperty titleProperty() {
        return stage.titleProperty();
    }

    public double getWidth() {
        return root.getMinWidth();
    }

    public void setWidth(double width) {
        root.setMinWidth(width);
    }

    public DoubleProperty widthProperty() {
        return root.minWidthProperty();
    }

    public double getHeight() {
        return root.getMinHeight();
    }

    public void setHeight(double height) {
        root.setMinHeight(height);
    }

    public DoubleProperty heightProperty() {
        return root.minHeightProperty();
    }
}
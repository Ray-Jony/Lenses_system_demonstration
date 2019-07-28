package test.view;

import Framework.LSD.app.View;
import Framework.LSD.input.Key;
import Framework.LSD.input.Mouse;
import Framework.LSD.world.Lens.CircleLens;
import Framework.LSD.world.Light.LightPath;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static Framework.LSD.Framework.*;

public class DemoView extends View {

    public static double direction = 0;

    private Pane demonstratePane;

    private Button homeBtn;
    private Button changeBtn;

    private Text angle;

    @Override
    public void onLaunch() {
        demonstratePane = new AnchorPane();
        demonstratePane.setLayoutX(10);
        demonstratePane.setLayoutY(50);

        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setLayoutX(10);
        homeBtn.setLayoutY(10);
        homeBtn.setOnAction(e ->
                app.gotoView("Home")
        );
        changeBtn = new Button();
        changeBtn.setText("change");
        changeBtn.setLayoutX(60);
        changeBtn.setLayoutY(10);
        changeBtn.setOnAction(e ->
                changeDirection()
        );

        angle = new Text(String.valueOf(direction));
        angle.setLayoutX(110);
        angle.setLayoutY(10);


        getPane().getChildren().addAll(demonstratePane, homeBtn, changeBtn,angle);

        app.regLight("RedLight", 200, 50, 0, LightPath.RED_LIGHT_WAVE_LENGTH);

        app.regMirror("RightEdge", new FlatMirror(400, 0, 800, 600));
        app.regMirror("LeftEdge", new FlatMirror(0, 0, 0, 600));
        app.regMirror("TopEdge", new FlatMirror(0, 0, 800, 0));
        app.regMirror("BottomEdge", new FlatMirror(0, 600, 800, 600));
//        app.regMirror("Circle", new CircleMirror(400, 300, 100));
        app.regLens("CircleLens", new CircleLens(400,300,100));

    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to DemoView");
    }

    @Override
    public void onUpdate(double time) {

        app.intersectionDetect();

        app.draw(demonstratePane);

        angle.setText(String.valueOf(direction));

        changeDirection();

//        System.out.println(app.getWidth() + "" + app.getHeight());

        if (keyinput.isPressed(Key.NUM0)) {
            System.out.println("Pressed 0");
        }

        if (keyinput.isReleased(Key.NUM0)) {
            System.out.println("Released 0");
        }

        if (keyinput.isHeld(Key.NUM1)) {
            System.out.println("Held 1");
        }

        if (keyinput.isTyped(Key.NUM2)) {
            System.out.println("Type 2:" + keyinput.getTypeCount(Key.NUM2));
        }

        if (mouseInput.isPressed(Mouse.LEFT)) {
            System.out.println("Left Pressed");
        }

        if (mouseInput.isReleased(Mouse.LEFT)) {
            System.out.println("Left Released");
        }

        if (mouseInput.isHeld(Mouse.RIGHT)) {
            System.out.println("Right Held");
        }

        if (mouseInput.isDragged(Mouse.LEFT)) {
            System.out.println("Left Dragged:" +
                    mouseInput.getDragX(Mouse.LEFT) + "," +
                    mouseInput.getDragY(Mouse.LEFT));
        }

        if (mouseInput.isClicked(Mouse.MIDDLE)) {
            System.out.println("Middle Clicked" +
                    mouseInput.getClickCount(Mouse.MIDDLE));
        }

        if (mouseInput.isScrolled()) {
            System.out.println("Scrolled" +
                    mouseInput.getScrollValue());
        }
    }

    public void changeDirection() {
        app.unregLight("RedLight");
        app.regLight("RedLight", 200, 50, direction, LightPath.RED_LIGHT_WAVE_LENGTH);
        direction += 0.0005;
    }
}

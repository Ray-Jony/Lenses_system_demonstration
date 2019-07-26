package test.view;

import Framework.LSD.app.View;
import Framework.LSD.input.Key;
import Framework.LSD.input.Mouse;
import Framework.LSD.world.Light.LightPath;
import Framework.LSD.world.Mirror.CircleMirror;
import Framework.LSD.world.Mirror.FlatMirror;
import Framework.LSD.world.Mirror.Mirror;
import javafx.scene.control.Button;

import static Framework.LSD.Framework.*;
import static Framework.LSD.Framework.mouseInput;

public class DemoView extends View {

    public static double direction = 0;

    private Button homeBtn;


    @Override
    public void onLaunch() {
        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e ->
                app.gotoView("Home")
        );
        getPane().getChildren().add(homeBtn);

        app.regLight("RedLight", 200, 50, Math.PI / 8, LightPath.RED_LIGHT_WAVE_LENGTH);
        System.out.println(app.getHeight());
        app.regMirror("RightEdge", new FlatMirror(400, 0, 800, 600));
        app.regMirror("LeftEdge", new FlatMirror(0, 0, 0, 600));
        app.regMirror("TopEdge", new FlatMirror(0, 0, 800, 0));
        app.regMirror("BottomEdge", new FlatMirror(0, 600, 800, 600));
        app.regMirror("Circle", new CircleMirror(400,300,100));


    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to DemoView");
    }

    @Override
    public void onUpdate(double time) {

        app.intersectionDetect();
        app.draw(getPane());
//        app.drawLight(getPane());
//        app.drawMirror(getPane());

//        changeDirection();
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
        direction += 0.001;
    }
}

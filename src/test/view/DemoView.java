package test.view;

import Framework.LSD.app.View;
import Framework.LSD.input.Key;
import Framework.LSD.input.Mouse;
import Framework.LSD.world.LightPath;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import static Framework.LSD.Framework.*;
import static Framework.LSD.Framework.mouseInput;

public class DemoView extends View {

    private Button homeBtn;


    @Override
    public void onLaunch() {
        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e ->
                app.gotoView("Home")
        );

        app.regLight("RedLight", 200, 50, -Math.PI / 6, LightPath.RED_LIGHT_WAVE_LENGTH);


        getPane().getChildren().add(homeBtn);
    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to DemoView");
//        app.getLight("RedLight").addLightPath(200,-Math.PI/3);

    }

    @Override
    public void onUpdate(double time) {

        app.intersectionDetect();
        app.drawLight(getPane());




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
}

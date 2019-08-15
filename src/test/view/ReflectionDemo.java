package test.view;

import Framework.LSD.app.View;
import Framework.LSD.input.Key;
import Framework.LSD.input.Mouse;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Light.LightPath;
import Framework.LSD.world.Mirror.FlatMirror;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static Framework.Framework.*;

public class ReflectionDemo extends View {

    private double direction = 0;
    private double speed = 0;
    private double tempSpeed = 0;

    private Pane demonstratePane;

    private Button homeBtn;
    private Button controlBtn;
    private Button speedUpBtn;
    private Button speedDownBtn;

    private Text angleText;
    private Text speedText;

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

        controlBtn = new Button();
        controlBtn.setText("Start");
        controlBtn.setLayoutX(70);
        controlBtn.setLayoutY(10);
        controlBtn.setOnAction(e -> {
                    if (controlBtn.getText().equals("Start")) {
                        controlBtn.setText("Pause");
                        speed = tempSpeed;
                    } else {
                        controlBtn.setText("Start");
                        tempSpeed = speed;
                        speed = 0;
                    }
                }
        );

        speedUpBtn = new Button();
        speedUpBtn.setText("+++");
        speedUpBtn.setLayoutX(130);
        speedUpBtn.setLayoutY(10);
        speedUpBtn.setOnAction(e -> {
                    tempSpeed += 0.0005;
                    if (controlBtn.getText().equals("Pause")) {
                        speed = tempSpeed;
                    }
                }
        );

        speedDownBtn = new Button();
        speedDownBtn.setText("---");
        speedDownBtn.setLayoutX(190);
        speedDownBtn.setLayoutY(10);
        speedDownBtn.setOnAction(e -> {
                    tempSpeed -= 0.0005;
                    if (controlBtn.getText().equals("Pause")) {
                        speed = tempSpeed;
                    }
                }
        );

        angleText = new Text(String.valueOf(Math.toDegrees(direction)));
        angleText.setLayoutX(10);
        angleText.setLayoutY(50);

        speedText = new Text(String.valueOf(tempSpeed));
        speedText.setLayoutX(250);
        speedText.setLayoutY(30);


        getPane().getChildren().addAll(
                demonstratePane,
                homeBtn,
                controlBtn,
                speedUpBtn,
                speedDownBtn,
                angleText,
                speedText);

    }

    @Override
    public void onEnter() {
        app.reset();

        app.regLight("RedLight", 200, 50, 0, LightInfo.GREEN);

        app.regMirror("RightEdge", new FlatMirror(400, 0, 800, 600,false));
        app.regMirror("LeftEdge", new FlatMirror(100, 0, 0, 600,false));
        app.regMirror("TopEdge", new FlatMirror(0, 0, 800, 0,false));
        app.regMirror("BottomEdge", new FlatMirror(0, 600, 800, 600,false));
//        app.regMirror("Circle", new CircleMirror(400, 300, 100));
    }

    @Override
    public void onUpdate(double time) {

        app.intersectionDetect();

        app.draw(demonstratePane);

        angleText.setText(String.valueOf(direction));
        speedText.setText(String.valueOf(tempSpeed));

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
        app.regLight("RedLight", 200, 50, direction, LightInfo.GREEN);
        direction += speed;
    }
}

package test.view;

import static Framework.LSD.Framework.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;
import Framework.LSD.input.Key;


public class HomeView extends View {


    private Button playBtn;
    private Button exitBtn;

    @Override
    public void onLaunch() {
        playBtn = new Button("Play");
        playBtn.setOnAction(e -> app.gotoView("Play"));

        exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> app.exit());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(playBtn, exitBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        getPane().getChildren().add(vBox);
    }

    @Override
    public void onUpdate(double time) {
        if (keyinput.isPressed(Key.NUM0)){
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
    }
}

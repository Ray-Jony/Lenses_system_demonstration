package test.view;

import static Framework.LSD.Framework.*;

import Framework.LSD.input.Mouse;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;
import Framework.LSD.input.Key;


public class HomeView extends View {


    private Button playBtn;
    private Button exitBtn;
    private Button demoBtn;

    @Override
    public void onLaunch() {
        playBtn = new Button("Play");
        playBtn.setOnAction(e -> app.gotoView("Play"));

        demoBtn = new Button("Demo");
        demoBtn.setOnAction(e -> app.gotoView("Demo"));

        exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> app.exit());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(demoBtn, exitBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        getPane().getChildren().add(vBox);
    }

    @Override
    public void onEnter() {
        System.out.println(app.getMinWidth() + " " + app.getMinHeight());
    }

    @Override
    public void onUpdate(double time) {

//        System.out.println("Mouse position:" + mouseInput.getPointX() +", "+ mouseInput.getPointY());

    }
}

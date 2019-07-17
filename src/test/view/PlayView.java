package test.view;

import static Framework.LSD.Framework.*;

import Framework.LSD.input.Mouse;
import Framework.LSD.input.MouseInput;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;
import javafx.scene.text.Text;

public class PlayView extends View {

    private Button homeBtn;
    private Button dragMe;
    private Text text;


    @Override
    public void onLaunch() {
        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e -> {
            app.gotoView("Home");
            text.setText("Hey Let's Play a Game!");
        });

        dragMe = new Button("Click Me");
        dragMe.setOnAction(e -> text.setText("You Win!"));

        text = new Text("Hey Let's Play a Game!");

//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(homeBtn);
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(20);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(homeBtn);
        anchorPane.getChildren().add(dragMe);
        anchorPane.getChildren().add(text);

        homeBtn.setLayoutX(10);
        homeBtn.setLayoutY(10);

        dragMe.setLayoutX(10);
        dragMe.setLayoutY(20);

        text.setLayoutX(10);
        text.setLayoutY(60);

        getPane().getChildren().add(anchorPane);
    }

    @Override
    public void onEnter() {
        System.out.println("Welcome to PlayView");
    }

    @Override
    public void onUpdate(double time) {
        dragMe.setLayoutX(mouseInput.getPointX() + 10);
        dragMe.setLayoutY(mouseInput.getPointY() + 10);
    }
}

package test.view;
import static Framework.LSD.Framework.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import Framework.LSD.app.View;

public class PlayView extends View {

    private Button homeBtn;

    @Override
    public void onLaunch() {
        homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e -> app.gotoView("Home"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(homeBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        getPane().getChildren().add(vBox);
    }
}

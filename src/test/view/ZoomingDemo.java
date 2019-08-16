package test.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Light.LightPath;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class controllerTestView is created on 09/08/2019 16:41.
 *
 * @author Ray
 * @version 09/08/2019
 **/

public class ZoomingDemo extends DemoView {


    @Override
    public void onLaunch() {
        super.onLaunch();

        //Set title of the demonstration

        setDemoTitle("Zooming");
        getLightDirectionSlider().setValue(0);

        getLightDirectionResetBtn().setOnAction(e -> {
            getLightDirectionSlider().setValue(0);
        });


    }

    @Override
    public void onEnter() {
        super.onEnter();
//        System.out.println(getLensControl().widthProperty().doubleValue());

    }

    @Override
    public void onUpdate(double time) {

        for (String key :
                getAnimatedLightMap().keySet()) {
            ArrayList info = getAnimatedLightMap().get(key);
            animatedLight(key, (double) info.get(0), (double) info.get(1), (LightInfo) info.get(2));
        }


//        animatedLight("BlueLight", getLightPositionSlider().getValue(),
//                -Math.PI * (getLightDirectionSlider().getValue() / 180), LightInfo.BLUE);
        animatedConvexLens("ConvexLens1", getLensPositionSlider().getValue(),
                250, 250, 200);
        animatedBoard("TopBoard", 0, 5,
                getMainDemoPane().getWidth(), 5);
        animatedBoard("BottomBoard", 0, getMainDemoPane().getHeight() - 20,
                3000, getMainDemoPane().getHeight() - 20);

        //************[Above] add animated light/lens******************/
        intersectionDetect();
        drawMainDemoPane();
        //************[Below] add others*******************************/

        addStandardLine();

        getLensPositionSlider().setMax(getMainDemoPane().getWidth() - 30);
        getLensPositionSlider().setMin(30);

        getLightPositionSlider().setMax((int) (getMainDemoPane().getHeight() / 2 - 25));
        getLightPositionSlider().setMin(-(int) ((getMainDemoPane().getHeight() / 2 - 25)));


        //************[Below] initialization****************************/

    }

}

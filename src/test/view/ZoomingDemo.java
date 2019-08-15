package test.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.Lens.ConvexLens;
import Framework.LSD.world.Light.LightInfo;
import Framework.LSD.world.Light.LightPath;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Pair;

/**
 * Class controllerTestView is created on 09/08/2019 16:41.
 *
 * @author Ray
 * @version 09/08/2019
 **/

public class ZoomingDemo extends DemoView {

    private JFXSlider lensPositionController;

    private Line standardLine;

    private double mainDemoPaneWidth = 0;
    private double mainDemoPaneHeight = 0;


    @Override
    public void onLaunch() {
        super.onLaunch();

        //Set title of the demonstration

        setDemoTitle("Zooming");

        getController().getAddNewLight().setOnAction(e->{
            Dialog<Pair<Pair<String,Double>,Pair<Double, LightInfo>>> addNewLightDialog = new Dialog<>();



        });




    }

    @Override
    public void onEnter() {
        super.onEnter();
//        System.out.println(getLensControl().widthProperty().doubleValue());

    }

    @Override
    public void onUpdate(double time) {


        animatedLight("BlueLight", 50, 0, LightInfo.BLUE);
        animatedConvexLens("ConvexLens1", getLensPositionSlider().getValue(), 250, 250, 200);
        animatedBoard("TopBoard", 0, 0, mainDemoPaneWidth - 5, 0);
        animatedBoard("BottomBoard", 0, mainDemoPaneHeight - 50, 3000, mainDemoPaneHeight - 50);

        //************Above add animated light/lens******************/
        intersectionDetect();
        drawMainDemoPane();
        //************Below add others*******************************/

        addStandardLine();

        getLensPositionSlider().setMax(getMainDemoPane().getWidth() - 10);
        getLensPositionSlider().setMin(10);


        mainDemoPaneWidth = getMainDemoPane().getWidth();
        mainDemoPaneHeight = getMainDemoPane().getHeight();
//        System.out.println(mainDemoPaneWidth);
    }
}

package test.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.Lens.LensMaterial;
import Framework.LSD.world.Lens.LensType;
import Framework.LSD.world.Light.LightInfo;

import java.util.ArrayList;
import java.util.Arrays;


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

        addAnimatedLens("ConvexLens2", new ArrayList<>(Arrays.asList(
                LensType.ConvexLens, 50D, 250D, 250D, 200D, LensMaterial.H_K10
        )));

        getLensSelector().getItems().addAll(getAnimatedLensMap().keySet());

    }

    @Override
    public void onEnter() {
        super.onEnter();
//        System.out.println(getLensControl().widthProperty().doubleValue());

    }

    @Override
    public void onUpdate(double time) {
        super.onUpdate(time);


//        animatedLight("BlueLight", getLightPositionSlider().getValue(),
//                -Math.PI * (getLightDirectionSlider().getValue() / 180), LightInfo.BLUE);
//        animatedConvexLens("ConvexLens1", getLensPositionSlider().getValue(),
//                250, 250, 200, LensMaterial.H_K10);
        animatedBoard("TopBoard", 0, 5,
                3000, 5);
        animatedBoard("BottomBoard",
                0, getMainDemoPane().getHeight() - 20,
                3000, getMainDemoPane().getHeight() - 20);

        //************[Above] add animated light/lens******************/
        intersectionDetect();
        drawMainDemoPane();
        highLightLight(getCurrentSelectedLight());
        //************[Below] add others*******************************/

        addStandardLine();

        getLensPositionSlider().setMax(getMainDemoPane().getWidth() - 30);
        getLensPositionSlider().setMin(30);

        getLightPositionSlider().setMax((int) (getMainDemoPane().getHeight() / 2 - 25));
        getLightPositionSlider().setMin(-(int) ((getMainDemoPane().getHeight() / 2 - 25)));


        //************[Below] initialization****************************/

    }

}

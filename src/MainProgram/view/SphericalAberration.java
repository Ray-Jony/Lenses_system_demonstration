package MainProgram.view;

import Framework.LSD.views.DemoView;
import Framework.LSD.world.Lens.LensMaterial;
import Framework.LSD.world.Lens.LensType;
import Framework.LSD.world.Light.LightInfo;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class SphericalAberration is created on 19/08/2019 19:20.
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class SphericalAberration extends DemoView {
    @Override
    public void launch() {

        setTopMenuColor("FFA6B1");
        setDemoTitle("Spherical Aberration");
        getController().getDemoName().setFont(Font.font("Roboto Black", 30));

        addAnimatedLight("Symmetrical Light1", new ArrayList<>(Arrays.asList(
                20D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light2", new ArrayList<>(Arrays.asList(
                30D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light3", new ArrayList<>(Arrays.asList(
                40D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light4", new ArrayList<>(Arrays.asList(
                50D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light5", new ArrayList<>(Arrays.asList(
                60D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light6", new ArrayList<>(Arrays.asList(
                70D, 0D, LightInfo.BLUE, true
        )), true);
        addAnimatedLight("Symmetrical Light7", new ArrayList<>(Arrays.asList(
                80D, 0D, LightInfo.BLUE, true
        )), true);

        addAnimatedLens("ConvexLens1", new ArrayList<>(Arrays.asList(
                LensType.ConvexLens, 200D, 300D, 300D, 200D, LensMaterial.findViaLensID(247)
        )), true);

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10,10,10,10));

        Label info = new Label();
        info.setWrapText(true);
        info.setMaxWidth(200);
        info.setText("Try To change the material of the lens to see how the light convert point change");
        info.setStyle("-fx-font-size: 20");
        box.getChildren().add(info);

        getInfoPane().getChildren().add(box);

    }

}

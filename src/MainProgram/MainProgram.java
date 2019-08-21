package MainProgram;

import Framework.LSD.app.Initial;
import MainProgram.view.*;

import static Framework.Framework.app;

/**
 * Class MainProgram is created on 19/08/2019 02:38.
 * This is the main program entrance, extends Initial
 *
 * @author Ray
 * @version 19/08/2019
 **/

public class MainProgram extends Initial {

    @Override
    public void onLaunch() {
        app.setTitle("JavaFX Lenses System Demo");
        app.setWidth(1280);
        app.setHeight(800);

        app.regView("Home", new Home());
        app.regView("Chromatic Aberration", new ChromaticAberration());
        app.regView("Zooming Effect",new ZoomingEffect());
        app.regView("Spherical Aberration", new SphericalAberration());
        app.regView("Free Mode", new FreeMode());

        app.gotoView("Home");

    }

    @Override
    public void onFinish() {

    }

    @Override
    public boolean onExit() {
        return true;
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

}

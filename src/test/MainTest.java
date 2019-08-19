package test;

import Framework.LSD.app.Initial;
import test.view.*;

import static Framework.Framework.app;

/**
 * @author :
 * @version :
 */
public class MainTest extends Initial {


//    public static void main(String[] args) {
//    launch(args);
//}

    @Override
    public void onLaunch() {
        app.setTitle("Test");
        app.setWidth(1280);
        app.setHeight(800);

        app.regView("Home", new HomeView());
//        app.regView("Play", new PlayView());
        app.regView("Reflection", new ReflectionDemo());
        app.regView("Spherical Aberration", new SphericalAberrationDemo());
        app.regView("Chromatic Aberration", new ChromaticAberrationDemo());
        app.regView("Zooming", new ZoomingDemo());
//        app.gotoView("ZoomingDemo");
        app.gotoView("Home");

//        System.out.println(app.getHeight());
    }

    @Override
    public void onFinish() {
        System.out.println("Finish");
    }

    @Override
    public boolean onExit() {
        System.out.println("Exit");
        return true;
    }
}

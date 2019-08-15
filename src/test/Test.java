package test;

import static Framework.Framework.*;

import Framework.LSD.app.Initial;
import test.view.*;

public class Test extends Initial {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onLaunch() {
        app.setTitle("Test");
        app.setWidth(1280);
        app.setHeight(800);

        app.regView("Home", new HomeView());
//        app.regView("Play", new PlayView());
        app.regView("Demo", new ReflectionDemo());
        app.regView("LensDemo", new SphericalAberrationDemo());
        app.regView("ChromaticAberrationDemo", new ChromaticAberrationDemo());
        app.regView("ZoomingDemo", new ZoomingDemo());
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

package test;

import static Framework.LSD.Framework.*;

import Framework.LSD.app.Initial;
import test.view.HomeView;
import test.view.PlayView;
import test.view.DemoView;

public class Test extends Initial {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onLaunch() {
        app.setTitle("Test");
        app.setWidth(1000);
        app.setHeight(800);

        app.regView("Home", new HomeView());
//        app.regView("Play", new PlayView());
        app.regView("Demo", new DemoView());
        app.gotoView("Home");

        System.out.println(app.getHeight());
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

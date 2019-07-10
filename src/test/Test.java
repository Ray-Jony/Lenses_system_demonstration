package test;

import static Framework.LSD.Framework.*;

import Framework.LSD.app.Game;
import test.view.HomeView;
import test.view.PlayView;

public class Test extends Game {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onLaunch() {
        app.setTitle("Test game");
        app.setWidth(800);
        app.setHeight(600);

        app.regView("Home", new HomeView());
        app.regView("Play", new PlayView());
        app.gotoView("Home");
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

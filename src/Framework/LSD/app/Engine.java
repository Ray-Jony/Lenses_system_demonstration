package Framework.LSD.app;

import javafx.animation.AnimationTimer;

/**
 * Class Engine enable the functionality of refresh frames
 */

public class Engine {

    private final Timer timer;

    private double currentTimeNanos;
    private double lastTimeNanos;
    private double deltaTimeNanos;

    //frames per second
    private double fps;

    //Nanos per frame
    private double npf;

    OnUpdate onUpdate;
    OnStart onStart;
    OnStop onStop;


    interface OnUpdate {
        void handle(double time);
    }

    interface OnStart {
        void handle();
    }

    interface OnStop {
        void handle();
    }

    public Engine() {
        this(60);
    }

    public Engine(double fps) {
        timer = new Timer();
        setFps(fps);
    }

    public double getCurrentTimeNanos() {
        return currentTimeNanos;
    }

    public double getNowMillis() {
        return currentTimeNanos * 1E-6;
    }

    public double getNowSeconds() {
        return currentTimeNanos * 1E-9;
    }

    public double getLastTimeNanos() {
        return lastTimeNanos;
    }

    public double getLastMillis() {
        return lastTimeNanos * 1E-6;
    }

    public double getLastSeconds() {
        return lastTimeNanos * 1E-9;
    }

    public double getDeltaTimeNanos() {
        return deltaTimeNanos;
    }

    public double getDeltaMillis() {
        return deltaTimeNanos * 1E-6;
    }

    public double getDeltaSec() {
        return deltaTimeNanos * 1E-9;
    }

    public double getNpf() {
        return npf;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
        this.npf = 1E9 / fps;
    }


    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    private final class Timer extends AnimationTimer {
        @Override
        public void start() {
            this.reset();
            super.start();

            if (onStart != null) {
                onStart.handle();
            }
        }

        @Override
        public void stop() {
            if (onStop != null) {
                onStop.handle();
            }
            this.reset();
            super.stop();
        }

        @Override
        public void handle(long now) {
            currentTimeNanos = now;
            if (lastTimeNanos > 0) {
                deltaTimeNanos += currentTimeNanos - lastTimeNanos;
            }
            lastTimeNanos = currentTimeNanos;

            if (deltaTimeNanos >= npf) {
                if (onUpdate != null) {
                    onUpdate.handle(deltaTimeNanos);
                }

                deltaTimeNanos -= npf;
            }
        }

        private void reset() {
            currentTimeNanos = 0;
            lastTimeNanos = 0;
            deltaTimeNanos = 0;
        }

    }
}

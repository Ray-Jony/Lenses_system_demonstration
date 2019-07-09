package Framework.LSD.app;

import javafx.animation.AnimationTimer;

public class Engine {

    private final Timer timer;

    private double nowNanos;
    private double lastNanos;
    private double deltaNanos;

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

    public double getNowNanos() {
        return nowNanos;
    }

    public double getNowMillis() {
        return nowNanos * 1E-6;
    }

    public double getNowSeconds() {
        return nowNanos * 1E-9;
    }

    public double getLastNanos() {
        return lastNanos;
    }

    public double getLastMillis() {
        return lastNanos * 1E-6;
    }

    public double getLastSeconds() {
        return lastNanos * 1E-9;
    }

    public double getDeltaNanos() {
        return deltaNanos;
    }

    public double getDeltaMillis() {
        return deltaNanos * 1E-6;
    }

    public double getDeltaSec() {
        return deltaNanos * 1E-9;
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
            nowNanos = now;
            if (lastNanos > 0) {
                deltaNanos += nowNanos - lastNanos;
            }
            lastNanos = nowNanos;

            if (deltaNanos >= npf) {
                if (onUpdate != null) {
                    onUpdate.handle(deltaNanos);
                }

                deltaNanos -= npf;
            }
        }

        private void reset() {
            nowNanos = 0;
            lastNanos = 0;
            deltaNanos = 0;
        }

    }
}

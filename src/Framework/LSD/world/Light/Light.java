package Framework.LSD.world.Light;

import Framework.LSD.world.Intersection;
import javafx.scene.layout.Pane;

import java.util.*;

public class Light {

    ArrayList<Intersection> CircleMirrorIntersectionList = new ArrayList<>();
    ArrayList<Intersection> FlatMirrorIntersectionList = new ArrayList<>();

    static final int MAXIMUM_LIGHT_PATH_NUMBER = 5;

    static int lightPathId = 0;

    private HashMap<Integer, LightPath> lightPathMap = new HashMap<>();
    IntersectionListener intersectionListener = (len, direction) ->
            addLightPath(len, direction);

    public Light(LightPath... lightPath) {

        for (LightPath l : lightPath) {
            l.light = this;
            lightPathMap.put(lightPathId, l);
            lightPathId++;
        }
    }

    public void addLightPath(double len, double direction) {

        if (lightPathMap.values().size() > MAXIMUM_LIGHT_PATH_NUMBER) {
            return;
        }
        LightPath previousLightPath = lightPathMap.get(lightPathId - 1);
        LightPath newLightPath = new LightPath(
                previousLightPath.getEndPointX(),
                previousLightPath.getEndPointY(),
                len,
                direction,
                previousLightPath.getWaveLength()
        );
        newLightPath.light = this;
        lightPathMap.put(lightPathId, newLightPath);
        lightPathId++;
        intersectionDetect();
    }

    public void drawLight(Pane pane) {
        for (LightPath l :
                lightPathMap.values()) {
            l.drawLightPath(pane);
        }
    }

    public void intersectionDetect() {
        FlatMirrorIntersectionList.clear();
        CircleMirrorIntersectionList.clear();
        ArrayList<LightPath> temp = new ArrayList<>(lightPathMap.values());
        for (LightPath l :
                temp) {
            l.intersectionDetect();
        }
    }

    public interface IntersectionListener {
        void intersectionDetected(double len, double direction);
    }
}

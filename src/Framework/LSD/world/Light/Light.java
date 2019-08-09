package Framework.LSD.world.Light;

import Framework.LSD.world.Intersection;
import javafx.scene.layout.Pane;

import java.util.*;

public class Light {

    ArrayList<Intersection> CircleMirrorIntersectionList = new ArrayList<>();
    ArrayList<Intersection> FlatMirrorIntersectionList = new ArrayList<>();
    ArrayList<Intersection> LeftCircleLensSurfaceIntersectionList = new ArrayList<>();
    ArrayList<Intersection> RightCircleLensSurfaceIntersectionList = new ArrayList<>();

    private static final int MAXIMUM_LIGHT_PATH_NUMBER = 10;

    private int lightPathId = 0;

    private HashMap<Integer, LightPath> lightPathMap = new HashMap<>();

    private HashMap<Integer, Intersection.point> intersectionPointMap = new HashMap<>();

    IntersectionListener intersectionListener = this::addLightPath;


    public Light(LightPath lightPath) {
        lightPath.light = this;
        lightPath.setLightPathID(lightPathId);
        lightPathMap.put(lightPathId, lightPath);
        lightPathId++;
    }

    private void addLightPath(double len, double direction, int intersectionType) {

        if (lightPathMap.values().size() > MAXIMUM_LIGHT_PATH_NUMBER) {
            return;
        }
        LightPath previousLightPath = lightPathMap.get(lightPathId - 1);
        previousLightPath.setDetected(true);
        LightPath newLightPath = new LightPath(
                previousLightPath.getEndPointX(),
                previousLightPath.getEndPointY(),
                len,
                direction,
                previousLightPath.getWaveLength()
        );
        newLightPath.light = this;
        lightPathMap.put(lightPathId, newLightPath);
        newLightPath.setLightPathID(lightPathId);
//        if (intersectionType == Intersection.REFRACTION) {
//            if (previousLightPath.isInMedium()) {
//                newLightPath.setInMedium(false);
//            } else newLightPath.setInMedium(true);
//        } else if (intersectionType == Intersection.TOTAL_INTERNAL_REFLECTION) {
//            newLightPath.setInMedium(true);
//        } else if (intersectionType == Intersection.REFLECTION) {
//            newLightPath.setInMedium(previousLightPath.isInMedium());
//        }
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

        ArrayList<LightPath> temp = new ArrayList<>(lightPathMap.values());
        for (LightPath l :
                temp) {
            if (!l.isDetected()) {
                l.intersectionDetect();
            }
        }
    }

    HashMap<Integer, LightPath> getLightPathMap() {
        return lightPathMap;
    }

    HashMap<Integer, Intersection.point> getIntersectionPointMap() {
        return intersectionPointMap;
    }

    public interface IntersectionListener {
        void intersectionDetected(double len, double direction, int intersectionType);
    }
}

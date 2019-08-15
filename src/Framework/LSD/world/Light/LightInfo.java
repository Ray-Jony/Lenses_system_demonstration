package Framework.LSD.world.Light;

/**
 * Enum LightInfo is created on 13/08/2019 21:14.
 *
 * @author Ray
 * @version 13/08/2019
 **/

public enum LightInfo {

    RED(656.3), GREEN(589.3), BLUE(486.1);

    private double waveLength;

    LightInfo(double waveLength) {
        this.waveLength = waveLength;
    }

    public double getWaveLength() {
        return this.waveLength;
    }

}

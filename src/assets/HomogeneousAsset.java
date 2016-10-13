package assets;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAsset extends Asset {
    private static double intrinsicValue;
    public HomogeneousAsset(double intrinsicValue) {
        this.intrinsicValue = intrinsicValue;
    }

    public double getIntrinsicValue() {
        return intrinsicValue;
    }
}

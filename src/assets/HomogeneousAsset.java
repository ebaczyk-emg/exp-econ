package assets;

import agents.Agent;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAsset extends Asset {
    private double intrinsicValue;
    private AssetRegistry registry;

    public HomogeneousAsset(AssetRegistry registry,
                            double intrinsicValue) {
        this.registry = registry;
        this.intrinsicValue = intrinsicValue;
        this.owner = null;
    }

    public double getIntrinsicValue() {
        return intrinsicValue;
    }
}

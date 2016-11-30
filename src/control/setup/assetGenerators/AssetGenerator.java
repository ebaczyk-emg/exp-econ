package control.setup.assetGenerators;

import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class AssetGenerator {
    AssetRegistry registry;
    Simulation sim;

    public AssetGenerator(AssetRegistry assetRegistry, Simulation sim) {
        this.registry = assetRegistry;
        this.sim = sim;
    }

    public abstract ArrayList<Asset> generateAssets(int numAssets);
}

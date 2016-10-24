package control.assetGenerators;

import assets.Asset;
import assets.AssetRegistry;
import assets.HomogeneousAsset;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAssetGenerator extends AssetGenerator {
    private AssetRegistry registry;
    private Simulation sim;

    public HomogeneousAssetGenerator(AssetRegistry assetRegistry, Simulation sim) {
        this.registry = assetRegistry;
        this.sim = sim;
    }

    public ArrayList<Asset> generateAssets(int numAssets){
        ArrayList<Asset> assets = new ArrayList<>();
        for(int i = 0; i < numAssets; i++) {
            Asset newAsset = new HomogeneousAsset(
                    registry,
                    Math.random() *
                        (sim.getConfig().getMaxAssetValue() - sim.getConfig().getMinAssetValue())
                        + sim.getConfig().getMinAssetValue());
            assets.add(newAsset);
        }
        return assets;
    }
}

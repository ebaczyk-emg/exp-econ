package control.setup.assetGenerators;

import assets.Asset;
import assets.AssetRegistry;
import assets.HomogeneousAsset;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAssetGenerator extends AssetGenerator {

    public HomogeneousAssetGenerator(AssetRegistry assetRegistry, Simulation sim) {
        super(assetRegistry,
                sim);
    }

    public ArrayList<Asset> generateAssets(int numAssets){
        ArrayList<Asset> assets = new ArrayList<>(numAssets);
        for(int i = 0; i < numAssets; i++) {
            double value = sim.getRandom().nextDouble() *
                    (sim.getConfig().getMaxAssetValue() - sim.getConfig().getMinAssetValue())
                    + sim.getConfig().getMinAssetValue();
            Asset newAsset = new HomogeneousAsset(registry, value);
            assets.add(newAsset);
        }
        return assets;
    }
}

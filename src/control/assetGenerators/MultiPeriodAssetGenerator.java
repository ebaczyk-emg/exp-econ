package control.assetGenerators;

import assets.Asset;
import assets.AssetRegistry;
import assets.HomogeneousAsset;
import assets.MultiPeriodAsset;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 11/1/2016.
 */
public class MultiPeriodAssetGenerator extends AssetGenerator {

    public MultiPeriodAssetGenerator(AssetRegistry registry, Simulation sim) {
        super(registry,
                sim);
    }

    public ArrayList<Asset> generateAssets(int numAssets) {
        ArrayList<Asset> assets = new ArrayList<>(numAssets);
        for(int i = 0; i < numAssets; i++) {
            double value = Math.random() *
                    (sim.getConfig().getMaxAssetValue() - sim.getConfig().getMinAssetValue())
                    + sim.getConfig().getMinAssetValue();
            Asset newAsset = new MultiPeriodAsset(registry, value);
            assets.add(newAsset);
        }
        return assets;
    }

}

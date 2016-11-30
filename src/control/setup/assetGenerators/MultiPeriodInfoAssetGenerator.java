package control.setup.assetGenerators;

import assets.Asset;
import assets.AssetRegistry;
import assets.InformationAsset;
import assets.MultiPeriodAsset;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 11/1/2016.
 */
public class MultiPeriodInfoAssetGenerator extends AssetGenerator {
    private double dividend;

    public MultiPeriodInfoAssetGenerator(AssetRegistry registry, Simulation sim) {
        super(registry,
                sim);
    }

    public ArrayList<Asset> generateAssets(int numAssets) {
        ArrayList<Asset> assets = new ArrayList<>(numAssets);
        this.dividend = registry.determineDividend();
        for(int i = 0; i < numAssets; i++) {
            double value = sim.getRandom().nextDouble() *
                    (sim.getConfig().getMaxAssetValue() - sim.getConfig().getMinAssetValue())
                    + sim.getConfig().getMinAssetValue();
            Asset newAsset = new InformationAsset(registry, value, dividend);
            assets.add(newAsset);
        }
        return assets;
    }

}

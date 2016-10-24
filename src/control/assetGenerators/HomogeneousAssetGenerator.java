package control.assetGenerators;

import assets.Asset;
import assets.HomogeneousAsset;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAssetGenerator extends AssetGenerator {
    private Simulation sim;

    public HomogeneousAssetGenerator(Simulation sim) {
        this.sim = sim;
    }

    public ArrayList<Asset> generateAssets(int numAssets){
        ArrayList<Asset> assets = new ArrayList<>();
        for(int i = 0; i < numAssets; i++) {
            Asset newAsset = new HomogeneousAsset(Math.random() *
                    (sim.getConfig().getMaxAssetValue() - sim.getConfig().getMinAssetValue())
                    + sim.getConfig().getMinAssetValue());
            assets.add(newAsset);
        }
        return assets;
    }
}

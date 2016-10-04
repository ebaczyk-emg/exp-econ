package control.assetGenerators;

import assets.Asset;
import assets.HomogeneousAsset;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAssetGenerator extends AssetGenerator {
    public HomogeneousAssetGenerator() {

    }

    public ArrayList<Asset> generateAssets(int numAssets){
        ArrayList<Asset> assets = new ArrayList<>();
        for(int i = 0; i < numAssets; i++) {
            Asset newAsset = new HomogeneousAsset(Math.random() * 100);
            assets.add(newAsset);
        }
        return assets;
    }
}

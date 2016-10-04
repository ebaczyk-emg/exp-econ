package control.assetGenerators;

import assets.Asset;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class AssetGenerator {

    public abstract ArrayList<Asset> generateAssets(int numAssets);
}

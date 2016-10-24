package assets;

import control.Simulation;
import control.config.Config;

import java.util.ArrayList;

/**
 * Created by Emily on 10/24/2016.
 */
public class AssetRegistry {
    private Simulation sim;
    private ArrayList<Asset> assets;
    private static final int ID_START = 1;
    private int ID;

    public AssetRegistry(Simulation sim) {
        this.sim = sim;
        assets = new ArrayList<>((sim.getConfig().getnAgents() * sim.getConfig().getInitAssetEndowment()));
        ID = ID_START;
    }

    public void init(Asset asset) {
        asset.setID(ID);
        ID++;
        assets.add(asset);
        System.out.println(asset.getID());
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public Config getConfig() {
        return sim.getConfig();
    }
}

package assets;

import control.Simulation;
import control.config.Config;
import markets.Marketplace;

import java.util.ArrayList;

/**
 * Created by Emily on 10/24/2016.
 */
public class AssetRegistry {
    private Simulation sim;
    private ArrayList<Asset> assets;
    private static final int ID_START = 1;
    private int ID;
    private Marketplace marketplace;

    public AssetRegistry(Simulation sim) {
        this.sim = sim;
        assets = new ArrayList<>((sim.getConfig().getnAgents() * sim.getConfig().getInitAssetEndowment()));
        ID = ID_START;
        marketplace = sim.getMarket();
    }

    public void init(Asset asset) {
        asset.setID(ID);
        ID++;
        assets.add(asset);
    }

    public double determineDividend() {
        double lowRegime = sim.getConfig().getInfoDividendMin();
        double highRegime = sim.getConfig().getInfoDividendMax();
        double p = sim.getConfig().getInfoPStateA();
        if(sim.getRandom().nextBoolean(p)) {
            return lowRegime;
        } else {
            return highRegime;
        }
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public Config getConfig() {
        return sim.getConfig();
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }
}

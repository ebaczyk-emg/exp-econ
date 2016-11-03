package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

/**
 * Created by Emily on 11/2/2016.
 */
public class LevelForwardAgent extends Agent {

    public LevelForwardAgent(AgentPopulation population) {
        super(population);
    }

    public Bid getBid() {
        if(assetEndowment.size() == 0) {
            return new Bid(this, 75d);
        } else {
            Asset mostValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getIntrinsicValue() > mostValuableOwnedAsset.getIntrinsicValue()) {
                    mostValuableOwnedAsset = asset;
                }
            }
            return this.getBid(mostValuableOwnedAsset);
        }
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) {
            Asset leastValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getIntrinsicValue() < leastValuableOwnedAsset.getIntrinsicValue()) {
                    leastValuableOwnedAsset = asset;
                }
            }
            return this.getOffer(leastValuableOwnedAsset);
        }
        else return null;
    }

    public double getFundamentalValue(Asset a) {
        return a.getIntrinsicValue();
    }
}

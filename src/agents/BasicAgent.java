package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emily on 10/3/2016.
 */
public class BasicAgent extends Agent{
    AgentPopulation population;


    public BasicAgent(AgentPopulation population) {
        super(population);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
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

    public ArrayList<Double> getValuesForAllPeriods(){
        return this.valuesForAllPeriods;
    }

}

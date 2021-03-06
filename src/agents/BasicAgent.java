package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;

/**
 * Created by Emily on 10/3/2016.
 */
public class BasicAgent extends Agent{

    public BasicAgent(AgentPopulation population,
                      boolean informed) {
        super(population, informed);
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
                if (asset.getFundingCost() > mostValuableOwnedAsset.getFundingCost()) {
                    mostValuableOwnedAsset = asset;
                }
            }
            return this.getBid(calculateFairValue(mostValuableOwnedAsset));
        }
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) {
            Asset leastValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getFundingCost() < leastValuableOwnedAsset.getFundingCost()) {
                    leastValuableOwnedAsset = asset;
                }
            }
            return this.getOffer(leastValuableOwnedAsset);
        }
        else return null;
    }

    public double calculateFairValue(Asset a) {
        return a.getFundingCost();
    }

    public ArrayList<Double> getValuesForAllPeriods(){
        return this.valuesForAllPeriods;
    }

}

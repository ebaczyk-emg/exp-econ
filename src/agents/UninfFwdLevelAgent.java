package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Emily on 11/29/2016.
 */
public class UninfFwdLevelAgent extends Agent {

    public UninfFwdLevelAgent(AgentPopulation population,
                              boolean informed) {
        super(population, informed);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
    }

    public Bid getBid() {
        //generate a bid that is the maximum of (total cash on hand, calculated FV of asset)
        double maxBid = 0d;
        if(this.getOwnedAssets().isEmpty()) {
            maxBid = this.calculateFairValue(null);
        } else {
            for(Asset a : this.getOwnedAssets()) {
                if(this.calculateFairValue(a) > maxBid) {
                    maxBid = this.calculateFairValue(a);
                }
            }
        }
        Bid bid = getBid(maxBid);
        return bid;
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) {
            Collections.sort(assetEndowment);
            Asset leastValuableProfitableAsset = null;
            for (Asset asset : assetEndowment) {
                if (asset.getFundingCost() <= this.calculateFairValue(null)) {
                    leastValuableProfitableAsset = asset;
                    break;
                }
            }
            if(leastValuableProfitableAsset != null) {
                return this.getOffer(leastValuableProfitableAsset);
            } else {
                return null;
            }
        }
        else return null;
    }

    public double calculateFairValue(Asset a) {
        ArrayList<Double> lastTransactions = population.getMarket().getPastTransactionPrices();
        double FV;
        if(lastTransactions.size() != 0) {
            double tempAvg = 0;
            for (int i = 0; i < Math.min(population.getConfig().getBckLookbackPeriod(),
                    lastTransactions.size()); i++) {
                tempAvg += lastTransactions.get(i);
            }
            tempAvg = tempAvg / Math.min(population.getConfig().getBckLookbackPeriod(),
                    lastTransactions.size());
            FV = tempAvg;
        } else {
            FV = population.getConfig().getInfoIntrinsicValue() +
                    (population.getConfig().getInfoDividendMax() +
                            population.getConfig().getInfoDividendMin()) / 2;
        }
        return FV;
    }

}
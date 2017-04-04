package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import util.LinearTrendLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emily on 11/29/2016.
 */
public class UninfExpDeltaAgent extends Agent {

    public UninfExpDeltaAgent(AgentPopulation population,
                              boolean informed) {
        super(population, informed);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
    }

    public Bid getBid() {
        Bid bid = getBid(this.calculateFairValue(null));
        return bid;
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) { //make sure you have assets
            Collections.sort(assetEndowment); //sort assets on funding cost
            Asset leastValuableProfitableAsset = null;
            //find the least valuable asset that can still be sold for a profit
            for (Asset asset : assetEndowment) {
                if (asset.getFundingCost() <= this.calculateFairValue(null)) {
                    leastValuableProfitableAsset = asset;
                    break;
                }
            }
            //calculate a bid for this asset if there is such an asset
            if(leastValuableProfitableAsset != null) {
                return this.getOffer(leastValuableProfitableAsset);
            } else {
                return null;
            }
        } //else, can't sell anything
        else return null;
    }

    public double calculateFairValue(Asset a) {
        ArrayList<Double> lastTransactions = population.getMarket().getPastTransactionPrices();
        double FV = 0;
        double theta = 0.9d;

        //if we have enough information, we should default to
        if(lastTransactions.size() >= 2) {
            //first, find the number that have been moving in the same direction
            List<Double> consideredTransactions = new ArrayList<>(lastTransactions);
            Collections.reverse(consideredTransactions);
            consideredTransactions = consideredTransactions.subList(0, Math.min(consideredTransactions.size(), population.getConfig().getBckLookbackPeriod()));
            double sum = 0d;
            for(int i = 0; i < consideredTransactions.size()-1; i++) {
                sum += Math.pow(theta, i) * (consideredTransactions.get(i)
                        - consideredTransactions.get(i+1));
            }
            FV = consideredTransactions.get(0) + (1-theta) * sum;

        } else {
            //there is not enough transaction data to infer direction, so we default to EV
            if (a != null) {
                FV = a.getFundingCost();
            } else {
                FV = population.getConfig().getInfoIntrinsicValue() +
                        (population.getConfig().getInfoDividendMin() +
                                population.getConfig().getInfoDividendMax()) / 2;
            }
        }

        return Math.min(FV, population.getConfig().getMaxAssetValue());
    }
}

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
public class UninfFwdDeltaAgent extends Agent {

    public UninfFwdDeltaAgent(AgentPopulation population,
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

        //if we have enough information, we should default to
        if(lastTransactions.size() >= 2) {
            //first, find the number that have been moving in the same direction
            List<Double> consideredTransactions = new ArrayList<>(lastTransactions);
            Collections.reverse(consideredTransactions);
            consideredTransactions = consideredTransactions.subList(0, Math.min(consideredTransactions.size(), population.getConfig().getBckLookbackPeriod()));
            boolean isIncreasing = (consideredTransactions.get(0) > consideredTransactions.get(1));
            ArrayList<Double> finalTransactions = new ArrayList<>();
            finalTransactions.add(consideredTransactions.get(0));
            consideredTransactions.remove(0);
            if (isIncreasing) {
                for(double price : consideredTransactions) {
                    if(price <= finalTransactions.get(finalTransactions.size()-1)) {
                        finalTransactions.add(price);
                    } else {
                        break;
                    }
                }
            } else {
                for(double price : consideredTransactions) {
                    if(price >= finalTransactions.get(finalTransactions.size()-1)) {
                        finalTransactions.add(price);
                    } else {
                        break;
                    }
                }
            }

            //now run a regression on the calculated values
            Collections.reverse(finalTransactions);
            int n = finalTransactions.size();
            double[] index = new double[n];
            double[] values = new double[n];
            for (int i = 0; i < n; i++) {
                index[i] = i;
                values[i] = finalTransactions.get(i);
            }

            if(n > 2) {
                LinearTrendLine trend = new LinearTrendLine();
                trend.setValues(values, index);
                FV = trend.predict(n);

            } else {
                if(isIncreasing) {
                    FV = values[1] + (values[1] - values[0]);
                } else {
                    FV = values[1] - (values[0] - values[1]);
                }
            }


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

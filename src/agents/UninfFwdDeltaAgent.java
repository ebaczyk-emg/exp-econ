package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import util.LinearTrendLine;

import java.util.ArrayList;
import java.util.Collections;

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
        //generate a bid that is the maximum of (total cash on hand, calculated FV of asset)
        double maxBid = 0d;
        if(this.getOwnedAssets().isEmpty()) {
            maxBid = this.getFundamentalValue(null);
        } else {
            for(Asset a : this.getOwnedAssets()) {
                if(this.getFundamentalValue(a) > maxBid) {
                    maxBid = this.getFundamentalValue(a);
                }
            }
        }
        Bid bid = getBid(maxBid);
        return bid;
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) { //make sure you have assets
            Collections.sort(assetEndowment); //sort assets on funding cost
            Asset leastValuableProfitableAsset = null;
            //find the least valuable asset that can still be sold for a profit
            for (Asset asset : assetEndowment) {
                if (asset.getFundingCost() <= this.getFundamentalValue(null)) {
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

    public double getFundamentalValue(Asset a) {
        ArrayList<Double> lastTransactions = population.getMarket().getPastTransactionPrices();
        double FV = 0;

        //if we have enough information, we should default to
        if(lastTransactions.size() >= 2) {
            //first, find the number that have been moving in the same direction
            ArrayList<Double> consideredTransactions = new ArrayList<>(lastTransactions);
            Collections.reverse(consideredTransactions);
            boolean isIncreasing = (consideredTransactions.get(0) > consideredTransactions.get(1));
            ArrayList<Double> finalTransactions = new ArrayList<>();
            finalTransactions.add(consideredTransactions.get(0));

            if (isIncreasing) {
                while (true) {
                    try {
                        if(consideredTransactions.get(0) > consideredTransactions.get(1)) {
                            finalTransactions.add(consideredTransactions.get(1));
                            consideredTransactions.remove(0);
                        } else {
                            break;
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        break;
                    }
                }
            } else {
                while (true) {
                    try {
                        if(consideredTransactions.get(0) > consideredTransactions.get(1)) {
                            finalTransactions.add(consideredTransactions.get(1));
                            consideredTransactions.remove(0);
                        } else {
                            break;
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        break;
                    }
                }
            }

            //now run a regression on the calculated values
            int n = consideredTransactions.size();
            double[] index = new double[n];
            double[] values = new double[n];
            for(int i = 0; i < n; i++){
                index[i] = i;
                values[i] = consideredTransactions.get(i);
            }
            LinearTrendLine trend = new LinearTrendLine();
            trend.setValues(index, values);
            FV = population.getConfig().getInfoIntrinsicValue() + trend.predict(n+1);

            System.out.println("PREDICTING FOR LEVELS");

        } else {
            //there is not enough transaction data to infer direction, so we default to EV
            FV = population.getConfig().getInfoIntrinsicValue() + (population.getConfig().getInfoDividendMin() +
                    population.getConfig().getInfoDividendMax())/2;
        }

        return FV;
    }
}

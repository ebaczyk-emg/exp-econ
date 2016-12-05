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
//        System.out.println("testing decay " + maxBid + " " + bid.getBidPrice());
        return bid;
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
        //then, find the regression

//        if(lastTransactions.size() == 0) {
//            double tempAvg = 0;
//            for (int i = 0; i < Math.min(population.getConfig().getBckLookbackPeriod(),
//                    lastTransactions.size()); i++) {
//                tempAvg += lastTransactions.get(i);
//            }
//            tempAvg = tempAvg / Math.min(population.getConfig().getBckLookbackPeriod(),
//                    lastTransactions.size());
//            return tempAvg;
//        } else {
////            return population.getConfig().getInitAssetEndowment() +
////                    (population.getConfig().getInfoDividendMax() + population.getConfig().getInfoDividendMin())/2;
//            return a.getFundingCost();
//        }
    }
}

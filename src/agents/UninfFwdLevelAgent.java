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

    public double calculateFairValue(Asset a) {
        ArrayList<Double> lastTransactions = population.getMarket().getPastTransactionPrices();
        Collections.reverse(lastTransactions);
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
            if (a != null) {
                FV = a.getFundingCost();
            } else {
                FV=0;
                for(Asset asset : this.getOwnedAssets()){
                    FV += asset.getFundingCost();
                }
                FV = FV / this.getOwnedAssets().size();
//                FV = population.getConfig().getInfoIntrinsicValue() +
//                        (population.getConfig().getInfoDividendMax() +
//                                population.getConfig().getInfoDividendMin()) / 2;
            }
        }
        return Math.min(FV, population.getConfig().getMaxAssetValue());
    }

}

package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Emily on 11/29/2016.
 */
public class UninfBckAgent extends Agent {

    public UninfBckAgent(AgentPopulation population,
                      boolean informed) {
        super(population, informed);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
    }

    public Bid getBid() {
        //generate a bid that is the maximum of (total cash on hand, calculated FV of asset)
        double maxBid = this.getFundamentalValue(null);
        double bidPrice = maxBid - Math.exp(maxBid/5 * -1);
        System.out.println("testing decay " + maxBid + " " + bidPrice);
        return new Bid(this, Math.max(this.getCashEndowment(), bidPrice));
    }

    public Offer getOffer() {
        //first, try to sell the asset with the highest funding cost (i.e. asset.intrinsicValue)
        if(assetEndowment.size() > 0) {
            Asset mostValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getIntrinsicValue() > mostValuableOwnedAsset.getIntrinsicValue()) {
                    mostValuableOwnedAsset = asset;
                }
            }
            return this.getOffer(mostValuableOwnedAsset);
        }
        //if no assets, then cannot sell
        else return null;
    }

    public double getFundamentalValue(Asset a) {
        ArrayList<Double> lastTransactions = population.getMarket().getPastTransactionPrices();
        if(lastTransactions.size() == 0) {
            double tempAvg = 0;
            for (int i = 0; i < Math.min(population.getConfig().getBckLookbackPeriod(),
                    lastTransactions.size()); i++) {
                tempAvg += lastTransactions.get(i);
            }
            tempAvg = tempAvg / Math.min(population.getConfig().getBckLookbackPeriod(),
                    lastTransactions.size());
            return tempAvg;
        } else {
//            return population.getConfig().getInitAssetEndowment() +
//                    (population.getConfig().getInfoDividendMax() + population.getConfig().getInfoDividendMin())/2;
            return a.getIntrinsicValue();
        }
    }
}

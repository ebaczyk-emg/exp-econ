package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;

/**
 * Created by Emily on 11/29/2016.
 */
public class InfBckAgent extends Agent {

    public InfBckAgent(AgentPopulation population,
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
        ArrayList<Boolean> information = population.getMarket().getReleasedInfo();
        //in no information, default to EV
        if(information.isEmpty()) {
            return population.getConfig().getInfoIntrinsicValue() +
                    (population.getConfig().getInfoDividendMax() + population.getConfig().getInfoDividendMin())/2;
        } else {
            int stateA = 0, stateB = 0;
            for (int i = 0; i < information.size(); i++) {
                if (information.get(i) == true) {
                    stateA++;
                } else {
                    stateB++;
                }
            }

//            System.out.println(stateA + " A <-> B " + stateB);


            //only move if differential above threshold
            double FV ;
            if (Math.abs(stateA - stateB) >= population.getConfig().getInfInfoThreshold()) {
                System.out.println("AGENTS HAVE ENOUGH INFO " + (stateA- stateB));
                if (stateA > stateB) {
                    FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMin();
                } else {
                    FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMax();
                }
            } else {
                //if info not above threshold, just default to EV
                FV = population.getConfig().getInfoIntrinsicValue() +
                        (population.getConfig().getInfoDividendMax() + population.getConfig().getInfoDividendMin())/2;
            }

//            System.out.println("CALCULATED FV of " + FV);
            return FV;
        }
    }
}

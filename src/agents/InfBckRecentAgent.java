package agents;

import assets.Asset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emily on 11/29/2016.
 */
public class InfBckRecentAgent extends Agent {

    public InfBckRecentAgent(AgentPopulation population,
                             boolean informed) {
        super(population, informed);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
    }

    public double calculateFairValue(Asset a) {
        ArrayList<Boolean> information = population.getMarket().getReleasedInfo();
        List trimmedInfo = information.subList(0, Math.min((-1 + information.size()), 10));
        double FV = 0;
        //in no information, default to EV
        if(information.isEmpty()) {
            if(a != null) {
                FV = a.getFundingCost();
            } else {
                for(Asset asset : this.getOwnedAssets()){
                    FV += asset.getFundingCost();
                }
                FV = FV / Math.max(1,this.getOwnedAssets().size());
            }
        } else {
            int stateA = 0, stateB = 0;
            for (int i = 0; i < information.size(); i++) {
                if (information.get(i) == true) {
                    stateA++;
                } else {
                    stateB++;
                }
            }


            if (stateA > stateB) {
                FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMin();
            } else {
                FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMax();
            }

        }

        return Math.min(FV, population.getConfig().getMaxAssetValue());
    }
}

package agents;

import assets.Asset;

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

    public double calculateFairValue(Asset a) {
        ArrayList<Boolean> information = population.getMarket().getReleasedInfo();
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

            //only move if differential above threshold
            if (Math.abs(stateA - stateB) >= population.getConfig().getInfInfoThreshold()) {
                System.out.println("AGENTS HAVE ENOUGH INFO " + (stateA- stateB));
                if (stateA > stateB) {
                    FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMin();
                } else {
                    FV = population.getConfig().getInfoIntrinsicValue() + population.getConfig().getInfoDividendMax();
                }
            } else {
                //if info isn't above threshold, just act as other agents
                //there is not enough transaction data to infer direction, so we default
//                if(this.getAssetEndowment() > 0){
//                    FV = this.getAverageFundingCost();
//                } else {
                    FV = population.getConfig().getInfoIntrinsicValue() +
                            (population.getConfig().getInfoDividendMin() +
                                    population.getConfig().getInfoDividendMax()) / 2;
//                }
            }
        }

        return Math.min(FV, population.getConfig().getMaxAssetValue());
    }
}

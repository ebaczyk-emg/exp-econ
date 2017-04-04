package agents;

import assets.Asset;

import java.util.ArrayList;

/**
 * Created by Emily on 11/29/2016.
 */
public class InfBck10Agent extends Agent {
    private boolean highState;
    public InfBck10Agent(AgentPopulation population,
                         boolean informed) {
        super(population, informed);
        this.population = population;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();
        this.highState = false;

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
            if(information.size() <= 10) {
                int stateA = 0, stateB = 0;
                for (int i = 0; i < information.size(); i++) {
                    if (information.get(i) == true) {
                        stateA++;
                    } else {
                        stateB++;
                    }
                }
                if(stateA > stateB) {
                    highState = false;
                } else highState = true;

            } else {
                if(highState) {
                    FV = population.getConfig().getInfoIntrinsicValue() +
                            population.getConfig().getInfoDividendMax();
                } else {
                    FV = population.getConfig().getInfoIntrinsicValue() +
                            population.getConfig().getInfoDividendMin();
                }
            }
        }
        System.out.println(highState + " " + FV);
        return Math.min(FV, population.getConfig().getMaxAssetValue());
    }
}

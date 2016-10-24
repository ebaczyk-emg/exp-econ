package agents;

import agentBrains.InductionBrain;
import agentBrains.LevelBrain;
import agentBrains.ThoughtBrain;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 10/3/2016.
 */
public class BasicAgent extends Agent{

    InductionBrain inductionBrain;
    LevelBrain levelBrain;
    ThoughtBrain thoughtBrain;

    private double cashEndowment;
    private int assetEndowment;

    ArrayList<Double> valuesForAllPeriods;

    public BasicAgent(InductionBrain inductionBrain,
                      LevelBrain levelBrain,
                      ThoughtBrain thoughtBrain){
        this.inductionBrain = inductionBrain;
        this.levelBrain = levelBrain;
        this.thoughtBrain = thoughtBrain;
        this.cashEndowment = Simulation.getInitCashEndowment();
        this.assetEndowment = Simulation.getInitAssetEndowment();

        valuesForAllPeriods = new ArrayList<>();
        valuesForAllPeriods.add(this.getFundamentalValue());
    }

    public double getBid() {
        double calculatedFairValue = this.getFundamentalValue();
        double calculatedBid = Math.random()*20 - calculatedFairValue; //some amount less than you think it's worth
        if(calculatedBid > cashEndowment) {
            return 0d;
        } else {
            return calculatedBid;
        }

    }

    public double getOffer() {
        double calculatedFairValue = this.getFundamentalValue();
        double calculatedOffer = Math.random()*20 + calculatedFairValue; // some amount more than you think it's worth
        if(assetEndowment == 0) {
            return 0d;
        } else {
            return calculatedOffer;
        }
    }

    public double getFundamentalValue() {
        return 0d;
    }

    @Override
    public double getCashEndowment() {
        return cashEndowment;
    }

    public void setCashEndowment(double cashEndowment) {
        this.cashEndowment = cashEndowment;
    }

    @Override
    public int getAssetEndowment() {
        return assetEndowment;
    }

    public void setAssetEndowment(int assetEndowment) {
        this.assetEndowment = assetEndowment;
    }

    public InductionBrain getInductionBrain() {
        return inductionBrain;
    }

    public void setInductionBrain(InductionBrain inductionBrain) {
        this.inductionBrain = inductionBrain;
    }

    public LevelBrain getLevelBrain() {
        return levelBrain;
    }

    public void setLevelBrain(LevelBrain levelBrain) {
        this.levelBrain = levelBrain;
    }

    public ThoughtBrain getThoughtBrain() {
        return thoughtBrain;
    }

    public void setThoughtBrain(ThoughtBrain thoughtBrain) {
        this.thoughtBrain = thoughtBrain;
    }

    public ArrayList<Double> getValuesForAllPeriods(){
        return this.valuesForAllPeriods;
    }
}

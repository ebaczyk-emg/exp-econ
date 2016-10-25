package agents;

import agentBrains.InductionBrain;
import agentBrains.LevelBrain;
import agentBrains.ThoughtBrain;
import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;

/**
 * Created by Emily on 10/3/2016.
 */
public class BasicAgent extends Agent{

    InductionBrain inductionBrain;
    LevelBrain levelBrain;
    ThoughtBrain thoughtBrain;

    ArrayList<Double> valuesForAllPeriods;

    public BasicAgent(AgentPopulation population,
                      InductionBrain inductionBrain,
                      LevelBrain levelBrain,
                      ThoughtBrain thoughtBrain){
        this.inductionBrain = inductionBrain;
        this.levelBrain = levelBrain;
        this.thoughtBrain = thoughtBrain;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
        //valuesForAllPeriods.add(this.getFundamentalValue());
    }

    public Bid getBid() {
        return new Bid (null, 0d);
    }

    public Offer getOffer() {
        return new Offer(null, 0d, null);
    }

    public double getBid(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedBid = Math.random()*20 - calculatedFairValue; //some amount less than you think it's worth
        if(calculatedBid > cashEndowment) {
            return 0d;
        } else {
            return calculatedBid;
        }

    }

    public double getOffer(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedOffer = Math.random()*20 + calculatedFairValue; // some amount more than you think it's worth
        if(assetEndowment.size() == 0) {
            return 0d;
        } else {
            return calculatedOffer;
        }
    }

    public double getFundamentalValue(Asset a) {
        return 0d;
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

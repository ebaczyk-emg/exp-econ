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
    AgentPopulation population;
    InductionBrain inductionBrain;
    LevelBrain levelBrain;
    ThoughtBrain thoughtBrain;

    ArrayList<Double> valuesForAllPeriods;

    public BasicAgent(AgentPopulation population,
                      InductionBrain inductionBrain,
                      LevelBrain levelBrain,
                      ThoughtBrain thoughtBrain){
        this.population = population;
        this.inductionBrain = inductionBrain;
        this.levelBrain = levelBrain;
        this.thoughtBrain = thoughtBrain;
        this.cashEndowment = 0;
        this.assetEndowment = new ArrayList<>();

        valuesForAllPeriods = new ArrayList<>();
        //valuesForAllPeriods.add(this.getFundamentalValue());
    }

    public Bid getBid() {
        if(assetEndowment.size() == 0) {
            return new Bid(this, 75d);
        } else {
            Asset mostValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getIntrinsicValue() > mostValuableOwnedAsset.getIntrinsicValue()) {
                    mostValuableOwnedAsset = asset;
                }
            }
            return this.getBid(mostValuableOwnedAsset);
        }
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) {
            Asset leastValuableOwnedAsset = assetEndowment.get(0);
            for (Asset asset : assetEndowment) {
                if (asset.getIntrinsicValue() < leastValuableOwnedAsset.getIntrinsicValue()) {
                    leastValuableOwnedAsset = asset;
                }
            }
            return this.getOffer(leastValuableOwnedAsset);
        }
        else return null;
    }

    public Bid getBid(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedBid = Math.max(calculatedFairValue - Math.random()*20,0d); //some amount less than you think it's worth
        if(calculatedBid > cashEndowment) {
            return new Bid(this, population.getConfig().getMinAssetValue());
        } else {
            return new Bid(this, calculatedBid);
        }

    }

    public Offer getOffer(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedOffer = Math.random()*20 + calculatedFairValue; // some amount more than you think it's worth
        if(assetEndowment.size() == 0) {
            return new Offer(this, population.getConfig().getMaxAssetValue(), a);
        } else {
            return new Offer(this, calculatedOffer, a);
        }
    }

    public double getFundamentalValue(Asset a) {
        return a.getIntrinsicValue();
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

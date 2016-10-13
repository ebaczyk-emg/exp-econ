package agents;

import agentBrains.InductionBrain;
import agentBrains.LevelBrain;
import agentBrains.ThoughtBrain;

/**
 * Created by Emily on 10/3/2016.
 */
public class BasicAgent extends Agent{

    InductionBrain inductionBrain;
    LevelBrain levelBrain;
    ThoughtBrain thoughtBrain;

    public BasicAgent(InductionBrain inductionBrain,
                      LevelBrain levelBrain,
                      ThoughtBrain thoughtBrain){
        this.inductionBrain = inductionBrain;
        this.levelBrain = levelBrain;
        this.thoughtBrain = thoughtBrain;
    }

    public double getBid() {
        return 0d;
    }

    public double getOffer() {
        return 0d;
    }

    public double getFundamentalValue() {
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
}

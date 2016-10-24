package agentBrains.levelBrains;

import agentBrains.LevelBrain;
import agents.Agent;
import assets.Asset;

/**
 * Created by Emily on 9/28/2016.
 */
public class PriceLevelBrain extends LevelBrain{

    public PriceLevelBrain() {

    }

    public double valueAsset(Asset a) {
        return a.getIntrinsicValue();
    }
}

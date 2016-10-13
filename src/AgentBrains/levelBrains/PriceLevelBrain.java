package agentBrains.levelBrains;

import agentBrains.LevelBrain;
import assets.Asset;

/**
 * Created by Emily on 9/28/2016.
 */
public class PriceLevelBrain extends LevelBrain{

    public double valueAsset(Asset a) {
        return a.getIntrinsicValue();
    }
}

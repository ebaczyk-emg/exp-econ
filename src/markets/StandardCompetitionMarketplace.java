package markets;

import agents.Agent;
import assets.Asset;
import control.assetGenerators.AssetGenerator;
import control.brainAllocators.BrainAllocator;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public class StandardCompetitionMarketplace extends Marketplace{

    ArrayList<Agent> agents = new ArrayList<>();
    ArrayList<Asset> assets = new ArrayList<>();
    int numAgents;

    public StandardCompetitionMarketplace(BrainAllocator brainAllocator,
                                          AssetGenerator assetGenerator,
                                          int numAgents){
        this.numAgents = numAgents;
        this.agents = brainAllocator.generateAgents(numAgents);
        this.assets = assetGenerator.generateAssets(1);
    }

    public boolean runOneStep() {

        for(Agent agent : agents) {

        }
        return true;
    }

}

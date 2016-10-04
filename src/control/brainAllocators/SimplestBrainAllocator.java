package control.brainAllocators;

import agentBrains.inductionBrains.ForwardInductionBrain;
import agentBrains.levelBrains.PriceLevelBrain;
import agentBrains.thoughtBrains.FirstOrderThoughtBrain;
import agents.Agent;
import agents.BasicAgent;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public class SimplestBrainAllocator extends BrainAllocator{

    public ArrayList<Agent> generateAgents(int numberOfAgents){
        ArrayList<Agent> agents = new ArrayList<>();
        for(int n = 0; n < numberOfAgents; n++){
            Agent newAgent = new BasicAgent(
                    new ForwardInductionBrain(),
                    new PriceLevelBrain(),
                    new FirstOrderThoughtBrain());
            agents.add(newAgent);
        }
        return agents;
    }
}

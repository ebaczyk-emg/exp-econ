package control.brainAllocators;

import agents.Agent;
import agents.AgentPopulation;
import agents.BasicAgent;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public class SimplestBrainAllocator extends BrainAllocator{
    private Simulation sim;
    private AgentPopulation population;

    public SimplestBrainAllocator(AgentPopulation population, Simulation sim) {
        this.sim = sim;
        this.population = population;
    }

    public ArrayList<Agent> generateAgents(int numberOfAgents){
        ArrayList<Agent> agents = new ArrayList<>();
        for(int n = 0; n < numberOfAgents; n++){
            Agent newAgent = new BasicAgent(
                    population,
                    false);
            agents.add(newAgent);
        }
        return agents;
    }
}

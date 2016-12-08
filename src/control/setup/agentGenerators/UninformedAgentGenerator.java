package control.setup.agentGenerators;

import agents.*;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 11/30/2016.
 */
public class UninformedAgentGenerator extends AgentGenerator {
    private Simulation sim;
    private AgentPopulation population;

    public UninformedAgentGenerator(AgentPopulation population, Simulation sim) {
        this.sim = sim;
        this.population = population;
    }

    public ArrayList<Agent> generateAgents(int numberOfAgents){
        ArrayList<Agent> agents = new ArrayList<>();
        for(int n = 0; n < numberOfAgents/2; n++){
            Agent newAgent = new UninfFwdLevelAgent(
                    population,
                    false);
            agents.add(newAgent);
            newAgent = new UninfFwdDeltaAgent(
                    population,
                    false);
            agents.add(newAgent);
        }
        return agents;
    }

}

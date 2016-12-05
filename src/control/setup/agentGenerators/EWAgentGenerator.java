package control.setup.agentGenerators;

import agents.*;
import control.Simulation;

import java.util.ArrayList;

/**
 * Created by Emily on 11/30/2016.
 */
public class EWAgentGenerator extends AgentGenerator {
    private Simulation sim;
    private AgentPopulation population;

    public EWAgentGenerator(AgentPopulation population, Simulation sim) {
        this.sim = sim;
        this.population = population;
    }

    public ArrayList<Agent> generateAgents(int numberOfAgents){
        ArrayList<Agent> agents = new ArrayList<>();
        for(int n = 0; n < 9; n++){
            Agent uninflevel = new UninfFwdLevelAgent(
                    population,
                    true);
            agents.add(uninflevel);
            Agent uninfdelta = new UninfFwdDeltaAgent(
                    population,
                    true);
            agents.add(uninfdelta);
        }

        Agent inf = new InfBckAgent(
                population,
                true);
        agents.add(inf);

        inf = new InfBckAgent(
                population,
                true);
        agents.add(inf);

        return agents;
    }

}

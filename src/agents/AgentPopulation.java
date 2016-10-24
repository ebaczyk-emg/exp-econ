package agents;

import control.Simulation;
import control.config.Config;

import java.util.ArrayList;

/**
 * Created by Emily on 10/24/2016.
 */
public class AgentPopulation {
    private Simulation sim;
    private ArrayList<Agent> agents;
    private static final int ID_START = 1;
    private int ID;

    public AgentPopulation(Simulation sim) {
        this.sim = sim;
        agents = new ArrayList<>(sim.getConfig().getnAgents());
        ID = ID_START;
    }

    public void init(Agent agent) {
        agent.setID(ID);
        ID++;
        agents.add(agent);
        System.out.println(agent.getID());
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public Config getConfig() {
        return sim.getConfig();
    }
}

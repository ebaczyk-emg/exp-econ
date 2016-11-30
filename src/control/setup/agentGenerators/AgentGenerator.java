package control.setup.agentGenerators;

import agents.Agent;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class AgentGenerator {

    public abstract ArrayList<Agent> generateAgents(int numAgents);
}

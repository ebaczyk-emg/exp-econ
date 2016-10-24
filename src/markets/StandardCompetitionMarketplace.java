package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import control.Simulation;
import control.assetGenerators.AssetGenerator;
import control.brainAllocators.BrainAllocator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Emily on 9/28/2016.
 */
public class StandardCompetitionMarketplace extends Marketplace{
    private Simulation sim;
    private AgentPopulation agentPopulation;
    ArrayList<Agent> agents = new ArrayList<>();
    ArrayList<Asset> assets = new ArrayList<>();
    int numAgents;

    ArrayList<Double> pastTransactionPrices = new ArrayList<>();

    public StandardCompetitionMarketplace(BrainAllocator brainAllocator,
                                          AssetGenerator assetGenerator,
                                          int numAgents,
                                          Simulation sim){
        this.sim = sim;
        this.numAgents = numAgents;

        agentPopulation = sim.getPopulation();
        this.agents = brainAllocator.generateAgents(numAgents);
        for(Agent agent : agents) {
            agentPopulation.init(agent);
        }

        this.assets = assetGenerator.generateAssets(1);
    }

    public boolean runOneStep() {
        HashMap<Double, Agent> bids = new HashMap<>();
        HashMap<Double, Agent> offers = new HashMap<>();

        for(Agent agent : agents) {
            agent.getFundamentalValue();

            //collect all agents' bids and asks
            if(Math.random() > 0.5d) {
                //the agent has been selected to be a buyer
                bids.put(agent.getBid(), agent);
            }  else {
                offers.put(agent.getOffer(), agent);
            }
            //sort them
            ArrayList<Double> sortedBids = new ArrayList<Double>(bids.keySet());
            Collections.sort(sortedBids);
            ArrayList<Double> sortedOffers = new ArrayList<Double>(offers.keySet());
            Collections.sort(sortedOffers);


        }
        return true;
    }

    public ArrayList<Double> getPastTransactionPrices() {
        return this.pastTransactionPrices;
    }

}

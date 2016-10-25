package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;
import control.assetGenerators.AssetGenerator;
import control.brainAllocators.BrainAllocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Emily on 9/28/2016.
 */
public class StandardCompetitionMarketplace extends Marketplace{
    private Simulation sim;
    private AgentPopulation agentPopulation;
    private AssetRegistry registry;
    ArrayList<Agent> agents = new ArrayList<>();
    ArrayList<Asset> assets = new ArrayList<>();
    int numAgents;

    private double activeBid;
    private double activeOffer;

    ArrayList<Double> pastTransactionPrices = new ArrayList<>();

    public StandardCompetitionMarketplace(BrainAllocator brainAllocator,
                                          AssetGenerator assetGenerator,
                                          int numAgents,
                                          Simulation sim){
        this.sim = sim;
        this.numAgents = numAgents;

        this.agentPopulation = sim.getPopulation();
        this.agents = brainAllocator.generateAgents(numAgents);
        for(Agent agent : agents) {
            agentPopulation.init(agent);
        }
        this.agents = new ArrayList<>(agentPopulation.getAgents());

        this.registry = sim.getAssetRegistry();
        this.assets = assetGenerator.generateAssets((sim.getConfig().getnAgents() *
                sim.getConfig().getInitAssetEndowment()));
        for(Asset asset : assets) {
            registry.init(asset);
        }
        this.assets = new ArrayList<>(registry.getAssets());

        this.initializeAssetAllocation();

        this.activeBid = sim.getConfig().getMinAssetValue();
        this.activeOffer = sim.getConfig().getMaxAssetValue();
    }

    public boolean initializeAssetAllocation() {
        ArrayList<Asset> unallocatedAssets = new ArrayList<>(assets);
        for(Agent agent : agents) {
            for(int i = 0; i < sim.getConfig().getInitAssetEndowment(); i++) {
                int index = (int) Math.floor(Math.random() * unallocatedAssets.size());
                System.out.println(index);
                agent.endowAsset(unallocatedAssets.get(index));
                unallocatedAssets.remove(index);
            }
        }
        assert unallocatedAssets.size() == 0: "assets were created and were not allocated";
        return true;
    }

    public boolean runOneStep() {
        HashMap<Double, Agent> bids = new HashMap<>();
        HashMap<Double, Agent> offers = new HashMap<>();
        Agent actingAgent = agents.get((int) Math.floor(Math.random() * agents.size()));
        if(Math.random() > 0.5d) {
            //the agent is a buyer
            double actingAgentBid = actingAgent.getBid();
        } else {
            double actingAgentOffer = actingAgent.getOffer();
        }

        //fuck this shit

        if(activeBid >= activeOffer) {

        }





        for(Agent agent : agents) {
            //agent.getFundamentalValue();
            if(agent.getID().equals("Agent1")) {
//                System.out.println("here");
                System.out.println(agent.getOwnedAssets().get(0).getID() + " " + agent.getOwnedAssets().get(0).getIntrinsicValue() +
                " " + agent.getOwnedAssets().get(1).getID() + " " + agent.getOwnedAssets().get(1).getIntrinsicValue());
            }

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

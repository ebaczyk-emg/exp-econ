package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;
import control.assetGenerators.AssetGenerator;
import control.brainAllocators.BrainAllocator;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

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

    private Bid activeBid;
    private Offer activeOffer;
    private PriorityQueue<Bid> bids;
    private PriorityQueue<Offer> offers;

    ArrayList<Double> pastTransactionPrices = new ArrayList<>();
    ArrayList<Double> allBidsThisMonth;

    int[] indices;

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

        this.activeBid = new Bid(null, sim.getConfig().getMinAssetValue());
        this.activeOffer = new Offer(null, sim.getConfig().getMaxAssetValue(), null);
        bids = new PriorityQueue<>();
        offers = new PriorityQueue<>();
        bids.add(activeBid);
        offers.add(activeOffer);

        this.setAgentOrder();
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

        for(Agent agent : agents) {
            agent.endowCash(sim.getConfig().getInitCashEndowment());
        }

        return true;
    }

    public boolean runOneStep() {
        for(int i=0; i < agents.size(); i++) {
            Agent actingAgent = agents.get(indices[i]);
            if (Math.random() > 0.5d) {
                //the agent is a buyer
                Bid actingAgentBid = actingAgent.getBid();
                System.out.println(actingAgentBid);
                if (actingAgentBid.getBidPrice() > activeBid.getBidPrice()) {
                    bids.add(actingAgentBid);
                    activeBid = actingAgentBid;
                }
            } else {
                Offer actingAgentOffer = actingAgent.getOffer();
                System.out.println(actingAgentOffer);
                if (actingAgentOffer.getOfferPrice() < activeOffer.getOfferPrice()) {
                    offers.add(actingAgentOffer);
                    activeOffer = actingAgentOffer;
                }
            }

            System.out.println(activeBid + " " + activeOffer);

            if (activeBid.getBidPrice() >= activeOffer.getOfferPrice()) {
                //a transaction has occurred
                double price = (activeBid.getBidPrice() + activeOffer.getOfferPrice()) / 2;
                System.out.println("TRANSACTION at price " + price);
                activeBid.getBiddingAgent().buyAsset(activeOffer.getOfferedAsset(), price);
                activeOffer.getOfferingAgent().sellAsset(activeOffer.getOfferedAsset(), price);

                bids = new PriorityQueue<>();
                offers = new PriorityQueue<>();

                bids.add(new Bid(null, sim.getConfig().getMinAssetValue()));
                offers.add(new Offer(null, sim.getConfig().getMaxAssetValue(), null));

                activeBid = bids.peek();
                activeOffer = offers.peek();
//                if(offers.isEmpty()) {
//                    offers.add(new Offer(null, sim.getConfig().getMaxAssetValue(), null));
//                } else {
//                    offers.remove();
//                }
//                if(bids.isEmpty()) {
//                    bids.add(new Bid(null, sim.getConfig().getMinAssetValue()));
//                } else {
//                    bids.remove();
//                }
            }


        }


        return true;
    }

    public void setAgentOrder() {
        indices = new int[agents.size()];
        ArrayList<Agent> temp = new ArrayList<>(agents);
        Collections.shuffle(temp);
        for(int i = 0; i < agents.size(); i++) {
            indices[i] = temp.indexOf(agents.get(i));
        }
    }

    public ArrayList<Double> getPastTransactionPrices() {
        return this.pastTransactionPrices;
    }

}

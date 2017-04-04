package markets;

import agents.Agent;
import assets.Asset;
import control.Simulation;
import control.setup.assetGenerators.AssetGenerator;
import control.setup.agentGenerators.AgentGenerator;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import control.output.MarketState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by Emily on 9/28/2016.
 */
public class StandardCompetitionMarketplace extends Marketplace{

    public StandardCompetitionMarketplace(AgentGenerator brainAllocator,
                                          AssetGenerator assetGenerator,
                                          Simulation sim){
        super(brainAllocator,
                assetGenerator,
                sim);

        this.initializeAssetAllocation();
        this.setAgentOrder();
    }

    public boolean initializeAssetAllocation() {
        ArrayList<Asset> unallocatedAssets = new ArrayList<>(assets);
        for(Agent agent : agents) {
            for(int i = 0; i < sim.getConfig().getInitAssetEndowment(); i++) {
                int index = (int) Math.floor(sim.getRandom().nextDouble() * unallocatedAssets.size());
                agent.endowAssetAtInit(unallocatedAssets.get(index));
                unallocatedAssets.remove(index);
            }
        }
        assert unallocatedAssets.size() == 0: "assets were created and were not allocated";

        for(Agent agent : agents) {
            agent.endowCash(sim.getConfig().getInitCashEndowment());
        }

        return true;
    }

    @Override
    public boolean runOneStep() {
        statesThisMonth = new ArrayList<>();

        for(int i=0; i < agents.size(); i++) {
            Agent actingAgent = agents.get(indices[i]);
            if (sim.getRandom().nextBoolean()) {
                //the agent is a buyer
                Bid actingAgentBid = actingAgent.getBid();
                if(actingAgentBid != null) {
                    if (actingAgentBid.getBidPrice() > activeBid.getBidPrice()) {
                        bids.add(actingAgentBid);
                        activeBid = actingAgentBid;
                    }
                }
            } else {
                Offer actingAgentOffer = actingAgent.getOffer();
                if(actingAgentOffer != null) {
                    if (actingAgentOffer.getOfferPrice() < activeOffer.getOfferPrice()) {
                        offers.add(actingAgentOffer);
                        activeOffer = actingAgentOffer;
                    }
                }
            }

            System.out.println("ACTIVE BIDS " + activeBid + " " + activeOffer);

            if (activeBid.getBidPrice() >= activeOffer.getOfferPrice()) {
                //a transaction has occurred
                statesThisMonth.add(new MarketState(
                        sim.getPeriod(),
                        sim.getStep(),
                        activeBid.getBidPrice(),
                        activeBid.getBiddingAgent(),
                        activeOffer.getOfferPrice(),
                        activeOffer.getOfferingAgent(),
                        true));

                double price = (activeBid.getBidPrice() + activeOffer.getOfferPrice()) / 2;
                System.out.println("TRANSACTION at price " + price);
                activeOffer.getOfferingAgent().sellAsset(activeOffer.getOfferedAsset(), price);
                activeBid.getBiddingAgent().buyAsset(activeOffer.getOfferedAsset(), price);

                assert (activeOffer.getOfferedAsset().getOwner() == activeBid.getBiddingAgent()):
                        "mismatch in asset transaction";

                bids = new PriorityQueue<>();
                offers = new PriorityQueue<>();

                bids.add(new Bid(null, sim.getConfig().getMinAssetValue()));
                offers.add(new Offer(null, sim.getConfig().getMaxAssetValue(), null));

                activeBid = bids.peek();
                activeOffer = offers.peek();
            } else {
                statesThisMonth.add(new MarketState(
                        sim.getPeriod(),
                        sim.getStep(),
                        activeBid.getBidPrice(),
                        activeBid.getBiddingAgent(),
                        activeOffer.getOfferPrice(),
                        activeOffer.getOfferingAgent(),
                        false));
            }

        }
        return true;
    }

    public void setAgentOrder() {
        Collections.shuffle(agents);
//        indices = new int[agents.size()];
//        ArrayList<Agent> temp = new ArrayList<>(agents);
//        Collections.shuffle(temp);
//        for(int i = 0; i < agents.size(); i++) {
//            indices[i] = temp.indexOf(agents.get(i));
//        }
    }

}

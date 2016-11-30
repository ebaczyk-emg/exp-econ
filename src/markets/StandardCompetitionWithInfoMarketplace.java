package markets;

import agents.Agent;
import assets.Asset;
import control.Simulation;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import control.output.MarketState;
import control.setup.assetGenerators.AssetGenerator;
import control.setup.agentGenerators.AgentGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by Emily on 9/28/2016.
 */
public class StandardCompetitionWithInfoMarketplace extends Marketplace{

    ArrayList<Boolean> releasedInfo;

    public StandardCompetitionWithInfoMarketplace(AgentGenerator brainAllocator,
                                                  AssetGenerator assetGenerator,
                                                  Simulation sim){
        super(brainAllocator,
                assetGenerator,
                sim);

        this.initializeAssetAllocation();
        this.setAgentOrder();

        releasedInfo = new ArrayList<>();
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

        this.releaseNewInformation();

        for(int i=0; i < agents.size(); i++) {
            Agent actingAgent = agents.get(indices[i]);
            if (sim.getRandom().nextBoolean()) {
                //the agent is a buyer
                Bid actingAgentBid = actingAgent.getBid();
                System.out.println(actingAgentBid);
                if(actingAgentBid != null) {
                    if (actingAgentBid.getBidPrice() > activeBid.getBidPrice()) {
                        bids.add(actingAgentBid);
                        activeBid = actingAgentBid;
                    }
                }
            } else {
                Offer actingAgentOffer = actingAgent.getOffer();
                System.out.println(actingAgentOffer);
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
        indices = new int[agents.size()];
        ArrayList<Agent> temp = new ArrayList<>(agents);
        Collections.shuffle(temp);
        for(int i = 0; i < agents.size(); i++) {
            indices[i] = temp.indexOf(agents.get(i));
        }
    }

    private void releaseNewInformation() {
        boolean coinFlip = sim.getRandom().nextBoolean(sim.getConfig().getInfoPStateA());
        this.releasedInfo.add(coinFlip);
    }

    @Override
    public void endOfPeriodReset() {
        super.endOfPeriodReset();
        releasedInfo = new ArrayList<>();
    }
}

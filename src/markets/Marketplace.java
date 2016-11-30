package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;
import control.setup.assetGenerators.AssetGenerator;
import control.setup.brainAllocators.BrainAllocator;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import control.output.MarketState;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Marketplace {

    Simulation sim;
    AgentPopulation agentPopulation;
    AssetRegistry registry;
    ArrayList<Agent> agents = new ArrayList<>();
    ArrayList<Asset> assets = new ArrayList<>();

    Bid activeBid;
    Offer activeOffer;
    PriorityQueue<Bid> bids;
    PriorityQueue<Offer> offers;

    ArrayList<Double> pastTransactionPrices = new ArrayList<>();
    ArrayList<MarketState> statesThisMonth;

    int[] indices;
    
    public Marketplace(BrainAllocator brainAllocator,
                       AssetGenerator assetGenerator,
                       Simulation sim) {
        this.sim = sim;

        this.agentPopulation = sim.getPopulation();
        this.agents = brainAllocator.generateAgents(sim.getConfig().getnAgents());
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

        this.activeBid = new Bid(null, sim.getConfig().getMinAssetValue());
        this.activeOffer = new Offer(null, sim.getConfig().getMaxAssetValue(), null);
        bids = new PriorityQueue<>();
        offers = new PriorityQueue<>();
        bids.add(activeBid);
        offers.add(activeOffer);
    }

    public void payDividends() {
        System.out.println("Paying Dividends for period " + sim.getPeriod());
        for(Asset asset : assets) {
            asset.payDividend();
        }
        //set dividends for next period
        double newDividend = registry.determineDividend();
        System.out.println("NEW DIVIDEND IS " + newDividend);
        for(Asset asset : assets) {
            asset.setDividend(newDividend);
        }
    }

    public abstract boolean runOneStep();

    public ArrayList<Double> getPastTransactionPrices() {
        return this.pastTransactionPrices;
    }

    public ArrayList<MarketState> getStatesThisMonth() {
        return this.statesThisMonth;
    }
}

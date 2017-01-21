package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;
import control.setup.assetGenerators.AssetGenerator;
import control.setup.agentGenerators.AgentGenerator;
import control.marketObjects.Bid;
import control.marketObjects.Offer;
import control.output.MarketState;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Marketplace {

    private AgentGenerator agentGen;
    private AssetGenerator assetGen;
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
    ArrayList<Boolean> releasedInfo;

    int[] indices;

    double maxBid, minOffer;
    
    public Marketplace(AgentGenerator brainAllocator,
                       AssetGenerator assetGenerator,
                       Simulation sim) {
        this.agentGen = brainAllocator;
        this.assetGen = assetGenerator;
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

        releasedInfo = new ArrayList<>();

        this.maxBid = sim.getConfig().getMinAssetValue();
        this.minOffer = sim.getConfig().getMaxAssetValue();
    }

    public void payDividends() {
        System.out.println("Paying Dividends for period " + sim.getPeriod());
//        for(Asset asset : assets) {
//            asset.payDividend();
//        }
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

    public void endOfPeriodReset() {
        this.maxBid = sim.getConfig().getMinAssetValue();
        this.minOffer = sim.getConfig().getMaxAssetValue();
    }


    public ArrayList<Boolean> getReleasedInfo() {
        return this.releasedInfo;
    }

    public double getMaxBid() {
        return this.maxBid;
    }

    public double getMinOffer() {
        return this.minOffer;
    }
}

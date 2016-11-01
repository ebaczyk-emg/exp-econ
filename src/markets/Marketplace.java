package markets;

import agents.Agent;
import agents.AgentPopulation;
import assets.Asset;
import assets.AssetRegistry;
import control.Simulation;
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
    int numAgents;

    Bid activeBid;
    Offer activeOffer;
    PriorityQueue<Bid> bids;
    PriorityQueue<Offer> offers;

    ArrayList<Double> pastTransactionPrices = new ArrayList<>();
    ArrayList<MarketState> statesThisMonth;

    int[] indices;
    
    
    public abstract boolean runOneStep();


    public ArrayList<Double> getPastTransactionPrices() {
        return this.pastTransactionPrices;
    }

    public ArrayList<MarketState> getStatesThisMonth() {
        return this.statesThisMonth;
    }
}

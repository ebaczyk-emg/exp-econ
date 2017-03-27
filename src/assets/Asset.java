package assets;

import agents.Agent;
import control.marketObjects.Bid;

import java.util.ArrayList;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class Asset implements Comparable {
    private AssetRegistry registry;
    private double fundingCost;
    private int id;
    Agent owner;
    double dividend;

    public Asset(AssetRegistry registry,
                 double fundingCost) {
        this.registry = registry;
        this.fundingCost = fundingCost;
        this.dividend = 0d;
    }

    public void payDividend() {
        this.owner.
                endowCash(
                        dividend);
    }

    public int compareTo(Object a) {
        if(((Asset) a).getFundingCost() < this.getFundingCost()) {
            return -1;
        } else {
            return 1;
        }
    }


    public double getFundingCost(){
        return this.fundingCost;
    }

    public String getID() {
        return "Asset" + id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setOwner(Agent agent) {
        this.owner = agent;
    }

    public Agent getOwner() {
        return owner;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public ArrayList<Double> getPastPrices() {
        return registry.getMarketplace().getPastTransactionPrices();
    }

    public void setFundingCost(double cost) {
        this.fundingCost = cost;
    }
}

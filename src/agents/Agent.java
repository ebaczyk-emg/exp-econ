package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Agent {
    private AgentPopulation population;
    private int id;
    ArrayList<Asset> assetEndowment;
    double cashEndowment;

    public abstract double getFundamentalValue(Asset a);
    public abstract Bid getBid();
    public abstract Offer getOffer();

    public Agent(AgentPopulation population){
        this.population = population;
    }

    public Bid getBid(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedBid = calculatedFairValue -
                population.getConfig().getDecayFactor() *
                        Math.exp(population.getConfig().getDecayFactor() *
                                -1d ); //some amount less than you think it's worth
        System.out.println("calculated to bid " + calculatedBid + " from FV " + calculatedFairValue);
        if(calculatedBid > cashEndowment) {
            return new Bid(this, population.getConfig().getMinAssetValue());
        } else {
            return new Bid(this, calculatedBid);
        }

    }

    public Offer getOffer(Asset a) {
        double calculatedFairValue = this.getFundamentalValue(a);
        double calculatedOffer = Math.random()*20 + calculatedFairValue; // some amount more than you think it's worth
        if(assetEndowment.size() == 0) {
            return new Offer(this, population.getConfig().getMaxAssetValue(), a);
        } else {
            return new Offer(this, calculatedOffer, a);
        }
    }

    public void buyAsset(Asset a, double price) {
        this.debit(price);
        this.endowAsset(a);
    }

    public void sellAsset(Asset a, double price) {
        this.credit(price);
        this.unendowAsset(a);
    }

    public void endowAsset(Asset a) {
        assert (a.getOwner() == null);
        assetEndowment.add(a);
        a.setOwner(this);
    }

    public void unendowAsset(Asset a) {
        assert (a.getOwner() == this);
        assetEndowment.remove(a);
        a.setOwner(null);
    }

    public void endowCash(double amount) {
        this.credit(amount);
    }

    private void credit(double amount) {
        cashEndowment += amount;
    }

    private void debit(double amount) {
        cashEndowment -= amount;
    }

    public int getAssetEndowment() {
        return this.assetEndowment.size();
    }

    public ArrayList<Asset> getOwnedAssets() {
        return this.assetEndowment;
    }

    public double getCashEndowment() {
        return this.cashEndowment;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getID() {
        return "Agent" + this.id;
    }

    public String printEndowment(){
        String ret = "Agent" + id + "," + cashEndowment;
        for(Asset a : assetEndowment) {
            ret += "," + a.getID() + "," + a.getIntrinsicValue();
        }
        return ret;
    }
}

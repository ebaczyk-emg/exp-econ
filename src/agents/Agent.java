package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Agent {
    AgentPopulation population;
    private int id;
    private boolean informed;
    ArrayList<Asset> assetEndowment;
    double cashEndowment;

    ArrayList<Double> valuesForAllPeriods;

    public abstract double calculateFairValue(Asset a);
    public abstract Bid getBid();
    public abstract Offer getOffer();

    public Agent(AgentPopulation population,
                 boolean isInformed){
        this.population = population;
        this.informed = isInformed;
    }

    public Bid getBid(double max) {
        double calculatedFairValue = max;
        double calculatedBid = calculatedFairValue -
                Math.exp(population.getRandom().nextDouble() * population.getConfig().getDecayFactor()); //some amount less than you think it's worth
//        System.out.println("calculated to bid " + calculatedBid + " from FV " + calculatedFairValue);
        if(calculatedBid > cashEndowment) {
            return new Bid(this, cashEndowment);
        } else {
//            return new Bid(this, Math.min(population.getMarket().getMinOffer(), calculatedBid));
            return new Bid(this, max);
            //return new Bid(this, calculatedBid);
        }

    }

    public Offer getOffer(Asset a) {
        double calculatedFairValue = this.calculateFairValue(a);
        double calculatedOffer = calculatedFairValue +
                Math.exp(population.getRandom().nextDouble() * population.getConfig().getDecayFactor()); //some amount more than you think it's worth
//        System.out.println(calculatedFairValue + " *** " + calculatedOffer);
//        System.out.println(population.getMarket().getMinOffer());
//        System.exit(11);
        if(calculatedOffer < a.getFundingCost() || this.getOwnedAssets().isEmpty()) {
            return new Offer(this, population.getConfig().getMaxAssetValue(), a);
        } else {
//            return new Offer(this, Math.max(population.getMarket().getMaxBid(), calculatedOffer), a);
            return new Offer(this, calculatedFairValue, a);
            //return new Offer(this, calculatedOffer, a);

        }
    }

    public void buyAsset(Asset a, double price) {
        this.debit(price);
        a.setFundingCost(price);
        this.endowAsset(a);
    }

    public void sellAsset(Asset a, double price) {
        this.credit(price);
        this.unendowAsset(a);
    }

    public void endowAsset(Asset a) {
        assetEndowment.add(a);
        a.setOwner(this);
        assert (a.getOwner() == this);
    }

    public void unendowAsset(Asset a) {
        assert (a.getOwner() != null);
        assetEndowment.remove(a);
        a.setOwner(null);
        assert (a.getOwner() == null);
    }

    public void endowAssetAtInit(Asset a) {
        assert (a.getOwner() == null);
        assetEndowment.add(a);
        a.setOwner(this);
        assert (a.getOwner() == this);
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

    public boolean isInformed() {
        return informed;
    }

    public String printEndowment(){
        String ret = "Agent" + id + "," + cashEndowment;
        for(Asset a : assetEndowment) {
            ret += "," + a.getID() + "," + a.getFundingCost();
        }
        return ret;
    }
}

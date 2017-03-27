package agents;

import assets.Asset;
import control.marketObjects.Bid;
import control.marketObjects.Offer;

import java.util.ArrayList;
import java.util.Collections;

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

    public Agent(AgentPopulation population,
                 boolean isInformed){
        this.population = population;
        this.informed = isInformed;
    }

    public Bid getBid() {
        Bid bid = getBid(this.calculateFairValue(null));
        return bid;
    }

    public Offer getOffer() {
        if(assetEndowment.size() > 0) {
            Asset assetToSell = assetEndowment.get(population.getRandom().nextInt(getAssetEndowment()));
            if(this.getAverageFundingCost() <= this.calculateFairValue(null)){
                return new Offer(this, this.calculateFairValue(assetToSell), assetToSell);
            } else {
                return null;
//                return new Offer(this, this.calculateFairValue(null), assetToSell);
            }
//            Collections.sort(assetEndowment); //sort endowment decreasing on funding cost
//            System.out.println(assetEndowment.get(0).getFundingCost());
//            for (int i=0; i < assetEndowment.size(); i++) {
//                if (assetEndowment.get(i).getFundingCost() <= this.calculateFairValue(null)) {
//                    if(i==0) {
//                        return this.getOffer(assetEndowment.get(0));
//                    } else {
//                        return this.getOffer(assetEndowment.get(i-1));
//                    }
//                }
//            }
//            if(assetToSell == null) {
//                return this.getOffer(assetEndowment.get(assetEndowment.size() - 1));
//            } else {
//                System.err.println("something is wrong in offer code");
//                System.exit(11);
//                return null;
//            }
        }
        else return null;
    }

    public Bid getBid(double max) {
        double calculatedFairValue = max;
        double calculatedBid = calculatedFairValue -
                Math.exp(population.getRandom().nextDouble() * population.getConfig().getDecayFactor()); //some amount less than you think it's worth
        if(calculatedFairValue > cashEndowment) {
            return new Bid(this, cashEndowment);
        } else {
            return new Bid(this, calculatedBid);
//            return new Bid(this, calculatedFairValue);
        }

    }

    public Offer getOffer(Asset a) {
        double calculatedFairValue = this.calculateFairValue(a);
        if(calculatedFairValue >= a.getFundingCost()) {
            //some amount more than you think it's worth
            double calculatedOffer = calculatedFairValue +
                    Math.exp(population.getRandom().nextDouble() * population.getConfig().getDecayFactor());
            if (calculatedFairValue < a.getFundingCost() || this.getOwnedAssets().isEmpty()) {
                return new Offer(this, population.getConfig().getMaxAssetValue(), a);
            } else {
//            return new Offer(this, calculatedFairValue, a);
                return new Offer(this, calculatedOffer, a);
            }
        } else {
            return new Offer(this, this.calculateFairValue(null), a);
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

    public double getAverageFundingCost() {
        double averageFundingCost = 0;
        for(Asset a: this.getOwnedAssets()) {
            averageFundingCost += a.getFundingCost();
        }
        return(averageFundingCost/Math.max(1,this.getAssetEndowment()));
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

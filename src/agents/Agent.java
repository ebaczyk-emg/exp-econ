package agents;

import assets.Asset;

import java.util.ArrayList;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Agent {
    private int id;
    private ArrayList<Asset> assetEndowment;
    private double cashEndowment;

    public abstract double getFundamentalValue();
    public abstract double getBid();
    public abstract double getOffer();

    public void endowAsset(Asset a) {
        assert (a.getOwner() == null);
        assetEndowment.add(a);
    }

    public void credit(double amount) {
        cashEndowment += amount;
    }

    public void debit(double amount) {
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


}

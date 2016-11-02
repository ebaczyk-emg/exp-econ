package assets;

import agents.Agent;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class Asset {
    AssetRegistry registry;
    double intrinsicValue;
    private int id;
    Agent owner;

    double dividend;

    public Asset(AssetRegistry registry,
                 double intrinsicValue) {
        this.registry = registry;
        this.intrinsicValue = intrinsicValue;
    }

    public double getIntrinsicValue(){
        return this.intrinsicValue;
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
}

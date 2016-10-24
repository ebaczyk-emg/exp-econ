package assets;

import agents.Agent;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class Asset {

    private double intrinsicValue;
    private String state;
    private int id;
    public Agent owner;

    public double getIntrinsicValue(){
        return this.intrinsicValue;
    }

    public String getState(){
        return this.state;
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
}

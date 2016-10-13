package agents;

/**
 * Created by Emily on 9/28/2016.
 */
public abstract class Agent {

    public abstract double getFundamentalValue();
    public abstract double getBid();
    public abstract double getOffer();

    public abstract int getAssetEndowment();
    public abstract double getCashEndowment();


}

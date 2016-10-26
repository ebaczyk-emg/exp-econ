package control.marketObjects;

import agents.Agent;
import assets.Asset;

/**
 * Created by Emily on 10/24/2016.
 */
public class Offer implements Comparable{
    private Agent offeringAgent;
    private double offerPrice;
    private Asset offeredAsset;

    public Offer(Agent offeringAgent,
                 double offerPrice,
                 Asset offeredAsset) {
        this.offeredAsset = offeredAsset;
        this.offeringAgent = offeringAgent;
        this.offerPrice = offerPrice;
    }

    public Agent getOfferingAgent() {
        return offeringAgent;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public Asset getOfferedAsset() {
        return offeredAsset;
    }

    public int compareTo(Object a) {
        if(((Offer) a).getOfferPrice() > this.getOfferPrice()) {
            return -1;
        } else {
            return 1;
        }
    }

    public String toString() {
        String ret = "";
        if (offeringAgent == null) {
            ret = "Offer of " + offerPrice +
                    " by PLACEHOLDER"  +
                    " for PLACEHOLDER";
        }
        else {
            ret = "Offer of " + offerPrice +
                    " by " + offeringAgent.getID() +
                    " for " + offeredAsset.getID();
        }
        return ret;
    }
}

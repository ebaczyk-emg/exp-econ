package control.marketObjects;

import agents.Agent;
import assets.Asset;

/**
 * Created by Emily on 10/24/2016.
 */
public class Offer {
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
}

package control.marketObjects;

import agents.Agent;
import assets.Asset;

/**
 * Created by Emily on 10/24/2016.
 */
public class Transaction {
    private Agent buyer;
    private Agent seller;
    private double price;
    private Asset asset;

    public Transaction(Agent b,
                       Agent s,
                       double p,
                       Asset a) {
        this.buyer = b;
        this.seller = s;
        this.price = p;
        this.asset = a;
    }

    public Agent getBuyer() {
        return buyer;
    }

    public Agent getSeller() {
        return seller;
    }

    public double getPrice() {
        return price;
    }

    public Asset getAsset() {
        return asset;
    }
}

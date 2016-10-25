package control.marketObjects;

import agents.Agent;

/**
 * Created by Emily on 10/24/2016.
 */
public class Bid {
    private Agent biddingAgent;
    private double bidPrice;

    public Bid(Agent bidder,
               double bidPrice) {
        this.biddingAgent = bidder;
        this.bidPrice = bidPrice;
    }

    public Agent getBiddingAgent() {
        return biddingAgent;
    }

    public double getBidPrice() {
        return bidPrice;
    }

}

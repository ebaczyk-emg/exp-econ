package control.marketObjects;

import agents.Agent;

/**
 * Created by Emily on 10/24/2016.
 */
public class Bid implements Comparable{
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

    public int compareTo(Object a) {
        if(((Bid) a).getBidPrice() < this.getBidPrice()) {
            return -1;
        } else {
            return 1;
        }
    }

    public String toString() {
        String ret = "";
        if(biddingAgent == null) {
            ret = "Bid of " + bidPrice + " by PLACEHOLDER";
        }  else {
            ret = "Bid of " + bidPrice + " by " + biddingAgent.getID();
        }
        return ret;
    }

    public String toPrint() {
        String ret = "";
        if(biddingAgent == null) {
            ret = bidPrice + ",NA";
        }  else {
            ret = bidPrice + "," + biddingAgent.getID();
        }
        return ret;
    }
}

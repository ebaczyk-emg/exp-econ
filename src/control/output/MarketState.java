package control.output;

import agents.Agent;

import java.util.ArrayList;

/**
 * Created by Emily on 10/29/2016.
 */
public class MarketState {
    double bidPrice, askPrice;
    String bidderID, askerID;
    boolean transaction;

    public MarketState(double bidPrice,
                       Agent bidder,
                       double askPrice,
                       Agent asker,
                       boolean transaction) {
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.transaction = transaction;
        if (asker == null) {
            this.askerID = "NA";
        } else {
            this.askerID = asker.getID();
        }
        if (bidder == null) {
            this.bidderID = "NA";
        } else {
            this.bidderID = bidder.getID();
        }
    }

    public ArrayList<String> toPrint() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(bidPrice + "");
        ret.add(bidderID);
        ret.add(askPrice + "");
        ret.add(askerID);
        ret.add(transaction + "");
        return ret;
    }
}

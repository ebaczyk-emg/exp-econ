package control.output;

import agents.Agent;

import java.util.ArrayList;

/**
 * Created by Emily on 10/29/2016.
 */
public class MarketState {
    double bidPrice, askPrice;
    String bidderID, askerID, bidderType, askerType;
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
            this.askerType = "NA";
        } else {
            this.askerID = asker.getID();
            this.askerType = asker.getClass().getSimpleName();
        }
        if (bidder == null) {
            this.bidderID = "NA";
            this.bidderType = "NA";
        } else {
            this.bidderID = bidder.getID();
            this.bidderType = bidder.getClass().getSimpleName();
        }
    }

    public ArrayList<String> toPrint() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(bidPrice + "");
        ret.add(bidderID);
        ret.add(bidderType);
        ret.add(askPrice + "");
        ret.add(askerID);
        ret.add(askerType);
        ret.add(transaction + "");
        return ret;
    }
}

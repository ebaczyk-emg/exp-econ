package control;

import markets.Marketplace;
import markets.StandardCompetitionMarketplace;

/**
 * Created by Emily on 9/28/2016.
 */
public class Simulation {

    Marketplace market;
    public Simulation() {
        market = new StandardCompetitionMarketplace();
        // figure out how to dynamically generate more markets
    }
}

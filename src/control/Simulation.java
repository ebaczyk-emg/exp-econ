package control;

import control.assetGenerators.AssetGenerator;
import control.assetGenerators.HomogeneousAssetGenerator;
import control.brainAllocators.BrainAllocator;
import control.brainAllocators.SimplestBrainAllocator;
import markets.Marketplace;
import markets.StandardCompetitionMarketplace;

/**
 * Created by Emily on 9/28/2016.
 */
public class Simulation {
    BrainAllocator brainAllocator;
    AssetGenerator assetGenerator;
    Marketplace market;
    private static final double INIT_CASH_ENDOWMENT = 1000;
    private static final int INIT_ASSET_ENDOWMENT = 2;

    public Simulation() {
        brainAllocator = new SimplestBrainAllocator();
        assetGenerator = new HomogeneousAssetGenerator();
        market = new StandardCompetitionMarketplace(brainAllocator,
                                                    assetGenerator,
                                                    100 );
        // figure out how to dynamically generate more markets

    }

    public static int getInitAssetEndowment() {
        return INIT_ASSET_ENDOWMENT;
    }

    public static double getInitCashEndowment() {
        return INIT_CASH_ENDOWMENT;
    }
}

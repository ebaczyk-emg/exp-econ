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
// hi

    public Simulation() {
        brainAllocator = new SimplestBrainAllocator();
        assetGenerator = new HomogeneousAssetGenerator();
        market = new StandardCompetitionMarketplace(brainAllocator,
                                                    assetGenerator,
                                                    100 );
        // figure out how to dynamically generate more markets
    }
}

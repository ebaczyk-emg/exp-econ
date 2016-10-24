package control;

import agents.AgentPopulation;
import assets.AssetRegistry;
import control.assetGenerators.AssetGenerator;
import control.assetGenerators.HomogeneousAssetGenerator;
import control.brainAllocators.BrainAllocator;
import control.brainAllocators.SimplestBrainAllocator;
import control.config.Config;
import markets.Marketplace;
import markets.StandardCompetitionMarketplace;

/**
 * Created by Emily on 9/28/2016.
 */
public class Simulation {
    BrainAllocator brainAllocator;
    AssetGenerator assetGenerator;
    Marketplace market;
    AgentPopulation population;
    AssetRegistry assetRegistry;

    private Config config;

    public Simulation() {
        this.config = new Config();
        this.population = new AgentPopulation(this);
        this.assetRegistry = new AssetRegistry(this);
        brainAllocator = new SimplestBrainAllocator(population, this);
        assetGenerator = new HomogeneousAssetGenerator(assetRegistry, this);
        market = new StandardCompetitionMarketplace(brainAllocator,
                                                    assetGenerator,
                                                    config.getnAgents(),
                                                    this);
        // figure out how to dynamically generate more markets
        System.out.println("market created");

        for(int i = 1; i <= config.getnSteps(); i++){
            market.runOneStep();
        }

    }

    public Config getConfig() {
        return config;
    }

    public AgentPopulation getPopulation() {
        return this.population;
    }

    public AssetRegistry getAssetRegistry() {
        return assetRegistry;
    }
}

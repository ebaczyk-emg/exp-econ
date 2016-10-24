package control;

import agents.AgentPopulation;
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

    private Config config;

    public Simulation() {
        this.config = new Config();
        this.population = new AgentPopulation(this);
        brainAllocator = new SimplestBrainAllocator(population, this);
        assetGenerator = new HomogeneousAssetGenerator(this);
        market = new StandardCompetitionMarketplace(brainAllocator,
                                                    assetGenerator,
                                                    config.getnAgents(),
                                                    this);
        // figure out how to dynamically generate more markets
        System.out.println("market created");

    }

    public Config getConfig() {
        return config;
    }

    public AgentPopulation getPopulation() {
        return this.population;
    }
}

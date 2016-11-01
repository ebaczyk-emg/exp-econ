package control;

import agents.AgentPopulation;
import assets.AssetRegistry;
import control.assetGenerators.AssetGenerator;
import control.assetGenerators.HomogeneousAssetGenerator;
import control.brainAllocators.BrainAllocator;
import control.brainAllocators.SimplestBrainAllocator;
import control.config.Config;
import control.output.OutputPrinter;
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
    OutputPrinter printer;

    private int step;
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
        printer = new OutputPrinter(config.getSystemPath(), this);

        //TODO figure out how to dynamically generate more markets
        System.out.println("Market created; starting to step");

        for(int i = 1; i <= config.getnSteps(); i++){
            step = i;
            market.runOneStep();
            printer.printOneStepOfOutput();
        }

        printer.closeWriters();
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

    public BrainAllocator getBrainAllocator() {
        return brainAllocator;
    }

    public Marketplace getMarket() {
        return market;
    }
}

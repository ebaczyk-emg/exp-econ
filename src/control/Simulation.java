package control;

import agents.AgentPopulation;
import assets.AssetRegistry;
import control.assetGenerators.AssetGenerator;
import control.assetGenerators.HomogeneousAssetGenerator;
import control.assetGenerators.MultiPeriodAssetGenerator;
import control.brainAllocators.BrainAllocator;
import control.brainAllocators.SimplestBrainAllocator;
import control.config.Config;
import control.output.OutputPrinter;
import markets.Marketplace;
import markets.StandardCompetitionMarketplace;
import util.MersenneTwisterFast;

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
    private int period;
    private int stepWithinPeriod;
    private Config config;
    private MersenneTwisterFast random;

    public Simulation(MersenneTwisterFast rng) {
        this.random = rng;
        this.config = new Config();
        this.population = new AgentPopulation(this);
        this.assetRegistry = new AssetRegistry(this);
        brainAllocator = new SimplestBrainAllocator(population, this);
        if(config.isUseMultiPeriodAsset()) {
            assetGenerator = new MultiPeriodAssetGenerator(assetRegistry, this);
        } else {
            assetGenerator = new HomogeneousAssetGenerator(assetRegistry, this);
        }
        market = new StandardCompetitionMarketplace(brainAllocator,
                                                    assetGenerator,
                                                    this);
        printer = new OutputPrinter(config.generateOutputPath(), this);

        //TODO figure out how to dynamically generate more markets
        System.out.println("Market created; starting to step");

        for(int i = 1; i <= config.getnDividendPeriods(); i++){
            market.payDividends();
            period++;
            
            for(int j = 1; j <+ config.getnStepsPerDividendPeriod(); j++) {
                step++;
                stepWithinPeriod = j;

                market.runOneStep();
                printer.printOneStepOfOutput();
            }
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

    public int getPeriod() {
        return period;
    }

    public int getStep() {
        return step;
    }

    public Marketplace getMarket() {
        return market;
    }

    public MersenneTwisterFast getRandom() {
        return this.random;
    }
}

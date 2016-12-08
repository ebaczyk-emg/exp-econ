package control;

import agents.AgentPopulation;
import assets.AssetRegistry;
import control.setup.agentGenerators.EWAgentGenerator;
import control.setup.agentGenerators.UninformedAgentGenerator;
import control.setup.assetGenerators.AssetGenerator;
import control.setup.assetGenerators.HomogeneousAssetGenerator;
import control.setup.agentGenerators.AgentGenerator;
import control.config.Config;
import control.output.OutputPrinter;
import control.setup.assetGenerators.MultiPeriodInfoAssetGenerator;
import markets.Marketplace;
import markets.StandardCompetitionWithInfoMarketplace;
import util.MersenneTwisterFast;

import java.util.Collections;

/**
 * Created by Emily on 9/28/2016.
 */
public class Simulation {
    AgentGenerator agentGenerator;
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
        agentGenerator = new EWAgentGenerator(population, this);
        Collections.shuffle(population.getAgents());
        if(config.isUseMultiPeriodAsset()) {
            assetGenerator = new MultiPeriodInfoAssetGenerator(assetRegistry, this);
        } else {
            assetGenerator = new HomogeneousAssetGenerator(assetRegistry, this);
        }
        market = new StandardCompetitionWithInfoMarketplace(agentGenerator,
                                                    assetGenerator,
                                                    this);
        printer = new OutputPrinter(config.generateOutputPath(), this);

        //TODO figure out how to dynamically generate more markets
        System.out.println("Market created; starting to step");

        for(int i = 1; i <= config.getnDividendPeriods(); i++){
            market.payDividends();
            period++;
            
            for(int j = 1; j <= config.getnStepsPerDividendPeriod(); j++) {
                step++;
                stepWithinPeriod = j;

                market.runOneStep();
                printer.printOneStepOfOutput();
            }
            market.endOfPeriodReset();
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

    public AgentGenerator getAgentGenerator() {
        return agentGenerator;
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

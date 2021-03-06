package control.output;

import agents.Agent;
import assets.Asset;
import control.Simulation;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Emily on 10/26/2016.
 */
public class OutputPrinter {
    private Simulation sim;
    private String path;
    private BufferedWriter transactionWriter;
    private BufferedWriter endowmentWriter;
    private BufferedWriter valuationWriter;

    public OutputPrinter(String systemPath, Simulation simulation){
        this.path = systemPath;
        this.sim = simulation;
        try {
            transactionWriter = new BufferedWriter(
                    new FileWriter(
                            new File(path + "/transactions-" + sim.getSimNumber() + ".csv")));
            endowmentWriter = new BufferedWriter(
                    new FileWriter(
                            new File(path + "/endowments-" + sim.getSimNumber() + ".csv")));
            valuationWriter = new BufferedWriter(
                    new FileWriter(
                            new File(path + "/valuations-" + sim.getSimNumber() + ".csv")));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(11);
        }
    }

    public void printOneStepOfOutput() {
        printTransactions();
        printEndowments();
        printValuations();
    }

    private void printTransactions() {
        for(MarketState state : sim.getMarket().getStatesThisMonth()) {
            try {
                writeSequenceWithNoTerminalComma(transactionWriter, state.toPrint());
                transactionWriter.flush();
            } catch (IOException ex) {
                System.err.println("Failed to print transaction records");
                ex.printStackTrace();
            }
        }
    }

    private void printEndowments() {
        for(Agent a : sim.getPopulation().getAgents()) {
            ArrayList<String> infoToAdd = new ArrayList<>();
            infoToAdd.add(a.getID());
            infoToAdd.add(a.getClass().getSimpleName());
            infoToAdd.add("" + a.getCashEndowment());
            infoToAdd.add("" + a.getAssetEndowment());
            double totalValueOfAssets = a.getCashEndowment();
            for(Asset asset : a.getOwnedAssets()){
                totalValueOfAssets += asset.getFundingCost();
            }
            infoToAdd.add("" + totalValueOfAssets);
            for(int i = 0; i < a.getAssetEndowment(); i++) {
                infoToAdd.add(a.getOwnedAssets().get(i).getID());
                infoToAdd.add("" + a.getOwnedAssets().get(i).getFundingCost());
            }
            try {
                writeSequenceWithNoTerminalComma(endowmentWriter, infoToAdd);
                endowmentWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void printValuations() {
        for(Agent a : sim.getPopulation().getAgents()){
            double valuationOfEndowment = 0;
            for(Asset asset : a.getOwnedAssets()){
                valuationOfEndowment += a.calculateFairValue(asset);
            }
            valuationOfEndowment = valuationOfEndowment/Math.max(1,a.getOwnedAssets().size());
            ArrayList<String> vals = new ArrayList<>();
            vals.add(a.getID());
            vals.add(a.getClass().getSimpleName());
            vals.add("" + a.calculateFairValue(null));
            vals.add("" + valuationOfEndowment);
            try{
                writeSequenceWithNoTerminalComma(valuationWriter, vals);
                valuationWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void writeSequenceWithNoTerminalComma(BufferedWriter writer, ArrayList<String> strings) throws IOException {
        int i;
        for (i = 0; i < strings.size() - 1; i++) {
            writer.append(strings.get(i));
            writer.append(',');
        }
        writer.append(strings.get(i));
        writer.newLine();
    }

    public void closeWriters() {
        try {
            transactionWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

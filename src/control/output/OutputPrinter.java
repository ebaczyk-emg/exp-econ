package control.output;

import agents.Agent;
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

    public OutputPrinter(String systemPath, Simulation simulation){
        this.path = systemPath;
        this.sim = simulation;
        System.out.println(path);
        try {
            transactionWriter = new BufferedWriter(
                    new FileWriter(
                            new File(path + "/transactions.csv")));
            endowmentWriter = new BufferedWriter(
                    new FileWriter(
                            new File(path + "/endowments.csv")));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(11);
        }
    }

    public void printOneStepOfOutput() {
        printTransactions();
        printEndowments();
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
            infoToAdd.add("" + a.getCashEndowment());
            infoToAdd.add("" + a.getAssetEndowment());
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

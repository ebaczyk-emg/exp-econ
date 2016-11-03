package control.config;

import control.SimulationCoordinator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Emily on 10/23/2016.
 */
public final class Config {

    private static final double INIT_CASH_ENDOWMENT = 1000;
    private static final int INIT_ASSET_ENDOWMENT = 2;
    private static final double MIN_ASSET_VALUE = 50;
    private static final double MAX_ASSET_VALUE = 100;
    private static final int N_AGENTS = 10;
    private static final int N_STEPS = 10;
    private static String systemPath = System.getProperty("user.dir");
    private static final boolean USE_MULTI_PERIOD_ASSET = true;
    private static final double decayFactor = 0.10d;

    public Config() {
        System.out.println("Generated Config file");
    }

    public static double getMaxAssetValue() {
        return MAX_ASSET_VALUE;
    }

    public static double getMinAssetValue() {
        return MIN_ASSET_VALUE;
    }

    public static int getInitAssetEndowment() {
        return INIT_ASSET_ENDOWMENT;
    }

    public static double getInitCashEndowment() {
        return INIT_CASH_ENDOWMENT;
    }

    public static int getnAgents() {
        return N_AGENTS;
    }

    public static int getnSteps() {
        return N_STEPS;
    }

    public static boolean isUseMultiPeriodAsset() {
        return USE_MULTI_PERIOD_ASSET;
    }

    public static double getDecayFactor() {
        return decayFactor;
    }

    public static String getSystemPath() {
        return systemPath;
    }

    public static String generateOutputPath() {
        File temp = new File(systemPath);
        String path = temp.getParentFile().getAbsolutePath();
        path += File.separator + "exp-econ-output" + File.separator + SimulationCoordinator.getMomentOfInvocationYearMonthDayHourMinuteSecond() ;
        try {
            new File(path).mkdirs();
        } catch (Exception ex ) {
            ex.printStackTrace();
            System.exit(11);
        }
        return path;
    }


}

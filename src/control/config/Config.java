package control.config;

import control.SimulationCoordinator;

import java.io.File;

/**
 * Created by Emily on 10/23/2016.
 */
public final class Config {

    private static final double INIT_CASH_ENDOWMENT = 1000;
    private static final int INIT_ASSET_ENDOWMENT = 3;
    private static final double MIN_ASSET_VALUE = 50;
    private static final double MAX_ASSET_VALUE = 300;
    private static final int N_AGENTS = 5;
    private static final int N_DIVIDEND_PERIODS = 1;
    private static final int N_STEPS_PER_DIVIDEND_PERIOD = 10;
    private static String systemPath = System.getProperty("user.dir");
    private static final boolean USE_MULTI_PERIOD_ASSET = true;
    private static final double decayFactor = 2d;

    /*
    For InformationAsset, the assets that provide some info to informed agents
    and no info to uninformed agents
     */

    private static final double INFO_INTRINSIC_VALUE = 100;
    private static final double INFO_DIVIDEND_MIN = 0;
    private static final double INFO_DIVIDEND_MAX = 30;
    private static final double INFO_P_STATE_A = 0.5d;
    private static final int BCK_LOOKBACK_PERIOD = 2;
    private static final int INF_INFO_THRESHOLD = 3;

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

    public static int getnStepsPerDividendPeriod() {
        return N_STEPS_PER_DIVIDEND_PERIOD;
    }

    public static int getnDividendPeriods() {
        return N_DIVIDEND_PERIODS;
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
        path += File.separator + "exp-econ-output" + File.separator + SimulationCoordinator.getInvocation() ;
        try {
            new File(path).mkdirs();
        } catch (Exception ex ) {
            ex.printStackTrace();
            System.exit(11);
        }
        return path;
    }

    public static double getInfoIntrinsicValue() {
        return INFO_INTRINSIC_VALUE;
    }

    public static double getInfoDividendMin() {
        return INFO_DIVIDEND_MIN;
    }

    public static double getInfoDividendMax() {
        return INFO_DIVIDEND_MAX;
    }

    public static double getInfoPStateA() {
        return INFO_P_STATE_A;
    }

    public static int getBckLookbackPeriod() {
        return BCK_LOOKBACK_PERIOD;
    }

    public static int getInfInfoThreshold() {
        return INF_INFO_THRESHOLD;
    }
}

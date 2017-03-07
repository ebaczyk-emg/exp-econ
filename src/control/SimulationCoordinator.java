package control;

import util.MersenneTwisterFast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Emily on 9/28/2016.
 */
public class SimulationCoordinator {
    static ArrayList<Simulation> sims;
    static String invocation;
    private static MersenneTwisterFast random = new MersenneTwisterFast(1);
    private static Random r = new Random(1);
    public static void main(String [] args) {
        invocation = getMomentOfInvocationYearMonthDayHourMinuteSecond();
        int numSims = 1;
        sims = new ArrayList<Simulation>();
        for(int i = 0; i < numSims; i++) {
            sims.add(new Simulation(r, i));
        }
    }

    public static String getMomentOfInvocationYearMonthDayHourMinuteSecond() {
        return (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format((new GregorianCalendar()).getTime());
    }

    public static String getInvocation() {
        return invocation;
    }
}

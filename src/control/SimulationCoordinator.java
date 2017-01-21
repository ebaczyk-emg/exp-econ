package control;

import util.MersenneTwisterFast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Emily on 9/28/2016.
 */
public class SimulationCoordinator {
    static ArrayList<Simulation> sims;
    private static MersenneTwisterFast random = new MersenneTwisterFast(1);
    public static void main(String [] args) {
        int numSims = 10;
        sims = new ArrayList<Simulation>();
        for(int i = 0; i < numSims; i++) {
            sims.add(new Simulation(random, i));
        }
    }

    public static String getMomentOfInvocationYearMonthDayHourMinuteSecond() {
        return (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format((new GregorianCalendar()).getTime());
    }
}

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
    private static MersenneTwisterFast random = new MersenneTwisterFast(1000);
    public static void main(String [] args) {
        sims = new ArrayList<Simulation>();
        sims.add(new Simulation(random));
    }

    public static String getMomentOfInvocationYearMonthDayHourMinuteSecond() {
        return (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format((new GregorianCalendar()).getTime());
    }
}

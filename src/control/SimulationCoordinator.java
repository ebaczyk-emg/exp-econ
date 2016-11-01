package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Emily on 9/28/2016.
 */
public class SimulationCoordinator {
    static ArrayList<Simulation> sims;
    public static void main(String [] args) {
        sims = new ArrayList<Simulation>();
        sims.add(new Simulation());
    }

    public static String getMomentOfInvocationYearMonthDayHourMinuteSecond() {
        return (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format((new GregorianCalendar()).getTime());
    }
}

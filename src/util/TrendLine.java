package util;

/**
 * http://stackoverflow.com/questions/17592139/trend-lines-regression-curve-fitting-java-library
 * Created by Emily on 12/4/2016.
 */
public interface TrendLine {
    public void setValues(double[] y, double[] x); // y ~ f(x)
    public double predict(double x); // get a predicted y for a given x
}

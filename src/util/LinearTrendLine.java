package util;

/**
 * http://stackoverflow.com/questions/17592139/trend-lines-regression-curve-fitting-java-library
 * Created by Emily on 12/4/2016.
 */
public class LinearTrendLine extends OLSTrendLine {
    final int degree = 1;
    public LinearTrendLine() {
    }
    protected double[] xVector(double x) { // {1, x, x*x, x*x*x, ...}
        double[] poly = new double[degree+1];
        double xi=1;
        for(int i=0; i<=degree; i++) {
            poly[i]=xi;
            xi*=x;
        }
        return poly;
    }
    @Override
    protected boolean logY() {
        return false;
    }
}

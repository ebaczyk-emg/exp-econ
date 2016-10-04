package assets;

/**
 * Created by Emily on 10/4/2016.
 */
public abstract class Asset {

    private double intrinsicValue;
    private String state;

    public double getIntrinsicValue(){
        return this.intrinsicValue;
    }

    public String getState(){
        return this.state;
    }
}

package assets;

/**
 * Created by Emily on 11/29/2016.
 */
public class InformationAsset extends MultiPeriodAsset {

    public InformationAsset (AssetRegistry registry,
                             double intrinsicValue,
                             double dividend) {
        super(registry, intrinsicValue);
        //dividend regime is specified, is redrawn each period.
        this.dividend = dividend;
    }

    public void setDividend(double newDividend) {
        this.dividend = dividend;
    }

}

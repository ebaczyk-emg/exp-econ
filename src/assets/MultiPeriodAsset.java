package assets;

/**
 * Created by Emily on 11/1/2016.
 */
public class MultiPeriodAsset extends Asset {

    public MultiPeriodAsset(AssetRegistry registry,
                            double intrinsicValue) {
        super(registry, intrinsicValue);
        this.owner = null;
        this.dividend = 0.05d * intrinsicValue; //this is arbitrary
        System.out.println(dividend);
    }
}

package assets;

/**
 * Created by Emily on 10/4/2016.
 */
public class HomogeneousAsset extends Asset {

    public HomogeneousAsset(AssetRegistry registry,
                            double intrinsicValue) {
        super(registry, intrinsicValue);
        this.owner = null;
        this.dividend = 0d;
    }

}

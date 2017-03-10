package shueching.mobileapp.dataobject;

/**
 * Created by shueching on 1/11/17.
 */
public class ProductLineItem {
    public Product product;
    public int qty;

    public ProductLineItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }
}

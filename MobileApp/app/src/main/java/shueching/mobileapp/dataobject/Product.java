package shueching.mobileapp.dataobject;

/**
 * Created by shueching on 1/11/17.
 */
public class Product {
    public String id;
    public String description;
    public double price;
    public int imageResourceId;

    public Product(String id, String description, double price, int imageResourceId) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }
}

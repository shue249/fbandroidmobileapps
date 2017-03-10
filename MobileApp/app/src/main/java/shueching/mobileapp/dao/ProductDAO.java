package shueching.mobileapp.dao;

import shueching.mobileapp.R;
import shueching.mobileapp.dataobject.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shueching on 1/11/17.
 */
public class ProductDAO {

    private static ProductDAO productDAO;
    private List<Product> products;

    public static ProductDAO getInstance() {
        if (productDAO == null) {
            productDAO = new ProductDAO();
        }
        return productDAO;
    }

    private ProductDAO() {
        products = new ArrayList<>();
        products.add(new Product("3DcAM01", "3D Camera", 1500, R.drawable.threedcam01));
        products.add(new Product("USB02", "External Hard Drive", 800, R.drawable.usb02));
        products.add(new Product("wristWear03", "Wrist Wear", 300, R.drawable.wristwear03));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(String id) {
        for (Product product : products) {
            if (product.id.equals(id)) {
                return product;
            }
        }
        return null;
    }
}

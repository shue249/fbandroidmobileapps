package shueching.mobileapp;

import android.content.Context;
import com.facebook.appevents.AppEventsLogger;
import shueching.mobileapp.dataobject.Product;
import shueching.mobileapp.dataobject.ProductLineItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shueching on 1/11/17.
 */
public class ShoppingCart {
    private static ShoppingCart shoppingCart = null;
    private HashMap<String, ProductLineItem> shoppingCartItems;
    private AppEventsLogger logger;

    public static ShoppingCart getInstance() {
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        return shoppingCart;
    }

    private ShoppingCart() {
        shoppingCartItems = new HashMap<>();
    }

    public void addProduct(Context context, Product product, int qty) {
        if (shoppingCartItems.containsKey(product.id)) {
            shoppingCartItems.get(product.id).qty += qty;
        } else {
            shoppingCartItems.put(product.id, new ProductLineItem(product, qty));
        }
        FacebookAppEventsController.getInstance(context).logAddToCart(new ProductLineItem(product, qty));
    }

    public List<ProductLineItem> getShoppingCartItems() {
        return new ArrayList<ProductLineItem>(shoppingCartItems.values());
    }

    public int getNumberOfShoppingCartItems() {
        return shoppingCartItems.size();
    }

    public double getTotalPrice() {
        double total = 0;
        for (ProductLineItem productLineItem : shoppingCartItems.values()) {
            total += productLineItem.qty * productLineItem.product.price;
        }
        return total;
    }

    public void removeAllItems() {
        shoppingCartItems.clear();
    }
}

package shueching.mobileapp;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by shueching on 1/15/17.
 */
public class MainController {
    private static MainController mainController = null;

    public static MainController getInstance() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }

    private MainController() {

    }

    public void updateShoppingCartMenuDisplay(Menu menu) {
        if (menu != null) {
            MenuItem menuItemCart = menu.findItem(R.id.menuCart);
            int numberOfShoppingCartItems = ShoppingCart.getInstance().getNumberOfShoppingCartItems();
            String cartTitle = "Cart - " + numberOfShoppingCartItems + " item" + ((numberOfShoppingCartItems > 1) ? "s" : "");
            menuItemCart.setTitle(cartTitle);
        }
    }

}

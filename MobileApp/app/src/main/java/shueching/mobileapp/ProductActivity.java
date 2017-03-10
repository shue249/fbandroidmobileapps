package shueching.mobileapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import shueching.mobileapp.dao.ProductDAO;
import shueching.mobileapp.dataobject.Product;
import shueching.mobileapp.dataobject.ProductLineItem;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = ProductActivity.class.getName();

    private FacebookAppEventsController facebookAppEventsController;

    private Product product;
    private ImageView imageView;
    private TextView textViewId;
    private TextView textViewDescription;
    private TextView textViewPrice;
    private EditText editTextQty;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        facebookAppEventsController = FacebookAppEventsController.getInstance(this);

        imageView = (ImageView) findViewById(R.id.imageView);
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        editTextQty = (EditText) findViewById(R.id.editTextQty);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data1 = intent.getData();
        if (action != null && data1 != null) {
            Log.i(TAG, "Deeplink: Action=" + action + " Data=" + data1.toString());
        } else {
            Log.i(TAG, "No deeplink");

        }
        String id = getIntent().getStringExtra("id");
        if (id == null || id.length() == 0) {
            Uri data = getIntent().getData();
            id = FacebookAppEventsController.getInstance(this).getIdFromURI(data);
        }
        product = ProductDAO.getInstance().getProduct(id);
        if (product != null) {
            showProduct(product);
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        MainController.getInstance().updateShoppingCartMenuDisplay(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showProduct(Product product) {
        imageView.setImageResource(product.imageResourceId);
        textViewId.setText(product.id);
        textViewDescription.setText(product.description);
        textViewPrice.setText("$" + product.price);
        editTextQty.setText("1");

        FacebookAppEventsController.getInstance(this).logViewContent(new ProductLineItem(product, 1));
    }

    public void onAddQty(View view) {
        int qty = getQty();
        qty++;
        setQty(qty);
    }

    public void onMinusQty(View view) {
        int qty = getQty();
        if (qty > 1) {
            qty--;
            setQty(qty);
        }
    }

    public void onAddToCart(View view) {
        int qty = getQty();
        if (qty > 0) {
            ShoppingCart.getInstance().addProduct(this, product, qty);
            MainController.getInstance().updateShoppingCartMenuDisplay(menu);
        }
    }

    private int getQty() {
        return Integer.parseInt(editTextQty.getText().toString());
    }

    private void setQty(int qty) {
        editTextQty.setText(String.valueOf(qty));
    }

    @Override
    public void onResume() {
        super.onResume();
        FacebookAppEventsController.getInstance(this).activateApp(this);
        MainController.getInstance().updateShoppingCartMenuDisplay(menu);
    }
}

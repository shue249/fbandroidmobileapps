package shueching.mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ProductListingActivity extends AppCompatActivity {

    private static final String TAG = ProductListingActivity.class.getName();

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);
    }

    public void onViewContent(View view) {
        String id = view.getTag().toString();
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        FacebookAppEventsController.getInstance(this).activateApp(this);
        MainController.getInstance().updateShoppingCartMenuDisplay(menu);
    }
}

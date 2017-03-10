package shueching.mobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import shueching.mobileapp.adapter.ShoppingCartAdapter;
import shueching.mobileapp.dataobject.ProductLineItem;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private static DecimalFormat df2 = new DecimalFormat(".##");
    private static final String TAG = CheckoutActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ListView listView = (ListView) findViewById(R.id.listProductLineItems);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.productlineitem_header_layout, listView, false);
        listView.addHeaderView(header, null, false);

        List<ProductLineItem> shoppingCartItems = ShoppingCart.getInstance().getShoppingCartItems();
        listView.setAdapter(new ShoppingCartAdapter(this, shoppingCartItems));

        TextView textView = (TextView) findViewById(R.id.textViewTotal);
        textView.setText("Total = "+df2.format(ShoppingCart.getInstance().getTotalPrice()));
    }

    public void onCheckout(View view) {
        FacebookAppEventsController.getInstance(this).logPurchase(ShoppingCart.getInstance().getShoppingCartItems());
//        ShoppingCart.getInstance().removeAllItems();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thank you for your purchase!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onSendViewCategory(View view) {
        FacebookAppEventsController.getInstance(this).logViewCategory(ShoppingCart.getInstance().getShoppingCartItems());
//        ShoppingCart.getInstance().removeAllItems();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thank you for your ViewCategory!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        FacebookAppEventsController.getInstance(this).activateApp(this);
    }
}

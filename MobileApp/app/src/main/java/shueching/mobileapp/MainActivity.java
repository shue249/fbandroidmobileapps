package shueching.mobileapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private AppEventsLogger logger;

    private EditText editTextAdvertId;
    private Spinner spinnerAppEvent;
    private Spinner spinnerCurrency;
    private EditText editTextContentId;
    private EditText editTextValue;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAdvertId = (EditText) findViewById(R.id.editTextAdvertId);
        spinnerAppEvent = (Spinner)findViewById(R.id.spinnerAppEvent);
        spinnerCurrency = (Spinner)findViewById(R.id.spinnerCurrency);
        editTextContentId = (EditText) findViewById(R.id.editTextContentId);
        editTextValue = (EditText) findViewById(R.id.editTextValue);
        textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                editTextAdvertId.setText(advertId);
                Log.d(TAG, "Advert ID = "+advertId);
            }

        };
        task.execute();

    }

    public void onFireAppEvents(View view) {
        String appEvent = String.valueOf(spinnerAppEvent.getSelectedItem());
        String currency = String.valueOf(spinnerCurrency.getSelectedItem());
        String contentId = editTextContentId.getText().toString();
        double value = Double.parseDouble(editTextValue.getText().toString());

        ArrayList<String> contents = new ArrayList<>();
        contents.add(contentId);

        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, TextUtils.join(", ", contents));
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);

        String fbAppEvent = "";
        if (appEvent.equals("ViewContent")) {
            fbAppEvent = AppEventsConstants.EVENT_NAME_VIEWED_CONTENT;
        }
        if (appEvent.equals("AddToCart")) {
            fbAppEvent = AppEventsConstants.EVENT_NAME_ADDED_TO_CART;
        }
        if (appEvent.equals("Purchase")) {
            fbAppEvent = AppEventsConstants.EVENT_NAME_PURCHASED;
        }

        logger.logEvent(fbAppEvent, value, parameters);

        String output = "Fired "+appEvent+" "+contentId+" "+currency+" "+value;
        Log.d(TAG, output);
        textViewOutput.setText(output);
    }

    public void onFireCustomAppEvents(View view) {
        logPurchaseVoucherEvent("Voucher", "2016 Christmas Voucher", "SGD", 120.99);
    }

    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logPurchaseVoucherEvent (String type, String name, String currency, double valToSum) {
        Bundle params = new Bundle();
        params.putString("Type", type);
        params.putString("Name", name);
        params.putString("Currency", currency);
        logger.logEvent("PurchaseVoucher", valToSum, params);
    }
}

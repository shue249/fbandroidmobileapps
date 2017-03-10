package shueching.mobileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getName();
    private AppEventsLogger logger;
    private EditText editTextAdvertId;
    private TextView textViewDeeplink;
    private TextView textViewDeferredDeepLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextAdvertId = (EditText) findViewById(R.id.editTextAdvertId);
        textViewDeeplink = (TextView) findViewById(R.id.textViewDeeplink);
        textViewDeferredDeepLink = (TextView) findViewById(R.id.textViewDeferredDeeplink);

        // Initialize the SDK before executing any other operations,
        final FacebookAppEventsController facebookAppEventsController = FacebookAppEventsController.getInstance(this);
        facebookAppEventsController.activateApp(this);

        facebookAppEventsController.getDeferredDeeplink(this, new AppLinkData.CompletionHandler() {
            @Override
            public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
            if (appLinkData != null) {
                final Uri data = appLinkData.getTargetUri();
                updateTextView("Deferred deep link = "+data.toString(), textViewDeferredDeepLink);
            } else {
                updateTextView("No deferred deep link found", textViewDeferredDeepLink);
            }
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (action != null && data != null) {
            updateTextView("Deep link = "+data.toString(), textViewDeeplink);
        } else {
            updateTextView("No deep link found", textViewDeeplink);
        }

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
//                Toast.makeText(getApplicationContext(), advertId, Toast.LENGTH_SHORT).show();
                editTextAdvertId.setText(advertId);
                Log.d(TAG, "Advert ID = "+advertId);
            }

        };
        task.execute();
    }

    public void onLogin(View view) {
        Intent intent = new Intent(this, ProductListingActivity.class);
        startActivity(intent);
    }

    private void updateTextView(final String text, final TextView textView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}

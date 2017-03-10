package shueching.mobileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class OfferActivity extends AppCompatActivity {

    private String TAG = OfferActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        StringBuilder sb = new StringBuilder();
        sb.append(data.toString()+"\n");
        for (String parameter : data.getQueryParameterNames()) {
            sb.append(parameter+"\n");
        }
        sb.append(action);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(sb.toString());
    }

}

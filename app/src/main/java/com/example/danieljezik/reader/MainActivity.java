package com.example.danieljezik.reader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnNovinky;
    private Button btnUlozene;

    public Button getBtnNovinky() {
        return btnNovinky;
    }

    public Button getBtnUlozene() {
        return btnUlozene;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNovinky = findViewById(R.id.btn_novinky);
        btnUlozene = findViewById(R.id.btn_ulozene);
        if (isNetworkAvailable())
        {
            btnNovinky.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent novinky = new Intent(MainActivity.this, NewsActivity.class);
                    MainActivity.this.startActivity(novinky);
                }
            });

            btnUlozene.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent ulozene = new Intent(MainActivity.this, SavedArticleActivity.class);
                    MainActivity.this.startActivity(ulozene);
                }
            });
        } else {
            btnNovinky.setEnabled(false);
            btnUlozene.setEnabled(false);

            Toast.makeText(getBaseContext(),getString(R.string.noInternetConnection),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnNovinky = findViewById(R.id.btn_novinky);
        btnUlozene = findViewById(R.id.btn_ulozene);
        if (isNetworkAvailable())
        {
            btnNovinky.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent novinky = new Intent(MainActivity.this, NewsActivity.class);
                    MainActivity.this.startActivity(novinky);
                }
            });

            btnUlozene.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent ulozene = new Intent(MainActivity.this, SavedArticleActivity.class);
                    MainActivity.this.startActivity(ulozene);
                }
            });
        } else {
            btnNovinky.setEnabled(false);
            btnUlozene.setEnabled(false);

            Toast.makeText(getBaseContext(),getString(R.string.noInternetConnection),
                    Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



}

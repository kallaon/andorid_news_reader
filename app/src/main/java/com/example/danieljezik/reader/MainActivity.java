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

    /**
     * Metoda onCreate volana pri vytvorení aplikacie, nastavuje potrebne parametre aplikacie
     * Podla dostupnosti internetu nastavuje buttony a logiku
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSettings(isNetworkAvailable());
    }

    /**
     * Metoda volana pri obnoveni, nastavuje potrebne parametre aplikacie
     * Podla dostupnosti internetu nastavuje buttony a logiku
     */
    @Override
    protected void onResume() {
        super.onResume();
        initSettings(isNetworkAvailable());
    }

    /**
     * Metoda nastavuje dostupnost buttonov, zobrazuje toasty, je vyuzivana onCreate a onResume
     * Po kliknuti na button - oblubene, presmeruje na offline clanky
     * Po kliknuti na button - novinky, presmeruje na zoznam noviniek
     *
     * @param isNetworkAvailable vysledok z metody isNetworkAvailable
     */
    private void initSettings(boolean isNetworkAvailable){
        btnNovinky = findViewById(R.id.btn_novinky);
        btnUlozene = findViewById(R.id.btn_ulozene);

        btnUlozene.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ulozene = new Intent(MainActivity.this, SavedArticleActivity.class);
                MainActivity.this.startActivity(ulozene);
            }
        });

        if (isNetworkAvailable)
        {
            btnNovinky.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent novinky = new Intent(MainActivity.this, NewsActivity.class);
                    MainActivity.this.startActivity(novinky);
                }
            });
        } else {
            btnNovinky.setEnabled(false);
            Toast.makeText(getBaseContext(),getString(R.string.noInternetConnection),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Metoda zistuje dostupnost internetu cez data a WiFi
     *
     * @return vracia boolean hodnotu dostupnosti internetu
     */
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

package com.example.danieljezik.reader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
        initSettings();
    }

    /**
     * Metoda volana pri obnoveni, nastavuje potrebne parametre aplikacie
     */
    @Override
    protected void onResume() {
        super.onResume();
        initSettings();
    }

    /**
     * Po kliknuti na button - oblubene, presmeruje na offline clanky
     * Po kliknuti na button - novinky, presmeruje na zoznam noviniek
     *
     */
    private void initSettings(){
        btnNovinky = findViewById(R.id.btn_novinky);
        btnUlozene = findViewById(R.id.btn_ulozene);

        btnUlozene.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ulozene = new Intent(MainActivity.this, SavedArticleActivity.class);
                MainActivity.this.startActivity(ulozene);
            }
        });

        btnNovinky.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent novinky = new Intent(MainActivity.this, NewsActivity.class);
                MainActivity.this.startActivity(novinky);
            }
        });

    }

}

package com.example.danieljezik.reader;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.danieljezik.reader.Database.DataBaseHelper;

public class ArticleActivity extends AppCompatActivity {

    private DataBaseHelper database;
    private Button sourceButton;
    private Button favoriteButton;
    private String imageUrl;
    private String publishedAt;
    private String title;
    private String description;
    private String source;
    private String url;
    private String author;
    private String status;

    /**
     * Metoda nastavuje texty buttonov na základe toho, ci je clanok ulozeny alebo nie
     */
    private void setButtonValues()
    {
        database = new DataBaseHelper(getBaseContext());
        if (database.ifArticleIsSaved(url))
        {
            favoriteButton.setText(getString(R.string.res_delete));
        } else {
            favoriteButton.setText(getString(R.string.res_favorite));
        }
    }

    /**
     * Hlavna metoda aktivity Clanku, po kliknuti zobrazuje dane texty,popisy, obrazky pre članok
     * Nastavuje buttony pre ulozenie clanku a zobrazenie originalneho clanku
     * Nastavuje toasty, ktore informuju o vymazani a pridani clanku
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getIntentContent();

        sourceButton = (Button) findViewById(R.id.btn_original_source);
        sourceButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        favoriteButton = (Button) findViewById(R.id.btn_favorite);
        setButtonValues();
        favoriteButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // zobrazuje toasty a maze alebo pridava do databazy
                if (database.ifArticleIsSaved(url))
                {
                    database.removeArticle(url);
                    Toast.makeText(getBaseContext(), getString(R.string.toast_deleted),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    database.insertArticle(source,author, title, description, url, imageUrl, publishedAt);
                    Toast.makeText(getBaseContext(), getString(R.string.toast_saved),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    database.close();
                }
            }
        });
    }

    /**
     * V metode onResume je volana metoda setButtonValues, pomocou ktorej sa pripadne upravia texty buttonov
     */
    @Override
    protected void onResume() {
        super.onResume();
        setButtonValues();
    }

    /**
     * Metoda nastavuje premenne hodnotami poslanymi v Intente
     */
    private void getIntentContent()
    {
        imageUrl = getIntent().getStringExtra("header_image");
        publishedAt = getIntent().getStringExtra("date");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        source = getIntent().getStringExtra("source");
        url = getIntent().getStringExtra("url");
        author = getIntent().getStringExtra("author");
        status = getIntent().getStringExtra("status");

        setTexts(title,description,source,publishedAt,imageUrl);
    }

    /**
     * Metoda naplna elementy textami a obrazkami
     *
     * @param title nadpis clánku
     * @param description popis clanku
     * @param source zdroj clanku
     * @param publishedAt datum publikovania clanku
     * @param imageUrl URL obrazku
     */
    private void setTexts(String title, String description, String source, String publishedAt, String imageUrl)
    {
        TextView source_name = findViewById(R.id.source_name);
        source_name.setText(source);

        TextView article_title = findViewById(R.id.article_title);
        article_title.setText(title);

        TextView article_description = findViewById(R.id.description);
        article_description.setText(description);

        TextView article_publishedAt = findViewById(R.id.date);
        article_publishedAt.setText(publishedAt);

        ImageView header_image = findViewById(R.id.header_image);
        if (imageUrl != null){
            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(header_image);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_launcher_background)
                    .into(header_image);
        }

    }
}

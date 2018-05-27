package com.example.danieljezik.reader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

    @Override
    protected void onResume() {
        super.onResume();
        setButtonValues();
    }

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

        //Log.d("vypis",publishedAt);

        setTexts(title,description,source,publishedAt,imageUrl);
    }

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
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(header_image);

    }
}

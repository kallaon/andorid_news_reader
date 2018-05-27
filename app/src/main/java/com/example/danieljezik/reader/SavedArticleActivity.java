package com.example.danieljezik.reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.danieljezik.reader.Database.DataBaseHelper;
import com.example.danieljezik.reader.Model.Article;

import java.util.List;

public class SavedArticleActivity extends AppCompatActivity implements RecyclerViewClickListener{

    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private List<Article> articleList;
    private RecyclerViewClickListener recyclerViewClickListener;
    private DataBaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        database = new DataBaseHelper(this);
        recyclerViewClickListener = this;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        articleList = database.getAllArticles();
        articlesAdapter = new ArticlesAdapter(articleList, recyclerViewClickListener);
        recyclerView.setAdapter(articlesAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("header_image",articleList.get(position).getUrlToImage());
        intent.putExtra("title",articleList.get(position).getTitle());
        intent.putExtra("description",articleList.get(position).getDescription());
        intent.putExtra("source",articleList.get(position).getSource().getName());
        intent.putExtra("date",articleList.get(position).getPublishedAt());
        intent.putExtra("url",articleList.get(position).getUrl());
        intent.putExtra("author",articleList.get(position).getAuthor());
        intent.putExtra("status","saved");
        startActivity(intent);
    }
}

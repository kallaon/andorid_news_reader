package com.example.danieljezik.reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.danieljezik.reader.Database.DataBaseHelper;
import com.example.danieljezik.reader.Model.Article;
import com.example.danieljezik.reader.Model.NewsResponse;
import com.example.danieljezik.reader.Retrofit.ApiClient;
import com.example.danieljezik.reader.Retrofit.EndpointInterface;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity  implements RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private List<Article> articleList;
    private RecyclerViewClickListener recyclerViewClickListener;
    private DataBaseHelper database;

    /**
     * Metóda onCreate, inicializuje prvky pri vytvorení a všetky potrebné veci
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        database = new DataBaseHelper(this);
        recyclerViewClickListener = this;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        EndpointInterface apiInterface = ApiClient.getClient().create(EndpointInterface.class);

        /**
         * Metóda vykoná asynchrónny call, z tela responsu získa dáta, ktoré spracuje na články a napĺňa nimi recyclerView
         *
         * @param call objekt volania call
         * @param response odpoveď (response)
         */
        Call<NewsResponse> newsResponse = apiInterface.getNewsData();
        newsResponse.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse nr = response.body();
                articleList = nr.getArticles();
                //articlesAdapter = new ArticlesAdapter(nr.getArticles(),);
                articlesAdapter = new ArticlesAdapter(articleList, recyclerViewClickListener);
                recyclerView.setAdapter(articlesAdapter);
            }

            /**
             * Metóda pre chybu
             *
             * @param call volanie
             * @param t výnimka
             */
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * Metóda na zíklade kliknutia zistí pozíciu prvku, vytvorí intent, do ktorého vloží informácie o danom článku a vyvolá novú aktivitu
     *
     *  @param view view
     * @param position pozícia prvku
     */
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
        intent.putExtra("status","news");
        startActivity(intent);
    }


}

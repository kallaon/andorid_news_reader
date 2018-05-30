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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * Metoda onCreate, inicializuje prvky pri vytvoreni a vsetky potrebne veci
     *
     * @param savedInstanceState savedInstanceState
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
         * Metoda vykona asynchronny call, z tela responsu ziska data, ktore spracuje na clanky a naplna nimi recyclerView
         *
         * @param call objekt volania call
         * @param response odpoved (response)
         */
        Call<NewsResponse> newsResponse = apiInterface.getNewsData();
        newsResponse.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse nr = response.body();
                //response.headers().get("x-cached-result").equals("false")
                articleList = nr.getArticles();
                for (Article article: articleList)
                {
                    article.setPublishedAt(toDate(article.getPublishedAt()));
                }

                //articlesAdapter = new ArticlesAdapter(nr.getArticles(),);
                articlesAdapter = new ArticlesAdapter(articleList, recyclerViewClickListener);
                recyclerView.setAdapter(articlesAdapter);
            }

            /**
             * Metoda pre chybu
             *
             * @param call volanie
             * @param t vynimka
             */
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * Metoda na zaklade kliknutia zistí poziciu prvku, vytvori intent, do ktoreho vlozi informacie o danom clanku a vyvola novu aktivitu
     *
     * @param view view
     * @param position pozicia prvku
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

    /**
     * Metoda formatujem datum clankov na user friendly format
     *
     * @param publishedAt datum publikovania clanku
     *
     * @return naformatovaný datum
     */
    private String toDate(String publishedAt)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy 'o' HH:mm");
            Date d = dateFormat.parse(publishedAt);
            String output = df.format(d);
            return output;

        } catch (ParseException e) {
            e.printStackTrace();
            return e.toString();
        }

    }

}

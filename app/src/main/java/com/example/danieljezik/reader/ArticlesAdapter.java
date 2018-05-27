package com.example.danieljezik.reader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.danieljezik.reader.Model.Article;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<Article> articlesList;
    private RecyclerViewClickListener recyclerViewClickListener;

    public ArticlesAdapter(List<Article> articlesList, RecyclerViewClickListener recyclerViewClickListener) {
        this.articlesList = articlesList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article = articlesList.get(position);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource().getName());
        holder.publishedAt.setText(article.getPublishedAt());

        if (article.getUrlToImage() != null){
            Glide.with(holder.context).load(article.getUrlToImage()).apply(RequestOptions.circleCropTransform()).into(holder.imageView_article);
        } else {
            Glide.with(holder.context).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(holder.imageView_article);
        }

    }


    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, source, publishedAt;
        private ImageView imageView_article;
        private Context context;
        public RecyclerViewClickListener mListener;


        public MyViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            context = view.getContext();
            mListener = listener;
            title = (TextView) view.findViewById(R.id.title);
            source = (TextView) view.findViewById(R.id.source_name);
            publishedAt = (TextView) view.findViewById(R.id.publishedAt);
            imageView_article = (ImageView) view.findViewById(R.id.imageView_article_prev);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}

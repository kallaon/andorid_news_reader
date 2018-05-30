package com.example.danieljezik.reader.Model;

import android.database.Cursor;

import com.example.danieljezik.reader.Database.DataBaseHelper;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("source")
    private Source source;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String publishedAt;

    /**
     * Konstruktor pre vytvorenie clanku, naplnany pomocou "@SerializedName"
     *
     * @param source zdroj clanku
     * @param author autor clanku
     * @param title nadpis clanku
     * @param description popis clanku
     * @param url url clanku
     * @param urlToImage URL obrazku clanku
     * @param publishedAt datum vytvorenia clanku
     */
    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    /**
     * Vytvaranie clanku z databázy
     *
     * @param cursor cursor
     */
    public Article(Cursor cursor)
    {
        String sourceName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_SOURCE_NAME));
        String tmp;

        this.source = new Source(null, sourceName);
        this.author = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_AUTHOR));
        this.title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TITLE));
        this.description = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_DESCRIPTION));
        this.url = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_URL));
        this.urlToImage = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_URL_TO_IMAGE));
        this.publishedAt = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PUBLISHEDAT));
    }



    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}

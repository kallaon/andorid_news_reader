package com.example.danieljezik.reader.Model;

import android.database.Cursor;
import android.util.Log;

import com.example.danieljezik.reader.Database.DataBaseHelper;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     * Konštruktor pre vytvorenie článku, napĺňaný pomocou "@SerializedName"
     *
     * @param source zdroj článku
     * @param author autor článku
     * @param title nadpis článku
     * @param description popis článku
     * @param url url článku
     * @param urlToImage URL obrázku článku
     * @param publishedAt dátum vytvorenia článku
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
     * Vytváranie článku z databázy
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

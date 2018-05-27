package com.example.danieljezik.reader.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.danieljezik.reader.Model.Article;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "article.db";
    public static final String TABLE_NAME = "article_table";

    public static final String COL_ARTICLE_ID = "ARTICLE_ID";
    public static final String COL_SOURCE_NAME = "SOURCE_NAME";
    public static final String COL_AUTHOR = "AUTHOR";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_URL = "URL";
    public static final String COL_URL_TO_IMAGE = "URL_TO_IMAGE";
    public static final String COL_PUBLISHEDAT = "PUBLISHEDAT";


    public static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            "ARTICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "SOURCE_NAME TEXT," +
            "AUTHOR TEXT," +
            "TITLE TEXT," +
            "DESCRIPTION TEXT," +
            "URL TEXT," +
            "URL_TO_IMAGE TEXT," +
            "PUBLISHEDAT TEXT" +
            ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertArticle(String sourceName, String author, String title,
                                  String description, String url, String urlToImage, String publishedAt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SOURCE_NAME,sourceName);
        contentValues.put(COL_AUTHOR,author);
        contentValues.put(COL_TITLE,title);
        contentValues.put(COL_DESCRIPTION,description);
        contentValues.put(COL_URL,url);
        contentValues.put(COL_URL_TO_IMAGE,urlToImage);
        contentValues.put(COL_PUBLISHEDAT,publishedAt);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
        {
            return false;
        } else {
            return true;
        }

    }

    public boolean removeArticle(String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME,COL_URL + "=" + "'" + url + "'",null);
        if (result == -1)
        {
            return false;
        } else {
            return true;
        }

    }

    public boolean ifArticleIsSaved(String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_URL + " LIKE " + "'" + url + "'",null);

        if ((mcursor != null) && (mcursor.getCount() > 0))
        {
            mcursor.close();
            return true;
        } else {
            mcursor.close();
            return false;
        }

    }

    public List<Article> getAllArticles()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        List<Article> allArticles = new ArrayList<Article>();

        try {
            while (cursor.moveToNext()) {
                allArticles.add(new Article(cursor));
            }
        }finally {
            cursor.close();
        }

        return allArticles;
    }
}
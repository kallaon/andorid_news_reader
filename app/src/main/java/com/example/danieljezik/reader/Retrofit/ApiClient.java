package com.example.danieljezik.reader.Retrofit;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient {
    private static final String BASE_URL = "http://newsapi.org";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}
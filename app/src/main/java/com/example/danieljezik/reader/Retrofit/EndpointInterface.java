package com.example.danieljezik.reader.Retrofit;

import com.example.danieljezik.reader.Model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndpointInterface {

    @GET("/v2/top-headlines?country=sk&apiKey=e7059dbbd36e40679cea1fa606b58c5b")
    Call<NewsResponse> getNewsData();
}

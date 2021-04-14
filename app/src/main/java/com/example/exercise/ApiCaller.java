package com.example.exercise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCaller {

    @GET("/3/movie/popular")
    Call<MovieResults> getMovies(
            //@Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page


    );

}

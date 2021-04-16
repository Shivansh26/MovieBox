package com.example.exercise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE_FIRST = 1;
    public int TOTAL_PAGES = 5;
    public int currentPage = PAGE_FIRST;
    String category1="now_playing";
    String category2="popular";

    public static String API_KEY = "3bfdb20171c7e8d0f9e7bc29c0c44078";
    public static String LANGUAGE = "en-US";

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    List<MovieResults> moviesList;

    Boolean isLoading = false;
    Boolean isLastPage=false;
    ProgressBar progressBar;
    AdapterVertical adapterVertical;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_layout);
        //mTextView=findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView1 = findViewById(R.id.recyclerView1);
        progressBar = findViewById(R.id.progressBar);
        //PutDataToVerticalRecyclerView(moviesList);
        adapterVertical=new AdapterVertical(this);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapterVertical);



        moviesList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCaller apiCaller = retrofit.create(ApiCaller.class);

        Call<MovieResults> call = apiCaller.getMovies(category1,API_KEY, LANGUAGE, currentPage);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                List<MovieResults.Result> listOfMovies = response.body().getResult();
                PutDataToRecyclerView(listOfMovies);
                //PutDataToVerticalRecyclerView(listOfMovies);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
        recyclerView1.addOnScrollListener(new PaginationScroll(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                        //adapterVertical.notifyDataSetChanged();
                        //progressBar.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {

                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();
       // progressBar.setVisibility(View.VISIBLE);



    }


    private void PutDataToRecyclerView(List<MovieResults.Result> moviesList) {
        Adaptery adaptery = new Adaptery(this, moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adaptery);
    }


    private void loadFirstPage() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCaller apiCaller = retrofit.create(ApiCaller.class);

        Call<MovieResults> call = apiCaller.getMovies(category2,API_KEY, LANGUAGE, currentPage);


        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                List<MovieResults.Result> listOfMovies = response.body().getResult();
               // PutDataToVerticalRecyclerView(listOfMovies);
                progressBar.setVisibility(View.GONE);

                adapterVertical.addAll(listOfMovies);

                if (currentPage <= TOTAL_PAGES) adapterVertical.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }
    private void loadNextPage() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCaller apiCaller = retrofit.create(ApiCaller.class);

        Call<MovieResults> call = apiCaller.getMovies(category2,API_KEY, LANGUAGE, currentPage);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                adapterVertical.removeLoadingFooter();
                isLoading = false;
                List<MovieResults.Result> listOfMovies = response.body().getResult();
                adapterVertical.addAll(listOfMovies);

                if (currentPage != TOTAL_PAGES){
                    adapterVertical.addLoadingFooter();

                }

                else isLastPage = true;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}





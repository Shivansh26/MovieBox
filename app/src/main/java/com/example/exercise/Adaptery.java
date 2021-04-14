package com.example.exercise;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context context;
    private List<MovieResults.Result> moviesList;

    public Adaptery(Context context, List<MovieResults.Result> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public Adaptery.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.movie_poster, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptery.MyViewHolder holder, int position) {

        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+
                moviesList.get(position).getPosterPath()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}

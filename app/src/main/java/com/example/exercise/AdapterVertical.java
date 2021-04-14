package com.example.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class AdapterVertical extends RecyclerView.Adapter<AdapterVertical.MyViewHolder1> {

    private Context context;
    private List<MovieResults.Result> moviesList;
    private Boolean isLoadingAdded = false;

    public AdapterVertical(Context context) {
        this.context = context;
        moviesList = new ArrayList<>();
    }
    public List<MovieResults.Result> getMoviesList(){
        return moviesList;
    }
    public void setMovies(List<MovieResults.Result> moviesList){
        this.moviesList=moviesList;
    }

    @NonNull
    @Override
    public AdapterVertical.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.vertical, parent, false);


        return new MyViewHolder1(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVertical.MyViewHolder1 holder, int position) {
        holder.txtView1.setText(moviesList.get(position).getOriginalTitle());
        holder.txtView2.setText(moviesList.get(position).getReleaseDate());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+
                moviesList.get(position).getPosterPath()).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.imgV);


    }

    @Override
    public int getItemCount() {
        return moviesList==null?0:moviesList.size();
        //return moviesList.size();
    }

    public void add(MovieResults.Result r) {
        moviesList.add(r);
        notifyItemInserted(moviesList.size() - 1);
    }

    public void addAll(List<MovieResults.Result> movieResults) {
        for (MovieResults.Result result : movieResults) {
            add(result);
        }
    }
    public void remove(MovieResults.Result r) {
        int position = moviesList.indexOf(r);
        if (position > -1) {
            moviesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MovieResults.Result());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = moviesList.size() - 1;
        MovieResults.Result res = getItem(position);

        if (res != null) {
            moviesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MovieResults.Result getItem(int position) {
        return moviesList.get(position);
    }


    public static class MyViewHolder1 extends RecyclerView.ViewHolder {

        ImageView imgV;
        TextView txtView1;
        TextView txtView2;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            imgV = itemView.findViewById(R.id.imageView3);
            txtView1 = itemView.findViewById(R.id.textView6);
            txtView2 = itemView.findViewById(R.id.textView7);

        }
    }

}

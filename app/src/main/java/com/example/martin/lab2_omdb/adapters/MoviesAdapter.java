package com.example.martin.lab2_omdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.annotation.GlideModule;
import com.example.martin.lab2_omdb.R;
import com.example.martin.lab2_omdb.data.MoviesData;
import com.example.martin.lab2_omdb.utils.GlideApp;
import com.example.martin.lab2_omdb.utils.Utils;

import java.util.ArrayList;

/**
 * Created by martin on 11/11/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private ArrayList<MoviesData> moviesArrayList = null;
    private Context context;
    private ArrayList<MoviesData> filterMoviesArray;


    public MoviesAdapter(Context context, ArrayList<MoviesData> moviesArrayList) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
        this.filterMoviesArray = new ArrayList<>();
        this.filterMoviesArray.addAll(this.moviesArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_moviesactivity, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieTitleTextView.setText(moviesArrayList.get(position).getTitle());
        holder.releaseYearTextView.setText(moviesArrayList.get(position).getMovieReleaseDate());
        GlideApp.with(context)
                .load(moviesArrayList.get(position).getMoviePoster())
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitleTextView;
        TextView releaseYearTextView;

        ViewHolder(View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.item_movie_image);
            movieTitleTextView = itemView.findViewById(R.id.item_movie_title);
            releaseYearTextView = itemView.findViewById(R.id.item_movie_release_year);
        }
    }

    public void filter(String filterString) {
        filterString = filterString.toLowerCase();
        moviesArrayList.clear();
        if (filterString.length() == 0) {
            moviesArrayList.addAll(filterMoviesArray);
        } else {
            for (MoviesData moviesData : filterMoviesArray) {
                if (moviesData.getTitle().toLowerCase().compareToIgnoreCase(filterString) == 0) {
                    moviesArrayList.add(moviesData);
                }
            }
        }
        notifyDataSetChanged();
    }
}

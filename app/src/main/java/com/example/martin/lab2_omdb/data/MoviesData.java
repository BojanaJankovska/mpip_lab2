package com.example.martin.lab2_omdb.data;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.martin.lab2_omdb.volley.APIResponse;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by martin on 11/11/17.
 */

public class MoviesData   {


//    @SerializedName("Title")
    private String title;

    //@SerializedName("Poster")
    private String moviePoster;

    //@SerializedName("Year")
    private String movieReleaseDate;

    //@SerializedName("imdbID")
    private String imdbID;

    //@SerializedName("Response")
    private String getResponseForMovie;

    public MoviesData(String title, String moviePoster, String movieReleaseDate, String imdbID) {
        this.title = title;
        this.moviePoster = moviePoster;
        this.movieReleaseDate = movieReleaseDate;
        this.imdbID = imdbID;
       // this.getResponseForMovie = getResponseForMovie;
    }


    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }


    public String getTitle() {
        return title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getResponseForMovie() {
        return getResponseForMovie;
    }


}

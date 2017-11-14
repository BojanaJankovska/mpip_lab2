package com.example.martin.lab2_omdb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.martin.lab2_omdb.R;
import com.example.martin.lab2_omdb.adapters.MoviesAdapter;
import com.example.martin.lab2_omdb.data.MoviesData;
import com.example.martin.lab2_omdb.utils.C;
import com.example.martin.lab2_omdb.utils.ItemClickSupport;
import com.example.martin.lab2_omdb.utils.Utils;
import com.example.martin.lab2_omdb.volley.APIJSONRequest;
import com.example.martin.lab2_omdb.volley.APIResponse;
import com.example.martin.lab2_omdb.volley.VolleySession;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, TextWatcher {

    private RecyclerView moviesRecyclerView;
    private SearchView moviesSearchView;
    private ArrayList<MoviesData> moviesArrayList = new ArrayList<>();
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private EditText searchMoviesEditText;
    RequestQueue requestQueue;
    ArrayList<MoviesData> moviesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //VolleySession.instance().initialize(this);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_movies);

        initUI();
    }


    private void initUI() {

        moviesRecyclerView = findViewById(R.id.recycler_moviesactivity);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesSearchView = findViewById(R.id.search_moviesactivity);
        searchMoviesEditText = findViewById(R.id.search_movies_moviesactivity);
        progressBar = findViewById(R.id.progress_bar_moviesactivity);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_movie_moviesactivity, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_moviesactivity).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    private void initListeners() {
        moviesRecyclerView.setOnClickListener(this);
        ItemClickSupport.addTo(moviesRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra(C.IMDB_ID, moviesData.get(position).getImdbID());
                startActivity(intent);
            }
        });
        searchMoviesEditText.addTextChangedListener(this);
    }

    private class MoviesSearch extends APIResponse {
        @SerializedName("Search")
        ArrayList<MoviesData> moviesDataArrayList = new ArrayList<>();
        @SerializedName("Response")
        String response;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        progressBar.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject();


/*
        APIJSONRequest<MoviesSearch> apijsonRequest = new APIJSONRequest<>(this, jsonObject.toString(), Request.Method.POST, "http://www.omdbapi.com/?s=" + query + "&plot=short&apikey=a5722302", MoviesSearch.class,
                new Response.Listener<MoviesSearch>() {

                    @Override
                    public void onResponse(MoviesSearch response) {
                        if (response.response.equals("False")) {
                            Utils.doToast(MoviesActivity.this, R.string.movie_not_found);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            moviesArrayList.addAll(response.moviesDataArrayList);
                            moviesAdapter = new MoviesAdapter(MoviesActivity.this, moviesArrayList);
                            moviesRecyclerView.setAdapter(moviesAdapter);
                            initListeners();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.doLog("errorVolley");
            }
        });
        VolleySession.instance().addRequest(apijsonRequest);
        return false;*/

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, "http://www.omdbapi.com/?s=" + query + "&plot=short&apikey=a5722302", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utils.doLog("response");
                        try {
                            JSONArray jsonArray = response.getJSONArray("Search");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject movie = jsonArray.getJSONObject(i);
                                String name = movie.getString("Title");
                                String year = movie.getString("Year");
                                String imdbID = movie.getString("imdbID");
                                String type = movie.getString("Type");
                                String poster = movie.getString("Poster");

                                moviesData.add(new MoviesData(name, poster, year, imdbID));
                                moviesAdapter = new MoviesAdapter(MoviesActivity.this, moviesData);
                                moviesRecyclerView.setAdapter(moviesAdapter);
                                initListeners();
                                progressBar.setVisibility(View.GONE);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.doLog(error.getMessage());
                Utils.doLog(error.getCause());
            }
        });

        requestQueue.add(jsonArrayRequest);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (moviesAdapter != null) {
            moviesAdapter.filter(newText);
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (moviesAdapter != null) {
            moviesAdapter.filter(s.toString());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

package com.example.martin.lab2_omdb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.martin.lab2_omdb.R;
import com.example.martin.lab2_omdb.utils.C;
import com.example.martin.lab2_omdb.utils.GlideApp;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView yearTextView;
    private TextView ratedTextView;
    private TextView runtimeTextView;
    private TextView actorsTextView;

    private ImageView moviePosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initUI();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getDetailsForMovie(bundle.getString(C.IMDB_ID));
        }
    }


    private void initUI() {

        titleTextView = findViewById(R.id.movie_name_text_view_moviedetailsactivity);
        yearTextView = findViewById(R.id.movie_year_text_view_moviedetailsactivity);
        ratedTextView = findViewById(R.id.movie_rating_text_view_moviedetailsactivity);
        runtimeTextView = findViewById(R.id.movie_runtime_moviedetailsactivity);
        moviePosterImageView = findViewById(R.id.movie_image_moviedetailsactivity);
        actorsTextView = findViewById(R.id.movie_actors);

    }

    private void getDetailsForMovie(String imdbID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://www.omdbapi.com/?i=" + imdbID + "&plot=full&apikey=a5722302", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            titleTextView.setText(response.get("Title").toString());
                            yearTextView.setText(response.get("Year").toString());
                            ratedTextView.setText(response.get("Rated").toString());
                            runtimeTextView.setText(response.get("Runtime").toString());
                            actorsTextView.setText(response.get("Actors").toString() + "\n");
                            GlideApp.with(MovieDetailsActivity.this)
                                    .load(response.get("Poster").toString())
                                    .into(moviePosterImageView);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

    }


}

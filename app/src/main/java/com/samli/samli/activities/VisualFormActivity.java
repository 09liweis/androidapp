package com.samli.samli.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.models.Visual;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class VisualFormActivity extends AppCompatActivity {
    EditText titleET;
    EditText originTitleET;
    EditText doubanIdET;
    EditText doubanRatingET;
    EditText summaryET;
    EditText episodesET;
    EditText imdbIDET;
    EditText imdbRatingET;
    ImageView doubanPosterIV;
    ImageView imdbPosterIV;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_form);

        FloatingActionButton fab = findViewById(R.id.post_visual);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        titleET = findViewById(R.id.visual_form_title);
        originTitleET = findViewById(R.id.visual_form_original_title);
        doubanIdET = findViewById(R.id.visual_form_douban_id);
        summaryET = findViewById(R.id.visual_form_summary);
        doubanPosterIV = findViewById(R.id.visual_form_douban_poster);
        doubanRatingET = findViewById(R.id.visual_form_douban_rating);
        episodesET = findViewById(R.id.visual_form_episodes);
        imdbIDET = findViewById(R.id.visual_form_imdb_id);
        imdbRatingET = findViewById(R.id.visual_form_imdb_rating);
        imdbPosterIV = findViewById(R.id.visual_form_imdb_poster);

        Intent intent = getIntent();
        String doubanId = intent.getStringExtra("doubanId");
        doubanIdET.setText(doubanId);

        getVisualFromDouban(doubanId);
        getIMDBID(doubanId);
    }

    public void getVisualFromDouban(String doubanId) {
        String url = "https://api.douban.com/v2/movie/subject/" + doubanId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    try {
                        Visual visual = new Visual();
                        visual.setTitle(result.getString("title"));
                        JSONObject images = result.getJSONObject("images");
                        visual.setPoster(images.getString("large"));
                        visual.setDoubanId(result.getString("id"));
                        JSONObject rating = result.getJSONObject("rating");
                        visual.setDoubanRating(rating.getDouble("average"));

                        String originalTitle = result.getString("original_title");
                        String summary = result.getString("summary");
                        String doubanPoster = images.getString("large");
                        Double doubanRating = rating.getDouble("average");
                        String episodes = result.getString("episodes_count");

                        titleET.setText(visual.getTitle());
                        originTitleET.setText(originalTitle);
                        summaryET.setText(summary);
                        Picasso.get().load(doubanPoster).into(doubanPosterIV);
                        doubanRatingET.setText(doubanRating.toString());
                        if (episodes.equals(null)) {
                            episodesET.setText("1");
                        } else {
                            episodesET.setText(episodes);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void getIMDBID(String doubanId) {
        String url = "https://what-i-watched.herokuapp.com/api/get_imdb_id?douban_id=" + doubanId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            String imdbId = result.getString("imdb_id");
                            imdbIDET.setText(imdbId);
                            getDataFromIMDB(imdbId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void getDataFromIMDB(String imdbId) {
        String url = "https://www.omdbapi.com/?apikey=6ad10fa5&i=" + imdbId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            String rating = result.getString("imdbRating");
                            String poster = result.getString("Poster");
                            imdbRatingET.setText(rating);
                            Picasso.get().load(poster).into(imdbPosterIV);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

}

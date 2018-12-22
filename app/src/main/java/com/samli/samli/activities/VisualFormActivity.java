package com.samli.samli.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.models.Visual;

import org.json.JSONException;
import org.json.JSONObject;

public class VisualFormActivity extends AppCompatActivity {
    EditText title;
    EditText originTitle;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        title = findViewById(R.id.visual_title);
        originTitle = findViewById(R.id.visual_original_title);

        Intent intent = getIntent();
        String doubanId = intent.getStringExtra("doubanId");
        getVisualFromDouban(doubanId);
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
                        title.setText(visual.getTitle());

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

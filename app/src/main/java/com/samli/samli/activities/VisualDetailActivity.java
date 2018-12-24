package com.samli.samli.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisualDetailActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Visual visual;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(VisualDetailActivity.this, VisualFormActivity.class);
                intent.putExtra("doubanId", visual.getDoubanId());
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String poster = intent.getStringExtra("poster");

        TextView titleView = (TextView) findViewById(R.id.visual_detail_title);
        ImageView iv = (ImageView) findViewById(R.id.visual_detail_poster);
        Button button = (Button) findViewById(R.id.visual_increase_episode);

        titleView.setText(title);
        Picasso.get().load(poster).into(iv);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://what-i-watched.herokuapp.com/api/visual/" + id, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        JSONObject result = jsonObject.getJSONObject("result");
                        visual = new Visual();
                        visual.setId(result.getString("id"));
                        visual.setTitle(result.getString("title"));
                        visual.setPoster(result.getString("poster"));
                        visual.setDoubanId(result.getString("douban_id"));
                        visual.setDoubanRating(result.getDouble("douban_rating"));

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://what-i-watched.herokuapp.com/api/visual/increase_episode?id=" + id, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String result = jsonObject.getString("current_episode");
                                Snackbar.make(view, result, Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
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
        });

    }

}

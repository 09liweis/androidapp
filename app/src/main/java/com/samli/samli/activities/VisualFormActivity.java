package com.samli.samli.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.adapters.VisualListAdapter;
import com.samli.samli.models.Visual;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VisualFormActivity extends AppCompatActivity {
    TextView doubanSearchText;
    Button doubanSearchButton;
    TextView displayText;
    String doubanSearchAPI = "https://api.douban.com/v2/movie/search?q=";
    RequestQueue requestQueue;
    private RecyclerView visualListRV;
    VisualListAdapter visualListAdapter;
    ArrayList<Visual> visualList;

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

        visualList = new ArrayList<Visual>();
        visualListRV = findViewById(R.id.visual_search_list);
        visualListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        doubanSearchText = (TextView) findViewById(R.id.douban_search_text);
        displayText = (TextView) findViewById(R.id.display_text);
        doubanSearchButton = (Button) findViewById(R.id.douban_search_button);

        doubanSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final String url = doubanSearchAPI + doubanSearchText.getText();
            StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            handleDoubanSearch(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayText.setText(error.toString());
                    }
                });
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
            }
        });
    }

    private void handleDoubanSearch(String response) throws JSONException {
        visualList.clear();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(jsonObject.getString("subjects"));

        int length = jsonArray.length();

        for(int i = 0; i < length; i++) {
            Visual visual = new Visual();
            JSONObject json = jsonArray.getJSONObject(i);;

            visual.setTitle(json.getString("title"));
            visual.setDoubanId(json.getString("id"));
            JSONObject images = new JSONObject(json.getString("images"));
            visual.setPoster(images.getString("large"));
            JSONObject rating = new JSONObject(json.getString("rating"));
            visual.setDoubanRating(rating.getDouble("average"));

            visualList.add(visual);
        }

        visualListAdapter = new VisualListAdapter(getApplicationContext(), visualList);
        visualListRV.setAdapter(visualListAdapter);
    }

}

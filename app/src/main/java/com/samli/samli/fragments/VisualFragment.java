package com.samli.samli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.activities.VisualSearchFormActivity;
import com.samli.samli.adapters.VisualListAdapter;
import com.samli.samli.models.Visual;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualFragment extends Fragment {

    private RecyclerView visualListRV;
    VisualListAdapter visualListAdapter;

    ArrayList<Visual> visualList;
    RequestQueue requestQueue;
    String visualAPI = "http://what-i-watched.herokuapp.com/api/visuals";

    public VisualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisualFragment newInstance() {
        VisualFragment fragment = new VisualFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visualList = new ArrayList<Visual>();

        getVisualList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_visual_list, container, false);
        visualListRV = rootView.findViewById(R.id.visual_list);
        visualListRV.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VisualSearchFormActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void getVisualList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, visualAPI, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                    try {
                        JSONArray visuals = jsonObject.getJSONArray("results");
                        handleTodoJSON(visuals);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext().getApplicationContext(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void handleTodoJSON(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            Visual visual = new Visual();
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                visual.setId(json.getString("id"));
                visual.setTitle(json.getString("title"));
                visual.setDoubanId(json.getString("douban_id"));
                visual.setPoster(json.getString("poster"));
                visual.setDoubanRating(json.getDouble("douban_rating"));
                if (json.has("episodes")) {
                    visual.setEpisodes(json.getInt("episodes"));
                }
                if (json.has("current_episode")) {
                    visual.setCurrentEpisode(json.getInt("current_episode"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            visualList.add(visual);
        }

        visualListAdapter = new VisualListAdapter(getContext(), visualList);
        visualListRV.setAdapter(visualListAdapter);
    }
}

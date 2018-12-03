package com.samli.samli.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.samli.samli.activities.VisualDetailActivity;
import com.samli.samli.models.Visual;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualListAdapter extends RecyclerView.Adapter<VisualListAdapter.DataViewHolder> {

    private Context mContext;
    private ArrayList<Visual> visualList;
    RequestQueue requestQueue;

    public VisualListAdapter(Context mContext, ArrayList<Visual> visualList) {
        this.mContext = mContext;
        this.visualList = visualList;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.visual, parent, false);
        //使用代码设置宽高（xml布局设置无效时）
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        final DataViewHolder holder = new DataViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = holder.getAdapterPosition();
                Visual visual = visualList.get(pos);
                if (visual.getId() == null) {
                    String url = "https://api.douban.com/v2/movie/subject/" + visual.getDoubanId();
                    Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject result) {
                                    try {
                                        Visual visual = new Visual();
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
                    requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                } else {
                    Intent intent = new Intent(mContext.getApplicationContext(), VisualDetailActivity.class);
                    intent.putExtra("id", visual.getId());
                    intent.putExtra("title", visual.getTitle());
                    intent.putExtra("poster", visual.getPoster());
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Visual visual = visualList.get(position);
        holder.visualTitle.setText(visual.getTitle());
        holder.doubanId.setText("豆瓣ID：" + visual.getDoubanId());
        holder.doubanRating.setText("豆瓣评分: " + Double.toString(visual.getDoubanRating()));
        Picasso.get().load(visual.getPoster()).into(holder.poster);
        if (visual.getCurrentEpisode() != null) {
            holder.episodesStatus.setText(Integer.toString(visual.getCurrentEpisode()) + " / " + Integer.toString(visual.getEpisodes()));
        }
    }

    @Override
    public int getItemCount() {
        return visualList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView visualTitle;
        TextView doubanId;
        ImageView poster;
        TextView doubanRating;
        TextView episodesStatus;
        public DataViewHolder(View itemView) {
            super(itemView);
            visualTitle = itemView.findViewById(R.id.visual_title);
            doubanId = itemView.findViewById(R.id.douban_id);
            poster = itemView.findViewById(R.id.visual_poster);
            doubanRating = itemView.findViewById(R.id.douban_rating);
            episodesStatus = itemView.findViewById(R.id.episodes_status);
        }
    }
}


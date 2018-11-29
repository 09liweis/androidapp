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

import com.samli.samli.R;
import com.samli.samli.activities.VisualDetailActivity;
import com.samli.samli.models.Visual;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VisualListAdapter extends RecyclerView.Adapter<VisualListAdapter.DataViewHolder> {

    private Context mContext;
    private ArrayList<Visual> visualList;

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
                Intent intent = new Intent(mContext.getApplicationContext(), VisualDetailActivity.class);
//                Toast.makeText(mContext, "Test" + visual.getTitle(), Toast.LENGTH_SHORT).show();
                intent.putExtra("id", visual.getId());
                intent.putExtra("title", visual.getTitle());
                intent.putExtra("poster", visual.getPoster());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Visual visual = visualList.get(position);
        holder.visualTitle.setText(visual.getTitle());
        holder.doubanId.setText(visual.getDoubanId());
        Picasso.get().load(visual.getPoster()).into(holder.poster);
        holder.doubanRating.setText(Double.toString(visual.getDoubanRating()));
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
        public DataViewHolder(View itemView) {
            super(itemView);
            visualTitle = itemView.findViewById(R.id.visual_title);
            doubanId = itemView.findViewById(R.id.douban_id);
            poster = itemView.findViewById(R.id.visual_poster);
            doubanRating = itemView.findViewById(R.id.douban_rating);
        }
    }
}


package com.samli.samli.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samli.samli.R;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.DataViewHolder> {

    private Context mContext;
    private ArrayList<String> mList;

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,null);
        //使用代码设置宽高（xml布局设置无效时）
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        DataViewHolder holder = new DataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.tv_data.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView tv_data;
        public DataViewHolder(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_recycle);
        }
    }
}


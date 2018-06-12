package com.samli.samli.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samli.samli.R;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.DataViewHolder> {
    private Context context;

    public TransactionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction,null);
        //使用代码设置宽高（xml布局设置无效时）
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        DataViewHolder holder = new DataViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(DataViewHolder viewHolder, int position) {

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public DataViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.samli.samli.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samli.samli.R;
import com.samli.samli.activities.TodoFormActivity;
import com.samli.samli.models.Todo;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.DataViewHolder> {

    private Context mContext;
    private ArrayList<Todo> todoList;

    public TodoListAdapter(Context mContext, ArrayList<Todo> todoList) {
        this.mContext = mContext;
        this.todoList = todoList;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo,null);
        //使用代码设置宽高（xml布局设置无效时）
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        final DataViewHolder holder = new DataViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Integer pos = holder.getAdapterPosition();
                Todo todo = todoList.get(pos);
                Intent intent = new Intent(mContext.getApplicationContext(), TodoFormActivity.class);
                intent.putExtra("id", todo.getId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.todoName.setText(todo.getName() + " - " + todo.getDate());
        holder.todoStatus.setText(todo.getStatus());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView todoName;
        TextView todoStatus;
        public DataViewHolder(View itemView) {
            super(itemView);
            todoName = itemView.findViewById(R.id.todo_name);
            todoStatus = itemView.findViewById(R.id.todo_status);
        }
    }
}


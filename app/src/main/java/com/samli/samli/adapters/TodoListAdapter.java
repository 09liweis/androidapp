package com.samli.samli.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.activities.TodoFormActivity;
import com.samli.samli.models.Todo;
import com.samli.samli.models.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.DataViewHolder> {

  private Context mContext;
  private ArrayList<Todo> todoList;
  String url = "https://samliweisen.herokuapp.com/api/todos/";
  RequestQueue requestQueue;

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
        int pos = holder.getAdapterPosition();
        Todo todo = todoList.get(pos);
        Intent intent = new Intent(mContext.getApplicationContext(), TodoFormActivity.class);
        intent.putExtra("id", todo.getId());
        mContext.startActivity(intent);
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(DataViewHolder holder, final int position) {
    final Todo todo = todoList.get(position);
    holder.todoName.setText(todo.getName());
    String status = todo.getStatus();
    int color = R.color.pending;
    if (status.equals("done")) {
      color = R.color.done;
    }
    if (status.equals("working")) {
      color = R.color.working;
    }
    holder.todoStatus.setBackgroundColor(ContextCompat.getColor(mContext,color));
    String date = todo.getDate();
    if (date == null) {
      holder.todoDate.setVisibility(View.GONE);
    } else {
      holder.todoDate.setText(date);
    }
    holder.todoDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deleteTodo(position,todo.getId());
      }
    });
  }

  public void deleteTodo(final int i, String id) {
    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.DELETE, url + id,null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject todo) {
        todoList.remove(i);
        notifyItemRemoved(i);
        notifyDataSetChanged();
        Util.showToast(mContext,"Delete todo " + i + " Success");
      }

    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Util.showToast(mContext,error.toString());
      }
    });
    requestQueue = Volley.newRequestQueue(mContext);
    requestQueue.add(sr);
  }

  @Override
  public int getItemCount() {
    return todoList.size();
  }

  public static class DataViewHolder extends RecyclerView.ViewHolder{
    TextView todoName;
    View todoStatus;
    TextView todoDate;
    Button todoDelete;
    public DataViewHolder(View itemView) {
      super(itemView);
      todoName = itemView.findViewById(R.id.todo_name);
      todoStatus = itemView.findViewById(R.id.todo_status);
      todoDate = itemView.findViewById(R.id.todo_date);
      todoDelete = itemView.findViewById(R.id.todo_delete);
    }
  }
}


package com.samli.samli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.samli.samli.R;
import com.samli.samli.activities.TodoFormActivity;
import com.samli.samli.adapters.TodoListAdapter;
import com.samli.samli.models.Todo;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TodoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoListFragment extends Fragment {
  private RecyclerView todo_list;
  AVLoadingIndicatorView avi;
  TodoListAdapter todoListAdapter;

  //todo list
  ArrayList<Todo> todoList;
  RequestQueue requestQueue;
  String todoAPI = "http://samliweisen.herokuapp.com/api/todos";

  public TodoListFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment TodoListFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static TodoListFragment newInstance() {
    TodoListFragment fragment = new TodoListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
    todo_list = rootView.findViewById(R.id.todo_list);
    todo_list.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

    FloatingActionButton fab = rootView.findViewById(R.id.todo_add);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getContext(), TodoFormActivity.class);
        intent.putExtra("id", "");
        startActivity(intent);
      }
    });
    avi = rootView.findViewById(R.id.avi);
    avi.show();
    return rootView;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    todoList = new ArrayList<>();


    //call todo list api
    getTodoList();
  }

  public void getTodoList() {
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(todoAPI,
            new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray jsonArray) {
                handleTodoJSON(jsonArray);
              }

            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                getTodoList();
                Toast.makeText(getContext().getApplicationContext(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
    );
    requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
    requestQueue.add(jsonArrayRequest);
  }

  public void handleTodoJSON(JSONArray jsonArray) {
    for(int i = 0; i < jsonArray.length(); i++) {
      Todo todo = new Todo();
      JSONObject json;
      try {
        json = jsonArray.getJSONObject(i);
        todo.setId(json.getString("_id"));
        todo.setName(json.getString("name"));
        todo.setStatus(json.getString("status"));
        String date = json.getString("date");
        if (!date.isEmpty()) {
          todo.setDate(json.getString("date"));
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
      todoList.add(todo);
    }

    todoListAdapter = new TodoListAdapter(getContext().getApplicationContext(), todoList);
    todo_list.setAdapter(todoListAdapter);
  }
}

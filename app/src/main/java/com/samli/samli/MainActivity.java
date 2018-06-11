package com.samli.samli;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.adapters.TodoListAdapter;
import com.samli.samli.fragments.TodoListFragment;
import com.samli.samli.models.Todo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView todo_list;
    TodoListAdapter todoListAdapter;

    //todo list
    ArrayList<Todo> todoList;
    RequestQueue requestQueue;
    String todoAPI = "http://samliweisen.herokuapp.com/api/todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                Toast.makeText(MainActivity.this, item.getItemId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        TodoListFragment todoListFragment = new TodoListFragment();
//        fragmentTransaction.replace(R.id.frame_content, todoListFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        todoList = new ArrayList<Todo>();
        todo_list = (RecyclerView) findViewById(R.id.todo_list);
        todo_list.setLayoutManager(new LinearLayoutManager(this));


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
                        Toast.makeText(MainActivity.this, "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void handleTodoJSON(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            Todo todo = new Todo();
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                todo.setId(json.getString("_id"));
                todo.setName(json.getString("name"));
                todo.setStatus(json.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            todoList.add(todo);
        }
        System.out.print(todoList);
        todoListAdapter = new TodoListAdapter(this, todoList);
        todo_list.setAdapter(todoListAdapter);
    }
}

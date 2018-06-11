package com.samli.samli;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.adapters.TodoListAdapter;
import com.samli.samli.models.FIleHelper;
import com.samli.samli.models.Todo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int itemId = item.getItemId();
//                Toast.makeText(MainActivity.this, itemId, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        todoList = new ArrayList<Todo>();
        todo_list = (RecyclerView) findViewById(R.id.todo_list);
        todo_list.setLayoutManager(new LinearLayoutManager(this));


        //call todo list api
//        getTodoList();

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

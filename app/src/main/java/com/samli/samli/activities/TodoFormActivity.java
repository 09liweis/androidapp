package com.samli.samli.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.fragments.TodoListFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class TodoFormActivity extends AppCompatActivity {

  EditText todoNameET;
  Button todoDateBT;
  Button todoAddBt;
  RequestQueue requestQueue;
  String url = "https://samliweisen.herokuapp.com/api/todos/";
  String id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo_form);

    todoNameET = findViewById(R.id.todo_form_name);
    todoDateBT = findViewById(R.id.todo_form_date);
    todoAddBt = findViewById(R.id.todo_form_add);

    Intent intent = getIntent();
    id = intent.getStringExtra("id");
    if (id.length() > 0) {
      getTodo(id);
    }
    todoDateBT.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
//        DatePickerDialog.datePickerDialog = new DatePickerDialog(this,this,Calen)
      }
    });
    todoAddBt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        submitTodo(view);
      }
    });
  }

  protected void getTodo(String id) {
    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, url + id,null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject res) {
        try {
          JSONObject todo = res;
          todoNameET.setText(todo.getString("name"));
          todoDateBT.setText(todo.getString("date"));
        } catch (JSONException e) {

        }
      }

    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });
    requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(sr);
  }


  protected void submitTodo(final View view) {
    int method = Request.Method.POST;
    JSONObject params = new JSONObject();
    try {
      params.put("name", todoNameET.getText());
      params.put("date", todoDateBT.getText());
      params.put("status", "pending");
      if (id.length() > 0) {
        params.put("_id",id);
        method = Request.Method.PUT;
        url = url + id;
      }
    } catch (JSONException e) {

    }

    JsonObjectRequest sr = new JsonObjectRequest(method, url, params,new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        Toast.makeText(TodoFormActivity.this, "Success", Toast.LENGTH_SHORT).show();
        finish();
      }
    },
    new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
      }
    });
    requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(sr);
  }
}

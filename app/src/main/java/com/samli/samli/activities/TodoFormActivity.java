package com.samli.samli.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class TodoFormActivity extends AppCompatActivity {

    EditText todoNameET;
    EditText todoDateET;
    Button todoAddBt;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);

        todoNameET = findViewById(R.id.todo_form_name);
        todoDateET = findViewById(R.id.todo_form_date);
        todoAddBt = findViewById(R.id.todo_form_add);

        todoAddBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTodo(view);
            }
        });
    }

    protected void submitTodo(final View view) {
        JSONObject params = new JSONObject();
        try {
            params.put("name", todoNameET.getText());
            params.put("date", todoDateET.getText());
            params.put("status", "pending");
        } catch (JSONException e) {

        }

        String url = "https://samliweisen.herokuapp.com/api/todos";

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Snackbar.make(view, "Success", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
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

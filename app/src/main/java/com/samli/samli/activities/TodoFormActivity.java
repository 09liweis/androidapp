package com.samli.samli.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samli.samli.R;
import com.samli.samli.models.Util;
import com.wang.avi.AVLoadingIndicatorView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class TodoFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

  EditText todoNameET;
  Button todoDateBT;
  Button todoAddBt;
  RadioGroup rdStatus;
  RadioButton rdPending;
  RadioButton rdWorking;
  RadioButton rdDone;
  RequestQueue requestQueue;
  String url = "https://samliweisen.herokuapp.com/api/todos/";
  String id;
  AVLoadingIndicatorView avi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo_form);

    todoNameET = findViewById(R.id.todo_form_name);
    todoDateBT = findViewById(R.id.todo_form_date);
    todoAddBt = findViewById(R.id.todo_form_add);

    rdStatus = findViewById(R.id.todo_form_status);
    rdPending = findViewById(R.id.pending);
    rdWorking = findViewById(R.id.working);
    rdDone = findViewById(R.id.done);

    avi = findViewById(R.id.todo_form_avi);

    rdStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int i) {

      }
    });

    Intent intent = getIntent();
    id = intent.getStringExtra("id");
    if (!id.equals("")) {
      getTodo(id);
    }
    todoDateBT.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        showDatePickerDialog();
      }
    });
    todoAddBt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        submitTodo();
      }
    });
  }
  private void showDatePickerDialog() {
    String date = todoDateBT.getText().toString();
    Integer year = Calendar.getInstance().get(Calendar.YEAR);
    Integer month = Calendar.getInstance().get(Calendar.MONTH);
    Integer day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    if (date.compareTo("Select Date") != 0) {
      List<String> dateAry = Arrays.asList(date.split("-"));
      year = Integer.parseInt(dateAry.get(0));
      month = Integer.parseInt((dateAry.get(1))) - 1;
      day = Integer.parseInt((dateAry.get(2)));
    }
    DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            this,
            year,
            month,
            day
    );
    datePickerDialog.show();
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int m, int d) {
    String month = ((m + 1) > 9) ? Integer.toString(m + 1) : "0" + (m + 1);
    String day = ((d > 9) ? Integer.toString(d) : "0"+d);
    String date = year + "-" + month + "-" + day;
    todoDateBT.setText(date);
  }

  protected void getTodo(String id) {
    avi.show();
    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, url + id,null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject todo) {
        try {
          todoNameET.setText(todo.getString("name"));
          todoDateBT.setText(todo.getString("date"));
          String status = todo.getString("status");
          if (status.equals(rdPending.getText().toString())) {
            rdStatus.check(rdPending.getId());
          }
          if (status.equals(rdWorking.getText().toString())) {
            rdStatus.check(rdWorking.getId());
          }
          if (status.equals(rdDone.getText().toString())) {
            rdStatus.check(rdDone.getId());
          }
          avi.hide();
        } catch (JSONException e) {
          Util.showToast(TodoFormActivity.this,e.toString());
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


  protected void submitTodo() {
    int method = Request.Method.POST;
    JSONObject params = new JSONObject();
    try {
      params.put("name", todoNameET.getText());
      params.put("date", todoDateBT.getText());
      RadioButton rb = findViewById(rdStatus.getCheckedRadioButtonId());
      params.put("status", rb.getText());
      if (id.length() > 0) {
        params.put("_id",id);
        method = Request.Method.PUT;
        url = url + id;
      }
    } catch (JSONException e) {
      Util.showToast(TodoFormActivity.this,e.toString());
    }

    JsonObjectRequest sr = new JsonObjectRequest(method, url, params,new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        Util.showToast(TodoFormActivity.this,"Success");
        finish();
      }
    },
    new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Util.showToast(TodoFormActivity.this,error.toString());
      }
    });
    requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(sr);
  }
}

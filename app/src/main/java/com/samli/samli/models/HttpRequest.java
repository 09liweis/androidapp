package com.samli.samli.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequest {
  private RequestQueue requestQueue;
  private Context mContext;
  private String url;

  public HttpRequest(Context mContext, RequestQueue requestQueue, String url) {
    this.requestQueue = requestQueue;
    this.mContext = mContext;
    this.url = url;
  }

  public JSONObject getJSONObject(final String key) {
    JSONObject jsonObject = new JSONObject();
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
      new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
          try {
            jsonObject = jsonObject.getJSONObject(key);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }

      },
      new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
      });
    requestQueue = Volley.newRequestQueue(mContext);
    requestQueue.add(jsonObjectRequest);
    return jsonObject;

  }
}

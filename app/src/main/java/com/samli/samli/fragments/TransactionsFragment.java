package com.samli.samli.fragments;

import android.os.Bundle;
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
import com.samli.samli.adapters.TodoListAdapter;
import com.samli.samli.adapters.TransactionsAdapter;
import com.samli.samli.models.Todo;
import com.samli.samli.models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {
    private RecyclerView transactionsView;
    TransactionsAdapter transactionsAdapter;

    ArrayList<Transaction> transactions;
    RequestQueue requestQueue;
    String api = "http://samliweisen.herokuapp.com/api/transactions";

    public TransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance() {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactions = new ArrayList<Transaction>();
        getTransactions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        transactionsView = rootView.findViewById(R.id.transactions_list);
        transactionsView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        return rootView;
    }

    public void getTransactions() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        handleTodoJSON(jsonArray);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getTransactions();
                        Toast.makeText(getContext().getApplicationContext(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    public void handleTodoJSON(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            Transaction transaction = new Transaction();
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                transaction.setId(json.getString("_id"));
                transaction.setTitle(json.getString("title"));
                transaction.setPrice(json.getString("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            transactions.add(transaction);
        }

        transactionsAdapter = new TransactionsAdapter(getContext().getApplicationContext(), transactions);
        transactionsView.setAdapter(transactionsAdapter);
    }
}

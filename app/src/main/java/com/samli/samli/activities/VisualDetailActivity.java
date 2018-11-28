package com.samli.samli.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samli.samli.R;
import com.squareup.picasso.Picasso;

public class VisualDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String poster = intent.getStringExtra("poster");

        TextView titleView = (TextView) findViewById(R.id.visual_detail_title);
        ImageView iv = (ImageView) findViewById(R.id.visual_detail_poster);

        titleView.setText(title);
        Picasso.get().load(poster).into(iv);

        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

    }

}

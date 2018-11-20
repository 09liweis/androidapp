package com.samli.samli;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.samli.samli.fragments.MusicFragment;
import com.samli.samli.fragments.TodoListFragment;
import com.samli.samli.fragments.TransactionsFragment;
import com.samli.samli.fragments.VisualFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
//                    case R.id.navigation_music:
//                        loadFragment(new MusicFragment());
//                        break;
                    case R.id.navigation_todos:
                        loadFragment(new TodoListFragment());
                        break;
                    case R.id.navigation_visuals:
                        loadFragment(new VisualFragment());
                        break;
                    case R.id.navigation_transactions:
                        loadFragment(new TransactionsFragment());
                        break;
                }
                return true;
            }
        });


        loadFragment(new TodoListFragment());

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
            return true;
        }
        return false;
    }
}

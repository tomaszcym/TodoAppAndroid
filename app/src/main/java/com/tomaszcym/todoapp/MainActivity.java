package com.tomaszcym.todoapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tomaszcym.todoapp.model.Task;
import com.tomaszcym.todoapp.repo.TaskRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Optional;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TaskRepository.init();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String colorName = sharedPreferences.getString("appBackgroundColor", "");
        int color = 0;
        try {
            color = Color.parseColor(colorName);
        }
        catch (Exception e) {
            if(colorName.trim().length() > 0) {
                String text = this.getString(R.string.unknown_color);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }

            color = Color.parseColor("#ffffff");
        }

        findViewById(R.id.activityMain).setBackgroundColor(color);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        View view =  findViewById(R.id.nav_host_fragment);

        //noinspection SimplifiableIfStatement
        switch(id) {
//            case R.id.action_tasks:
//                Navigation.findNavController(view).navigate(R.id.action_TaskShowFragment_to_TaskListFragment);
//                return true;
//            case R.id.action_new_task:
//                Navigation.findNavController(view).navigate(R.id.action_TaskListFragment_to_ShowTaskFragment);
//                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
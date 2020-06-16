package com.tomaszcym.todoapp;

import android.app.FragmentTransaction;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

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
            case R.id.action_tasks:
                Navigation.findNavController(view).navigate(R.id.action_TaskShowFragment_to_TaskListFragment);
                return true;
            case R.id.action_new_task:
                Navigation.findNavController(view).navigate(R.id.action_TaskListFragment_to_ShowTaskFragment);
                return true;
            case R.id.action_settings:
                Log.println(Log.INFO, "Menu: ", "Settings");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
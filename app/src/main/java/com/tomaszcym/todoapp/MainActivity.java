package com.tomaszcym.todoapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tomaszcym.todoapp.model.Task;
import com.tomaszcym.todoapp.repo.TaskRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

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

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_tasks:

                RecyclerView rv = (RecyclerView) findViewById(R.id.task_list_recycler_view);
                Log.println(Log.INFO, "TASKLIST: ", String.valueOf(rv.getAdapter().getItemCount()));

                Log.println(Log.INFO, "Menu: ", "Tasks");
                return true;
            case R.id.action_new_task:
                Task task = new Task("Test", "Jakis opis zadania");
                Log.println(Log.INFO, "Task: ", '#' + String.valueOf(task.getId()) + " " + task.toString());
                Log.println(Log.INFO, "Task:", "Date: " + task.getCreated_date().toString() + " | Time: " + task.getCreated_time().toString());


                Log.println(Log.INFO, "TaskList", String.valueOf(TaskRepository.getAll().size()));
                TaskRepository.addTask(task);
                Log.println(Log.INFO, "TaskList", String.valueOf(TaskRepository.getAll().size()));
                Optional<Task> testFromRepo = TaskRepository.getTaskById(1);
                Log.println(Log.INFO, "TaskList", testFromRepo.map(Task::toString).orElse("Nie znaleziono"));

                Log.println(Log.INFO, "Menu: ", "New Tasks");
                return true;
            case R.id.action_settings:
                Log.println(Log.INFO, "Menu: ", "Settings");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
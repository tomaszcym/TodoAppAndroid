package com.tomaszcym.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tomaszcym.todoapp.adapter.TaskListRecyclerViewAdapter;
import com.tomaszcym.todoapp.model.Task;
import com.tomaszcym.todoapp.repo.TaskRepository;

import java.util.List;

public class TaskListFragment extends Fragment {

    private RecyclerView taskListRecyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskListRecyclerView = (RecyclerView) view.findViewById(R.id.task_list_recycler_view);

        TaskListRecyclerViewAdapter taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter((List<Task>) TaskRepository.getAll());
        taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(TaskListFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }
}
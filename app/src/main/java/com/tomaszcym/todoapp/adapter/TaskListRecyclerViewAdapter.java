package com.tomaszcym.todoapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tomaszcym.todoapp.R;
import com.tomaszcym.todoapp.TaskListFragment;
import com.tomaszcym.todoapp.model.Task;

import java.util.Collection;
import java.util.List;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder> {

    private List<Task> taskList;
    private LayoutInflater inflater;

    public TaskListRecyclerViewAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        this.inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.task_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = this.taskList.get(position);

        holder.taskNameTextView.setText(task.getId() + " | " + task.getName());
        holder.statusCheckbox.setChecked(task.getStatus());
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView taskNameTextView;
        public CheckBox statusCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            statusCheckbox = itemView.findViewById(R.id.statusCheckBox);

            statusCheckbox.setOnClickListener(v -> {
                CheckBox checkbox = (CheckBox) v;
                int position = getAdapterPosition();
                taskList.get(position).setStatus(checkbox.isChecked());

                Log.println(Log.INFO, "Status checkbox", String.valueOf(taskList.get(position).getStatus()));
            });

            taskNameTextView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Task task = taskList.get(position);

                Bundle bundle = new Bundle();
                bundle.putInt("task_id", task.getId());

                Navigation.findNavController(v).navigate(R.id.action_TaskListFragment_to_ShowTaskFragment, bundle);
            });
        }
    }
}

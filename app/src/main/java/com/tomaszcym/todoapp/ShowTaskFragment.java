package com.tomaszcym.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.tomaszcym.todoapp.model.Task;
import com.tomaszcym.todoapp.repo.TaskRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

public class ShowTaskFragment extends Fragment {

    public EditText taskNameTextInput;
    public EditText taskValueEditText;
    public EditText taskDateEditText;
    public EditText taskTimeEditText;
    public CheckBox taskStatusCheckbox;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_task, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.taskNameTextInput = (EditText) view.findViewById(R.id.taskNameEditText);
        this.taskValueEditText = (EditText) view.findViewById(R.id.taskValueEditText);
        this.taskDateEditText = (EditText) view.findViewById(R.id.taskDateEditText);
        this.taskTimeEditText = (EditText) view.findViewById(R.id.taskTimeEditText);
        this.taskStatusCheckbox = (CheckBox) view.findViewById(R.id.taskStatusCheckbox);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


        int id = 0;
        if(getArguments() != null)
            id = getArguments().getInt("task_id");

        Optional<Task> repoTask = TaskRepository.getTaskById(id);

        Task task = null;
        if(repoTask.isPresent()) {
            task = repoTask.get();

            this.taskNameTextInput.setText(task.getName());
            this.taskValueEditText.setText(task.getValue());
            if(task.getDate() != null)
                this.taskDateEditText.setText(task.getDate().format(dateFormat));
            if(task.getTime() != null)
                this.taskTimeEditText.setText(task.getTime().format(timeFormat));
            this.taskStatusCheckbox.setChecked(task.getStatus());


            Log.println(Log.INFO, "Show id=", String.valueOf(id));
        }
        else {
            task = new Task();
            Log.println(Log.INFO, "Show id=", "NEW TASSSSSSSSSSSSSSSSSSSSK!");
        }


        view.findViewById(R.id.taskDateChangeButton).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    LocalDate date = LocalDate.of(year, month, dayOfMonth);
                    taskDateEditText.setText(date.format(dateFormat));
                }
            }, year, month, day);
            datePickerDialog.show();
        });

        view.findViewById(R.id.taskTimeChangeButton).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    LocalTime time = LocalTime.of(hourOfDay, minute);
                    taskTimeEditText.setText(time.format(timeFormat));
                }
            }, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
            timePickerDialog.show();
        });


        int finalId = id;
        Task finalTask = task;
        view.findViewById(R.id.saveButton).setOnClickListener(v -> {

            if(!this.isTaskNameValid(this.taskNameTextInput))
                return;

            finalTask.setName(this.taskNameTextInput.getText().toString());
            finalTask.setValue(this.taskValueEditText.getText().toString());
            if(this.taskDateEditText.getText().toString().length() > 0)
                finalTask.setDate(LocalDate.parse(this.taskDateEditText.getText().toString(), dateFormat));
            if(this.taskTimeEditText.getText().toString().length() > 0)
                finalTask.setTime(LocalTime.parse(this.taskTimeEditText.getText().toString(), timeFormat));
            finalTask.setStatus(this.taskStatusCheckbox.isChecked());

            Context context = getContext();
            CharSequence text = "";

            if(finalId != 0) {
                try {
                    TaskRepository.update(finalTask.getId(), finalTask);
                    if(context != null)
                        text = context.getString(R.string.update_task__success);
                }
                catch (Exception e) {
                    Log.println(Log.ERROR, "ShowTaskFragment::saveButton::clickListener", "Error when update new task");
                    if(context != null)
                        text = context.getString(R.string.update_task__error);
                }
            }
            else {
                try {
                    TaskRepository.addTask(finalTask);
                    if(context != null)
                        text = context.getString(R.string.new_task__success);
                }
                catch (Exception e) {
                    Log.println(Log.ERROR, "ShowTaskFragment::saveButton::clickListener", "Error when adding new task");
                    if(context != null)
                        text = context.getString(R.string.new_task__error);
                }
            }

            if(context == null) {
                Log.println(Log.WARN, "ShowTaskFragment::saveButton::clickListener", "Context is null! Toasts will not be shown.");
            }
            else {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }

            Navigation.findNavController(v).navigate(R.id.action_TaskShowFragment_to_TaskListFragment);
        });

    }

    private boolean isTaskNameValid(EditText input) {
        if(input.getText().toString().length() < 3) {
            Context context = getContext();
            if(context != null) {
                CharSequence text = context.getString(R.string.task_name_length_validator, 3);
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }
}

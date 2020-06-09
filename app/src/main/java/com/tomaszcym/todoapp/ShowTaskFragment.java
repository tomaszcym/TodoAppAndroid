package com.tomaszcym.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ReportFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tomaszcym.todoapp.model.Task;
import com.tomaszcym.todoapp.repo.TaskRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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

        int id = 0;
        if(getArguments() != null)
            id = getArguments().getInt("task_id");

        Optional<Task> repoTask = TaskRepository.getTaskById(id);

        if(!repoTask.isPresent()) {
            return;
        }

        Task task = repoTask.get();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        this.taskNameTextInput.setText(task.getName());
        this.taskValueEditText.setText(task.getValue());
        if(task.getDate() != null)
            this.taskDateEditText.setText(task.getDate().format(dateFormat));
        if(task.getTime() != null)
            this.taskTimeEditText.setText(task.getTime().format(timeFormat));
        this.taskStatusCheckbox.setChecked(task.getStatus());


        Log.println(Log.INFO, "Show id=", String.valueOf(id));


        view.findViewById(R.id.taskDateChangeButton).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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


        view.findViewById(R.id.saveButton).setOnClickListener(v -> {
            task.setName(this.taskNameTextInput.getText().toString());
            task.setValue(this.taskValueEditText.getText().toString());
            if(this.taskDateEditText.getText().toString().length() > 0)
                task.setDate(LocalDate.parse(this.taskDateEditText.getText().toString(), dateFormat));
            if(this.taskTimeEditText.getText().toString().length() > 0)
                task.setTime(LocalTime.parse(this.taskTimeEditText.getText().toString(), timeFormat));
            task.setStatus(this.taskStatusCheckbox.isChecked());

            TaskRepository.update(task.getId(), task);
        });

    }
}
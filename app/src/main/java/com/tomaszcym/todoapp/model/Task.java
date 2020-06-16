package com.tomaszcym.todoapp.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

public class Task {

    private static int task_id = 1;

    private int id;
    private String name;
    private String value;
    private LocalDate date;
    private LocalTime time;
    private boolean status = false;

    private LocalDate created_date;
    private LocalTime created_time;

    public Task() {
        this.id = task_id++;
    }

    public Task(String name) {
        this(name, null);
    }

    public Task(String name, String value) {
        this(name, value, null, null);
    }

    public Task(String name, String value, LocalDate date, LocalTime time) {
        this.id = task_id++;
        this.name = name;
        this.value = value;
        this.date = date;
        this.time = time;

        this.created_date = LocalDate.now();
        this.created_time = LocalTime.now();
    }




    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public LocalTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(LocalTime created_time) {
        this.created_time = created_time;
    }


    //    Additional methods
    public boolean toggleStatus() {
        boolean newStatus = !this.status;
        this.status = newStatus;
        return newStatus;
    }


    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}

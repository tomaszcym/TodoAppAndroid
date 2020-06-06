package com.tomaszcym.todoapp.model;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Timer;

public class Task {

    private static int task_id = 1;

    private int id;
    private String name;
    private String value;
    private Date date;
    private Timer time;
    private boolean status = false;

    private Date created_date;
    private Timer created_time;


    public Task(String name, String value) {
        this(name, value, null, null);
    }

    public Task(String name, String value, Date date, Timer time) {
        this.id = task_id++;
        this.name = name;
        this.value = value;
        this.date = date;
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timer getTime() {
        return time;
    }

    public void setTime(Timer time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

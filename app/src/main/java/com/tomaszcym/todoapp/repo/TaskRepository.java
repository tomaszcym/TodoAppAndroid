package com.tomaszcym.todoapp.repo;

import com.tomaszcym.todoapp.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class TaskRepository {

    private static Collection<Task> taskList;
    private static boolean isInitialized = false;

    private TaskRepository() {
    }

    public static void init() {
        if(!isInitialized) {
            taskList = new ArrayList<>(Arrays.asList(
                    new Task("Zakupy", "Zrobić zakupy na grilla."),
                    new Task("Umyć samochód"),
                    new Task("Zrobić projekt", "Projekt na przedmiot aplikacje mobline. Aplikacja TODO w Java'ie.")
            ));
            isInitialized = true;
        }
    }


    //    ======================
//    MODIFIERS
//    ======================
    public static boolean addTask(Task task) {
        return taskList.add(task);
    }

    public static boolean removeById(int id) {
        return taskList.removeIf(task -> {
            return task.getId() == id;
        });
    }


    //    ======================
//    GETTERS
//    ======================
    public static Optional<Task> getTaskById(int id) {
        return taskList.stream().filter(task -> {
            return task.getId() == id;
        }).findFirst();
    }

    public static Collection<Task> getAll() {
        return taskList;
    }


}

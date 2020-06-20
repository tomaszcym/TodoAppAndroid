package com.tomaszcym.todoapp.repo;

import com.tomaszcym.todoapp.model.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class TaskRepository {

    private static List<Task> taskList;
    private static boolean isInitialized = false;

    private TaskRepository() {
    }

    public static void init() {
        if(!isInitialized) {
            Task firstTask = new Task("Całe zadanie", "Opis całego nowego zadania ze wszystkimi polami");
            firstTask.setDate(LocalDate.now());
            firstTask.setTime(LocalTime.now());
            firstTask.setStatus(true);
            taskList = new ArrayList<>(Arrays.asList(
                    firstTask,
                    new Task("Zakupy", "Zrobić zakupy na grilla."),
                    new Task("Umyć samochód"),
                    new Task("Umyć samochód"),
                    new Task("Zrobić projekt", "Projekt na przedmiot aplikacje mobline. Aplikacja TODO w Java'ie.")
            ));
            isInitialized = true;
        }
    }


    //    ======================
//    MODIFIERS
//    ======================
    public static void addTask(Task task) {
        taskList.add(task);
    }

    public static boolean removeById(int id) {
        return taskList.removeIf(task -> {
            return task.getId() == id;
        });
    }

    public static void update(int id, Task task) {
        if(id != task.getId())
            return;


        Optional<Task> repoTask = taskList.stream().filter(t -> {
            return t.getId() == id;
        }).findFirst();

        if(!repoTask.isPresent())
            return;


        int index = taskList.indexOf(repoTask.get());
        taskList.set(index, task);
    }


    //    ======================
//    GETTERS
//    ======================
    public static Optional<Task> getTaskById(int id) {
        return taskList.stream().filter(task -> task.getId() == id).findFirst();
    }

    public static Collection<Task> getAll() {
        return taskList;
    }


}

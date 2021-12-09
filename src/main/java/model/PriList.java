package model;


import java.time.LocalDate;
import java.util.*;

/**
 * PriList is the list that holds the PriTasks.
 */

public class PriList {

    private String title;
    private LinkedList<Task> tasks;


    public PriList (String name){
        this.title = name;
        tasks = new LinkedList<>();
    }

    public void sortTasks() {
        Collections.sort(tasks);
    }

    // Getters and Setters for local variables
    public String getTitle() {
        return title;
    }

    public LinkedList<Task> getTasks(){
        return tasks;
    }

    public void editTask(Task task, String newTitle, String newDescription, int newPriority,
                         LocalDate newDeadline){
        task.setTitle(newTitle);
        task.setStatus(newDescription);
        task.setPriority(newPriority);
        task.setDeadLine(newDeadline);
        sortTasks();
    }
    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public void createTask(String name, String description, int priority, LocalDate deadline) {
        Task task = new Task(name, description, priority, deadline);
        tasks.add(task);
        sortTasks();
    }

    public int getSize(){
        return tasks.size();
    }

}
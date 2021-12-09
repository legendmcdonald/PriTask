package model;

import model.persistence.Persistence;

import java.time.LocalDate;
import java.util.LinkedList;

public class PriTaskManager {
    private LinkedList<PriList> lists;

    public PriTaskManager() {
        lists = new LinkedList<>();
    }

    public void addNewPriorityList(String name) {
        lists.add(new PriList(name));
        save();
    }

    public void removePriorityList(PriList priList) {
        lists.remove(priList);
        save();
    }

    private PriList getPriorityList(PriList priList) {
        for (PriList p : lists) {
            if (p.equals(priList))
                return p;
        }
        return null;
    }

    public void createTask(PriList priList, String taskName, String description, int priority, LocalDate deadline) {
        PriList list = getPriorityList(priList);
        if (list != null)
            list.createTask(taskName, description, priority, deadline);
        save();
    }

    public void deleteTask(PriList priList, Task task) {
        PriList list = getPriorityList(priList);
        if (list != null)
            list.deleteTask(task);
        save();
    }

    public void editTask(PriList priList, Task task, String newTitle, String newDescription, int newPriority,
                         LocalDate newDeadline) {
        PriList list = getPriorityList(priList);
        if (list != null)
            list.editTask(task, newTitle, newDescription, newPriority, newDeadline);
        save();
    }

    public LinkedList<PriList> getLists() {
        return lists;
    }


    // Sorting function
    private void sortTasks() {
        for (PriList l : lists) {
            l.sortTasks();
        }
    }


    // Save and load functions
    private void save() {
        sortTasks();
        Persistence.setList(lists);
        Persistence.saveToJson();
    }

    public void load() {
        Persistence.loadJsonFile();
        lists = Persistence.getListsLoadedFromJson();
    }
}
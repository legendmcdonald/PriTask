package view;

import model.PriList;
import model.Task;

import java.time.LocalDate;
import java.util.LinkedList;

public interface IView {
    void gotoMainMenu(LinkedList<PriList> listOfLists);

    void gotoAddList();

    void gotoAddTask();

    void gotoEditTask();

    void gotoDeleteList(PriList ListToDelete);

    void gotoDeleteTask(Task TaskToDelete);

    String getCurrentlyAddingListName();

    String getCurrentlyAddingTaskName();

    String getCurrentlyAddingTaskDesc();

    int getCurrentlyAddingTaskPriority();

    LocalDate getCurrentlyAddingTaskDeadline();

}

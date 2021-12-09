package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.PriList;
import model.Task;

import java.time.LocalDate;
import java.util.LinkedList;

public class View implements IView{

    private Stage primaryStage;
    private Button saveNewListButton;
    private Button saveNewTaskButton;
    private Button saveEditTaskButton;
    private Button createTask;
    private Button createList;
    private Button editTask;
    private Button deleteList;
    private Button deleteTask;
    private Button yesTaskDeleteButton;
    private Button yesListDeleteButton;
    private Button noButton;
    private ListView<String> taskList;
    private ListView<String> listList;
    private VBox taskWindow;
    private Text taskHeader;
    private Text taskDesc;
    private ScrollPane taskDescScroll;
    private VBox taskDescWindow;
    private Button backButton;

    // --- Variables for List/Task creation used by the controller
    private String currentlyAddingListName;
    private String currentlyAddingTaskName;
    private String currentlyAddingTaskDesc;
    private int currentlyAddingTaskPriority;
    private LocalDate currentlyAddingTaskDeadline;

    public View(Stage stage, Button saveNewListButton, Button saveNewTaskButton, Button saveEditTaskButton,
                Button createTask, Button createList, Button editTask, Button deleteList, Button deleteTask,
                Button yesListDeleteButton, Button yesTaskDeleteButton, Button noButton,
                ListView<String> listList, ListView<String> taskList, VBox taskWindow, Text taskHeader,
                Text taskDesc, ScrollPane taskDescScroll,
                VBox taskDescWindow, Button backButton) {
        this.primaryStage = stage;
        this.saveNewListButton = saveNewListButton;
        this.saveNewTaskButton = saveNewTaskButton;
        this.saveEditTaskButton = saveEditTaskButton;
        this.createTask = createTask;
        this.createList = createList;
        this.editTask = editTask;
        this.deleteList = deleteList;
        this.deleteTask = deleteTask;
        this.yesListDeleteButton = yesListDeleteButton;
        this.yesTaskDeleteButton = yesTaskDeleteButton;
        this.noButton = noButton;
        this.taskList = taskList;
        this.taskWindow = taskWindow;
        this.listList = listList;
        this.taskHeader = taskHeader;
        this.taskDesc = taskDesc;
        this.taskDescScroll = taskDescScroll;
        this.taskDescWindow = taskDescWindow;
        this.backButton = backButton;
    }

    //set the scene to main menu
    public void gotoMainMenu(LinkedList<PriList> listOfLists) {
        primaryStage.setTitle("PriTask");
        primaryStage.setScene(MainMenuScene(listOfLists));
        primaryStage.show();
    }

    //set the scene to Add list menu
    public void gotoAddList() {
        primaryStage.setScene(addListScene());
        primaryStage.setTitle("Create List");
        primaryStage.show();
    }

    //set the scene to Add task menu
    public void gotoAddTask() {
        primaryStage.setScene(getEditOrAddTaskScene(saveNewTaskButton));
        primaryStage.setTitle("Create Task");
        primaryStage.show();
    }

    //set the scene to Edit task menu
    public void gotoEditTask() {
        primaryStage.setScene(getEditOrAddTaskScene(saveEditTaskButton));
        primaryStage.setTitle("Edit Task");
        primaryStage.show();
    }

    public void gotoDeleteList(PriList ListToDelete) {
        primaryStage.setScene(deleteListScene(ListToDelete));
        primaryStage.setTitle("Delete List");
        primaryStage.show();
    }

    public void gotoDeleteTask(Task TaskToDelete) {
        primaryStage.setScene(deleteTaskScene(TaskToDelete));
        primaryStage.setTitle("Delete Task");
        primaryStage.show();
    }

    private Scene addListScene() {
        Label user_id = new Label("List Name");
        TextField listNameTextField = new TextField();
        listNameTextField.setPrefWidth(200);

        saveNewListButton.setLayoutX(150);
        saveNewListButton.setLayoutY(220);
        backButton.setLayoutX(300);
        backButton.setLayoutY(220);
        GridPane root = new GridPane();
        root.addRow(0, user_id, listNameTextField);
        root.addRow(2, saveNewListButton, backButton);
        Scene scene = new Scene(root, 850, 400);
        scene.getStylesheets().add("view/PriTaskStyle.css");
        listNameTextField.textProperty().addListener(((observable, oldValue, newValue)->
                currentlyAddingListName = newValue));

        return scene;
    }

    private Scene MainMenuScene(LinkedList<PriList> listOfLists) {
        //Part of the window including list of Lists

        //instantiate the UI objects
        VBox listWindow = new VBox();
        listWindow.getStyleClass().add("vbox-style");
        listWindow.setMaxWidth(200);
        listWindow.setMinWidth(200);

        Text listHeader = new Text("Lists");
        listList.getItems().clear();
        for (PriList listOfList : listOfLists) {
            String listName = listOfList.getTitle();
            listList.getItems().add(listName);
        }
        listList.setMaxWidth(200);
        listList.setMinWidth(200);

        createList.setMinWidth(200);
        createList.setMaxWidth(200);


        deleteList.setMinWidth(200);
        deleteList.setMaxWidth(200);

        listWindow.getChildren().addAll(listHeader, listList, createList, deleteList);

        //part of the window including list of Tasks

        //instantiate the UI objects
        taskWindow.getStyleClass().add("vbox-style");
        taskWindow.setMaxWidth(200);
        taskWindow.setMinWidth(200);

        taskList.setMaxWidth(200);
        taskList.setMinWidth(200);

        createTask.setMaxWidth(200);
        createTask.setMinWidth(200);
        taskWindow.getChildren().clear();
        taskWindow.getChildren().addAll(taskHeader, taskList);

        //Task description window
        taskDescWindow.getStyleClass().add("vbox-style");
        taskDescWindow.setMaxWidth(400);
        taskDescWindow.setMinWidth(400);

        taskDescScroll.setMaxWidth(400);
        taskDescScroll.setContent(taskDesc);

        editTask.setMinWidth(200);
        editTask.setMaxWidth(200);

        deleteTask.setMinWidth(200);
        deleteTask.setMaxWidth(200);

        taskDescWindow.getChildren().clear();




        //entire window
        HBox window = new HBox();
        window.getChildren().addAll(listWindow, taskWindow, taskDescWindow);

        //set up of scene
        Scene scene = new Scene(window, 850, 510);
        scene.getStylesheets().add("view/PriTaskStyle.css");
        return scene;
    }

    private Scene getEditOrAddTaskScene(Button saveOrEditButton) {
        Label nameLabel = new Label("Task Name");
        TextField nameField= new TextField();
        nameField.setPrefWidth(200);
        Label descLabel = new Label("Task Description");
        TextField descField= new TextField();
        Label deadline = new Label("Deadline");
        DatePicker deadlineDatePicker = new DatePicker();
        Label priority = new Label("priority");
        Slider prioritySlider = new Slider(0, 10, 1);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(saveOrEditButton, backButton);
        VBox root = new VBox();
        root.getChildren().addAll(nameLabel, nameField, descLabel, descField, priority, prioritySlider, deadline, deadlineDatePicker, buttons);

        Scene scene = new Scene(root, 850, 400);
        scene.getStylesheets().add("view/PriTaskStyle.css");

        nameField.textProperty().addListener(((observable, oldValue, newValue)-> currentlyAddingTaskName = newValue));
        descField.textProperty().addListener(((observable, oldValue, newValue)-> currentlyAddingTaskDesc = newValue));
        prioritySlider.valueProperty().addListener(((observable, oldValue, newValue)->
                currentlyAddingTaskPriority = (int) Math.floor((Double) newValue)));
        deadlineDatePicker.setOnAction(e -> currentlyAddingTaskDeadline = deadlineDatePicker.getValue());

        return scene;
    }

    private Scene deleteListScene(PriList deletedList) {
        Text deleteListText = new Text("Are you sure you want to delete the list \"" + deletedList.getTitle() + "\"?");
        return getDeleteListOrTaskScene(yesListDeleteButton, deleteListText);
    }

    private Scene getDeleteListOrTaskScene(Button yesDeleteListOrTask, Text deleteListOrTaskText) {
        HBox answerBox = new HBox();
        answerBox.getChildren().addAll(yesDeleteListOrTask, noButton);
        answerBox.setPadding(new Insets(10,10,10,100));
        answerBox.setSpacing(230);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(deleteListOrTaskText, answerBox);
        Scene scene = new Scene(root, 600, 200) ;
        scene.getStylesheets().add("view/PriTaskStyle.css");
        return scene;
    }
    public Scene deleteTaskScene(Task deletedTask) {
        Text deleteTaskText = new Text("Are you sure you want to delete the task \"" + deletedTask.getTitle() + "\"?");
        return getDeleteListOrTaskScene(yesTaskDeleteButton, deleteTaskText);
    }

    // --- Getters for creation variables used by controller ---.
    public String getCurrentlyAddingListName(){
        String tmp = currentlyAddingListName;
        currentlyAddingListName = null;
        return tmp;
    }

    public String getCurrentlyAddingTaskName() {
        if (currentlyAddingTaskName == null)
            return "Task name not set...";
        String tmp = currentlyAddingTaskName;
        currentlyAddingTaskName = null;
        return tmp;
    }

    public String getCurrentlyAddingTaskDesc() {
        if (currentlyAddingTaskDesc == null)
            return "No Description set";
        String tmp = currentlyAddingTaskDesc;
        currentlyAddingTaskDesc = null;
        return tmp;
    }

    public int getCurrentlyAddingTaskPriority() {
        return currentlyAddingTaskPriority;
    }

    public LocalDate getCurrentlyAddingTaskDeadline() {
        return currentlyAddingTaskDeadline;
    }
}
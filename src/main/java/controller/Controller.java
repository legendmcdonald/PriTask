package controller;

import java.time.LocalDate;
import java.util.LinkedList;

import javafx.application.Application;

import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.PriList;
import model.PriTaskManager;
import model.Task;
import view.IView;
import view.View;

public class Controller extends Application {

	private Button createTask;
	private Button createList;
	private Button editTask;
	private Button saveNewListButton;
	private Button saveNewTaskButton;
	private Button saveEditTaskButton;
	private Button deleteList;
	private Button deleteTask;
	private Button yesTaskDeleteButton;
	private Button yesListDeleteButton;
	private Button noButton;
	private IView view;
	private LinkedList<PriList> listOfLists;
	private PriList currentList;
	private Task currentTask;
	private ListView<String> listList;
	private ListView<String> taskList;
	private VBox taskWindow;
	private Text taskHeader;
	private Text taskDescHeader;
	private Text taskDeadline;
	private Text taskDesc;
	private ScrollPane taskDescScroll;
	private VBox taskDescWindow;
	private Button backButton;

	// Model Facade Object (PriTaskManager)priTask
	private PriTaskManager priTaskManager;

	//start is needed for the Application Interface
	public void start(Stage stage) {
		priTaskManager = new PriTaskManager();

		initializeButtons();
		setButtonFunctionality();
		view = new View(stage, saveNewListButton, saveNewTaskButton, saveEditTaskButton, createTask, createList,
				editTask, deleteList, deleteTask, yesListDeleteButton, yesTaskDeleteButton, noButton,
				listList, taskList, taskWindow, taskDeadline,
				taskDesc, taskDescScroll, taskDescWindow, backButton);
		listOfLists = new LinkedList<>();

		priTaskManager.load();
		listOfLists = priTaskManager.getLists();
		view.gotoMainMenu(listOfLists);
	}


	public static void uiMain(String[] args) {
		launch(args);
	}



	private void initializeButtons() {
		createTask = new Button("Create Task");
		createList = new Button("Create List");
		editTask = new Button("Edit Task");
		saveNewListButton = new Button("Save");
		saveNewTaskButton = new Button("Save");
		saveEditTaskButton = new Button("Save");
		deleteList = new Button("Delete List");
		deleteTask = new Button("Delete Task");
		yesTaskDeleteButton = new Button("Yes!");
		yesListDeleteButton = new Button("Yes!");
		noButton = new Button("No!");
		listList = new ListView<>();
		taskList = new ListView<>();
		taskWindow = new VBox();
		taskHeader = new Text("Tasks");
		taskDescHeader = new Text("Task description");
		taskDeadline = new Text();
		taskDesc = new Text();
		taskDescScroll = new ScrollPane();
		taskDescWindow = new VBox();
		backButton = new Button("Back");
	}

	private void setButtonFunctionality() {

		saveNewListButton.setOnAction(a -> {
			String listName = view.getCurrentlyAddingListName();
			if (listName!= null && !listName.equals("")) {
				priTaskManager.addNewPriorityList(listName);
				listOfLists = priTaskManager.getLists();
				view.gotoMainMenu(listOfLists);
			}
		});

		saveNewTaskButton.setOnAction(a -> {
			String taskName = view.getCurrentlyAddingTaskName();
			String taskDesc = view.getCurrentlyAddingTaskDesc();
			LocalDate taskDate = view.getCurrentlyAddingTaskDeadline();

			priTaskManager.createTask(currentList, taskName,
					taskDesc , view.getCurrentlyAddingTaskPriority(), taskDate);
			view.gotoMainMenu(listOfLists);
		});

		saveEditTaskButton.setOnAction(a -> {
			String taskName = view.getCurrentlyAddingTaskName();
			String taskDesc = view.getCurrentlyAddingTaskDesc();
			LocalDate taskDate = view.getCurrentlyAddingTaskDeadline();


			priTaskManager.editTask(currentList, currentTask,taskName,
					taskDesc, view.getCurrentlyAddingTaskPriority(), taskDate);
			listOfLists = priTaskManager.getLists();
			view.gotoMainMenu(listOfLists);
		});

		createList.setOnAction(a -> view.gotoAddList());

		deleteList.setOnAction(a -> view.gotoDeleteList(currentList));

		createTask.setOnAction(a -> view.gotoAddTask());

		editTask.setOnAction(a -> view.gotoEditTask());

		deleteTask.setOnAction(a -> view.gotoDeleteTask(currentTask));

		yesListDeleteButton.setOnAction(a -> {
			priTaskManager.removePriorityList(currentList);
			listOfLists = priTaskManager.getLists();
			view.gotoMainMenu(listOfLists);
		});

		yesTaskDeleteButton.setOnAction(a -> {
			priTaskManager.deleteTask(currentList, currentTask);
			listOfLists = priTaskManager.getLists();
			view.gotoMainMenu(listOfLists);
		});

		noButton.setOnAction(a -> {
			listOfLists = priTaskManager.getLists();
			view.gotoMainMenu(listOfLists);
		});

		backButton.setOnAction(a -> {
			listOfLists = priTaskManager.getLists();
			view.gotoMainMenu(listOfLists);
		});

		// Display a specific List when it is selected by the user
		listList.getSelectionModel().selectedIndexProperty().addListener(ov -> {
			int index = listList.getSelectionModel().getSelectedIndex();
			if(index == -1) {
				index = 0;
			}
			currentList = listOfLists.get(index);
			taskList.getItems().clear();
			LinkedList<Task> tasks = currentList.getTasks();
			for (Task task : tasks) {
				taskList.getItems().add(task.getTitle());
			}
			taskList.getSelectionModel().select(0);

			taskWindow.getChildren().clear();
			taskWindow.getChildren().addAll(taskHeader, taskList, createTask);
		});


		//display a specific task when it is selected by the user
		taskList.getSelectionModel().selectedIndexProperty().addListener(ov -> {
			int taskIndex = taskList.getSelectionModel().getSelectedIndex();
			if (taskIndex == -1) {
				taskIndex = 0;
			}
			if (currentList.getSize() != 0) {
				LinkedList<Task> currentListTasks = currentList.getTasks();
				currentTask = currentListTasks.get(taskIndex);
			}
			taskDescHeader.setText(currentTask.getTitle());
			LocalDate deadline = currentTask.getDeadLine();
			String date = "";

			if (deadline != null){
				date = date + (deadline.getYear()) + "/" + (deadline.getMonth() ) + "/" +deadline.getDayOfMonth()+ "/";
			}else{
				date = "Date not set";
			}

			taskDeadline.setText(date);
			taskDesc.setText(currentTask.getStatus());
			taskDescScroll.setContent(taskDesc);

			taskDescWindow.getChildren().clear();
			taskDescWindow.getChildren().addAll(taskDescHeader, taskDeadline, taskDescScroll, editTask, deleteTask);
		});
	}
}
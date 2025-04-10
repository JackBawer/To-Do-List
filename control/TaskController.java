package control;

import model.Priority;
import model.Status;
import model.Task;
import service.TaskService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TaskController {
    private TaskService taskService;

    public TaskController() {
        this.taskService = new TaskService();
    }

    public void addTask(String title, String description, LocalDateTime deadline, String priority, int userId) {
        Task task = new Task(0, title, description, deadline, Priority.valueOf(priority.toUpperCase()), Status.TODO, userId, LocalDateTime.now(), null, false);
        try {
            taskService.saveTask(task);
            System.out.println("Task added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskStatus(int taskId, String status) {
        try {
            taskService.updateTaskStatus(taskId, status);
            System.out.println("Task status updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        try {
            Task task = taskService.getTask(taskId);
            if (task != null) {
                taskService.deleteTask(task);
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            tasks.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
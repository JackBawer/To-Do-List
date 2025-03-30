package model;

import service.TaskService;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();

        try {
            // Add a new task
            Task newTask = new Task(0, "Test Task", "This is a test task", LocalDateTime.now().plusDays(1), Priority.HIGH, Status.TODO, 1, LocalDateTime.now(), null, false);
            taskService.saveTask(newTask);
            System.out.println("Task added successfully.");

            // List all tasks
            System.out.println("All tasks:");
            taskService.getAllTasks().forEach(System.out::println);

            // Update task status
            taskService.updateTaskStatus(newTask.getId(), "IN_PROGRESS");
            System.out.println("Task status updated successfully.");

            // List all tasks
            System.out.println("All tasks after status update:");
            taskService.getAllTasks().forEach(System.out::println);

            // Delete a task
            taskService.deleteTask(newTask);
            System.out.println("Task deleted successfully.");

            // List all tasks
            System.out.println("All tasks after deletion:");
            taskService.getAllTasks().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
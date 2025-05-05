package service;

import model.Status;
import model.Task;
import model.TaskDAO;
import model.TaskDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAOImpl();
    }

    public Task getTask(int id) throws SQLException {
        return taskDAO.get(id);
    }

    public List<Task> getAllTasks() throws SQLException {
        return taskDAO.getAll();
    }

    public int saveTask(Task task) throws SQLException {
        return taskDAO.save(task);
    }

    public int deleteTask(Task task) throws SQLException {
        return taskDAO.delete(task);
    }

    public int updateTaskStatus(int taskId, String status) throws SQLException {
        Task task = taskDAO.get(taskId);
        if (task != null) {
            task.setStatus(Status.valueOf(status.toUpperCase()));
            return taskDAO.update(task);
        }
        return 0;
    }
    
     public List<Task> getTasksByStatus(String status) throws SQLException {
        return taskDAO.getTasksByStatus(status);
    }

    public List<Task> getTasksSorted(String sortBy, String order) throws SQLException {
        return taskDAO.getTasksSorted(sortBy, order);
    }
}

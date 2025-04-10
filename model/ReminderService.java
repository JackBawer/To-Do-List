package model;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ReminderService {
    private final DataSource dataSource;
    private final ScheduledExecutorService scheduler;

    public ReminderService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkAndNotify();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 0, 15, TimeUnit.MINUTES);
    }

    public void stop() {
        scheduler.shutdown();
    }

    private void checkAndNotify() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            List<Task> tasks = getUndoneTasks(connection);

            if (!tasks.isEmpty()) {
                sendNotif(tasks);
            }
        }
        catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private List<Task> getUndoneTasks(Connection connection) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE status <> 'COMPLETED'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.currentTimeMillis();
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();

            while (resultSet.next()) {
                Task task = new Task(resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getTimestamp("deadline").toLocalDateTime(),
                                (Priority) resultSet.getObject("Status"),
                                (Status) resultSet.getObject("Priority"),
                                resultSet.getInt("user_id"),
                                resultSet.getTimestamp("date_issued").toLocalDateTime(),
                                resultSet.getTimestamp("completed_at").toLocalDateTime(),
                                resultSet.getBoolean("is_deleted"));
                tasks.add(task);
            }
        return tasks;
        }
    }
    private void sendNotif(List<Task> tasks) {
        System.out.println("Sending reminders for " + tasks.size() + " tasks:");
        tasks.forEach(task -> {
            System.out.printf("- [%s] %s (Due: %s)%n");
            task.getStatus();
            task.getPriority();
            task.getDeadline();
        });
    }
}
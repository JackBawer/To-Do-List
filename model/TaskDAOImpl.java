package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    @Override
    public Task get(int id) throws SQLException {
        String sql = "SELECT id, title, description, deadline, priority, status, date_issued, completed_at, user_id, is_deleted FROM tasks WHERE id = ? AND is_deleted = FALSE";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int retrievedId = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    LocalDateTime deadline = rs.getTimestamp("deadline").toLocalDateTime();
                    String priority = rs.getString("priority").toUpperCase();
                    String status = rs.getString("status").toUpperCase();
                    LocalDateTime dateIssued = rs.getTimestamp("date_issued").toLocalDateTime();
                    Timestamp completedAtTs = rs.getTimestamp("completed_at");
                    LocalDateTime completedAt = (completedAtTs != null) ? completedAtTs.toLocalDateTime() : null;
                    int userId = rs.getInt("user_id");
                    boolean isDeleted = rs.getBoolean("is_deleted");

                    Priority priorityEnum = Priority.valueOf(priority);
                    Status statusEnum = Status.valueOf(status);

                    return new Task(retrievedId, title, description, deadline, priorityEnum, statusEnum, userId, dateIssued, completedAt, isDeleted);
                }
                return null;
            }
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        String sql = "SELECT id, title, description, deadline, priority, status, date_issued, completed_at, user_id, is_deleted FROM tasks WHERE is_deleted = FALSE";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Task> tasks = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime deadline = rs.getTimestamp("deadline").toLocalDateTime();
                String priority = rs.getString("priority").toUpperCase();
                String status = rs.getString("status").toUpperCase();
                LocalDateTime dateIssued = rs.getTimestamp("date_issued").toLocalDateTime();
                Timestamp completedAtTs = rs.getTimestamp("completed_at");
                LocalDateTime completedAt = (completedAtTs != null) ? completedAtTs.toLocalDateTime() : null;
                int userId = rs.getInt("user_id");
                boolean isDeleted = rs.getBoolean("is_deleted");

                Priority priorityEnum = Priority.valueOf(priority);
                Status statusEnum = Status.valueOf(status);

                Task task = new Task(id, title, description, deadline, priorityEnum, statusEnum, userId, dateIssued, completedAt, isDeleted);
                tasks.add(task);
            }
            return tasks;
        }
    }

    @Override
    public int save(Task task) throws SQLException {
        if (task.getId() > 0) {
            // Update existing task
            return update(task);
        } else {
            // Insert new task
            return insert(task);
        }
    }

    @Override
    public int insert(Task task) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO tasks (title, description, deadline, priority, status, user_id) VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(task.getDeadline()));
            ps.setString(4, task.getPriority().toString());
            ps.setString(5, task.getStatus().toString());
            ps.setInt(6, task.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int update(Task task) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE tasks SET title = ?, description = ?, deadline = ?, priority = ?, status = ?, user_id = ? WHERE id = ?")) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(task.getDeadline()));
            ps.setString(4, task.getPriority().toString());
            ps.setString(5, task.getStatus().toString());
            ps.setInt(6, task.getUserId());
            ps.setInt(7, task.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(Task task) {
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE tasks SET is_deleted = TRUE WHERE id = ?")) {

            ps.setInt(1, task.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Filtrer les tâches par statut
    public List<Task> getTasksByStatus(String status) throws SQLException {
        String sql = "SELECT id, title, description, deadline, priority, status FROM tasks WHERE status = '" + status + "'";
        List<Task> tasks = new ArrayList<>();

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("deadline").toLocalDateTime(),
                    Priority.valueOf(rs.getString("priority").toUpperCase()),
                    Status.valueOf(rs.getString("status").toUpperCase())
                );
                tasks.add(task);
            }
        }

        return tasks;
    }

    // Trier les tâches
    public List<Task> getTasksSorted(String sortBy, String order) throws SQLException {
        String column;
        switch (sortBy) {
            case "priority": column = "priority"; break;
            case "category": column = "c.name"; break;
            case "date":
            default: column = "deadline"; break;
        }

        String sortOrder = (order != null && order.equalsIgnoreCase("desc")) ? "DESC" : "ASC";

        String sql = sortBy.equals("category")
            ? "SELECT t.id, t.title, t.description, t.deadline, t.priority, t.status " +
              "FROM tasks t " +
              "LEFT JOIN task_categories tc ON t.id = tc.task_id " +
              "LEFT JOIN categories c ON tc.category_id = c.id " +
              "ORDER BY " + column + " " + sortOrder
            : "SELECT id, title, description, deadline, priority, status FROM tasks ORDER BY " + column + " " + sortOrder;

        List<Task> tasks = new ArrayList<>();

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("deadline").toLocalDateTime(),
                    Priority.valueOf(rs.getString("priority").toUpperCase()),
                    Status.valueOf(rs.getString("status").toUpperCase())
                );
                tasks.add(task);
            }
        }

        return tasks;
    }
}

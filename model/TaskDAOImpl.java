package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    // CRUD - Retrieve

    @Override
    public Task get(int id) throws SQLException {
        String sql = "SELECT id, title, description, deadline, priority, status FROM tasks WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                Task task = null;
                if (rs.next()) {
                    int retrievedId = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    LocalDateTime deadline = rs.getTimestamp("deadline").toLocalDateTime();
                    String priority = rs.getString("priority").toUpperCase();
                    String status = rs.getString("status").toUpperCase();

                    // Convert String to Enum
                    Priority priorityEnum = Priority.valueOf(priority);
                    Status statusEnum = Status.valueOf(status);

                    task = new Task(retrievedId, title, description, deadline, priorityEnum, statusEnum);
                }
                return task;
            }
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public int save(Task task) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Task task) throws SQLException {
        return 0;
    }

    @Override
    public int update(Task task) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Task task) {
        return 0;
    }
}

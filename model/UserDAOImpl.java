package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User get(int id) throws SQLException {
        String sql = "SELECT user_id, username FROM users WHERE user_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    return new User(userId, username);
                }
                return null;
            }
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT user_id, username FROM users";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                users.add(new User(userId, username));
            }
            return users;
        }
    }

    @Override
    public int save(User user) throws SQLException {
        if (user.getId() > 0) {
            // Update existing user
            return update(user);
        } else {
            // Insert new user
            return insert(user);
        }
    }

    @Override
    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username) VALUES (?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());

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
    public int update(User user) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE user_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(User user) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
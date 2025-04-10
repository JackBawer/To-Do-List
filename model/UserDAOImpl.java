package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    return new User(rs.getInt("user_id"), rs.getString("username"));
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT user_id, username FROM users";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getInt("user_id"), rs.getString("username")));
            }
            return users;
        }
    }

    @Override
    public int save(User user) throws SQLException {
        if (user.getId() > 0) {
            return update(user);
        } else {
            if (usernameExists(user.getUsername())) {
                throw new SQLException("Username already exists.");
            }
            return insert(user);
        }
    }

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
            throw new RuntimeException("Error while deleting the user", e);
        }
    }

    private boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username FROM users WHERE username = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("user_id"), rs.getString("username"));
                }
            }
        }
        return null;
    }
}
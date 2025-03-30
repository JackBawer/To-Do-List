package control;

import model.User;
import service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void addUser(String username) {
        try {
            User existingUser = userService.getUserByUsername(username);
            if (existingUser != null) {
                System.out.println("Username already exists.");
                return;
            }
            User user = new User(0, username);
            userService.saveUser(user);
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUser(int userId, String newUsername) {
        try {
            User existingUser = userService.getUserByUsername(newUsername);
            if (existingUser != null) {
                System.out.println("Username already exists.");
                return;
            }
            User user = userService.getUser(userId);
            if (user != null) {
                user.setUsername(newUsername);
                userService.saveUser(user);
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            User user = userService.getUser(userId);
            if (user != null) {
                userService.deleteUser(user);
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            users.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User getUser(int userId) {
        try {
            return userService.getUser(userId);
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
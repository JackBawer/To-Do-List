package model;

import control.UserController;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();

        // Add a new user
        userController.addUser("newuser");

        // List all users
        System.out.println("All users:");
        userController.listAllUsers();

        // Update user
        userController.updateUser(1, "updateduser");

        // Get user by ID
        System.out.println("User with ID 1:");
        System.out.println(userController.getUser(1));

        // Delete user
        userController.deleteUser(1);

        // List all users again to confirm deletion
        System.out.println("All users after deletion:");
        userController.listAllUsers();
    }
}
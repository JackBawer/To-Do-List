package model;

import control.UserController;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        DataSource ds = createDB();

        ReminderService rs = new ReminderService(ds);
        rs.start();

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

        rs.stop();
    }

    public static DataSource createDB() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("12345");
        return dataSource;
    }
}
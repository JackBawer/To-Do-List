package model;

import model.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Connection con = Database.getConnection();

        // if (con != null) {
        //     System.out.println("Database connection successful");
        // }

         TaskDAO taskDAO = new TaskDAOImpl();

         Task task = taskDAO.get(3);

         System.out.println(task);
    }
}

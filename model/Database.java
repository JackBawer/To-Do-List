package model;

import javax.sql.DataSource;
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
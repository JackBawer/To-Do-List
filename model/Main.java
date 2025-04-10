package model;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) throws SQLException {
         //Connection con = Database.getConnection();

         TaskDAO taskDAO = new TaskDAOImpl();

         Task task = taskDAO.get(3);

         DataSource ds = createDB();

         ReminderService rs = new ReminderService(ds);

         rs.start();

         System.out.println(task);

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


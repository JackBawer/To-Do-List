package model;

import java.sql.SQLException;

public interface UserDAO extends DAO<User> {
    User getUserByUsername(String username) throws SQLException;
}

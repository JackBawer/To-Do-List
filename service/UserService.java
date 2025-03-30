package service;

import model.User;
import model.UserDAO;
import model.UserDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public int saveUser(User user) throws SQLException {
        return userDAO.save(user);
    }

    public User getUser(int id) throws SQLException {
        return userDAO.get(id);
    }

    public void deleteUser(User user) throws SQLException {
        userDAO.delete(user);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }
}
package service;

import model.RewardDAO;
import model.RewardDAOImpl;
import util.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class RewardService {
    private RewardDAO rewardDAO;

    public RewardService() {
        try {
            Connection connection = Database.getConnection();
            this.rewardDAO = new RewardDAOImpl(connection);
        } catch (SQLException e) {
            System.err.println("Failed to initialize RewardDAO.");
        }
    }

    public void rewarding(int userId) {
        try {
            rewardDAO.rewarding(userId);
        } catch (SQLException e) {
            System.err.println("Error assigning reward: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RewardDAOImpl implements RewardDAO {

    private final Connection connection;

    public RewardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reward get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reward> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public int save(Reward reward) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Reward reward) throws SQLException {
        return 0;
    }

    @Override
    public int update(Reward reward) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Reward reward) {
        return 0;
    }

    public void rewarding(int userId) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM tasks WHERE user_id = " + userId + " AND status = 'done'";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(countSql);

        int completedTasks = 0;
        if (rs.next()) {
            completedTasks = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        int[] thresholds = {1, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        for (int threshold : thresholds) {
            if (completedTasks >= threshold) {
                int rewardId = threshold;

                String checkSql = "SELECT 1 FROM user_rewards WHERE user_id = " + userId + " AND reward_id = " + rewardId;
                Statement checkStmt = connection.createStatement();
                ResultSet checkRs = checkStmt.executeQuery(checkSql);

                boolean alreadyReceived = checkRs.next();
                checkRs.close();
                checkStmt.close();

                if (!alreadyReceived) {
                    String insertSql = "INSERT INTO user_rewards (user_id, reward_id, achieved_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
                    PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, rewardId);
                    insertStmt.executeUpdate();
                    insertStmt.close();

                    System.out.println("\uD83C\uDF89 Badge attribu\u00e9 pour " + threshold + " t\u00e2ches termin\u00e9es !");
                }
            }
        }
    }
} 



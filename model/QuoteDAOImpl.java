package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuoteDAOImpl implements QuoteDAO {

    private final Connection connection;

    public QuoteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Quote get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Quote> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public int save(Quote quote) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Quote quote) throws SQLException {
        return 0;
    }

    @Override
    public int update(Quote quote) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Quote quote) {
        return 0;
    }

    public Quote getRandomQuote() throws SQLException {
        String sql = "SELECT id, text, author FROM quotes ORDER BY RANDOM() LIMIT 1";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        Quote quote = null;
        if (rs.next()) {
            quote = new Quote(
                rs.getInt("id"),
                rs.getString("text"),
                rs.getString("author")
            );
        }

        rs.close();
        stmt.close();

        return quote;
    }
} 

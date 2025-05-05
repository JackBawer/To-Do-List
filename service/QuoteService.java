package service;

import model.Quote;
import model.QuoteDAO;
import model.QuoteDAOImpl;
import util.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuoteService {
    private QuoteDAO quoteDAO;

    public QuoteService() {
        try {
            Connection connection = Database.getConnection();
            this.quoteDAO = new QuoteDAOImpl(connection);
        } catch (SQLException e) {
            System.err.println("Failed to initialize QuoteDAO.");
        }
    }

    public Quote getQuote(int id) throws SQLException {
        return quoteDAO.get(id);
    }

    public List<Quote> getAllQuotes() throws SQLException {
        return quoteDAO.getAll();
    }

    public int saveQuote(Quote quote) throws SQLException {
        return quoteDAO.save(quote);
    }

    public int deleteQuote(Quote quote) throws SQLException {
        return quoteDAO.delete(quote);
    }

    public Quote getRandomQuote() throws SQLException {
        return quoteDAO.getRandomQuote();
    }
}

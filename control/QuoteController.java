package control;

import model.Quote;
import service.QuoteService;

import java.sql.SQLException;

public class QuoteController {
    private QuoteService quoteService;

    public QuoteController() {
        this.quoteService = new QuoteService();
    }

    public void displayRandomQuote() {
        try {
            Quote quote = quoteService.getRandomQuote();
            if (quote != null) {
                System.out.println("\"" + quote.getText() + "\" — " + quote.getAuthor());
            } else {
                System.out.println("No motivational quote available.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving quote: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

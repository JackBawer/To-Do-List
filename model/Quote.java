package model;

public class Quote {
    private int id;
    private String text;
    private String author;

    public Quote(int id, String text, String author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public int getId() { 
      return id; }
  
    public String getText() { 
      return text; }
  
    public String getAuthor() { 
      return author; }

    @Override
    public String toString() {
        return "\"" + text + "\" — " + author;
    }
}

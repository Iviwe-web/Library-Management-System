package Library;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true; // Book is available by default
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, ISBN: %s, Available: %s",
                title, author, isbn, isAvailable ? "Yes" : "No");
    }

    public void setisAvailable(boolean isAvailable) {
    }
}

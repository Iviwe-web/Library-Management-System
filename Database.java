package Library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private static final String USERS_FILE = "users.txt";
    private static final String BOOKS_FILE = "books.txt";

    private Database() {
        loadUsers();
        loadBooks();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public int login(String phoneNumber, String email) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getPhoneNumber().equals(phoneNumber) && user.getEmail().equals(email)) {
                return i;
            }
        }
        return -1;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public Book getBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean userExists(String phoneNumber, String email) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber) || user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void removeBook(Book book) {
        books.remove(book);
        saveBooks();
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.getClass().getSimpleName() + "|" + user.getName() + "|" + user.getEmail() + "|" + user.getPhoneNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String userType = parts[0];
                String name = parts[1];
                String email = parts[2];
                String phoneNumber = parts[3];

                User user;
                if (userType.equals("NormalUser")) {
                    user = new NormalUser(name, email, phoneNumber);
                } else {
                    // Handle other user types if any
                    continue;
                }
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "|" + book.getAuthor() + "|" + book.getIsbn() + "|" + book.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String title = parts[0];
                String author = parts[1];
                String isbn = parts[2];
                boolean isAvailable = Boolean.parseBoolean(parts[3]);

                Book book = new Book(title, author, isbn);
                book.setisAvailable(isAvailable);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
}

package Library;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class NormalUser extends User {
    private static final long serialVersionUID = 1L;
    private List<Book> borrowedBooks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public NormalUser(String name) {
        super(name);
    }

    public NormalUser(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void menu() {
        int choice;
        do {
            System.out.println("1. View Books");
            System.out.println("2. Search Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Check Book Status");
            System.out.println("6. Add Book");
            System.out.println("7. Delete Book");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    checkBookStatus();
                    break;
                case 6:
                    addBook();
                    break;
                case 7:
                    deleteBook();
                    break;
                case 8:
                    logout();
                    return; // Exit the menu loop
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        } while (choice != 8);
    }

    private void viewBooks() {
        System.out.println("Books in the library:");
        for (Book book : Database.getInstance().getBooks()) {
            System.out.println(book);
        }
    }

    private void searchBooks() {
        System.out.println("Search by:\n1. Title\n2. Author");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<Book> foundBooks = new ArrayList<>();
        for (Book book : Database.getInstance().getBooks()) {
            if ((searchChoice == 1 && book.getTitle().toLowerCase().contains(searchTerm)) ||
                    (searchChoice == 2 && book.getAuthor().toLowerCase().contains(searchTerm))) {
                foundBooks.add(book);
            }
        }

        if (foundBooks.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search results:");
            for (Book book : foundBooks) {
                System.out.println(book);
            }
        }
    }

    private void borrowBook() {
        System.out.print("Enter the title of the book you want to borrow: ");
        String title = scanner.nextLine();
        Book book = Database.getInstance().getBook(title);
        if (book != null) {
            if (book.isAvailable()) {
                book.toggleAvailability();
                borrowedBooks.add(book);
                System.out.println("You have borrowed: " + book.getTitle());
                // Save the updated state of the book
                Database.getInstance().saveBooks();
            } else {
                System.out.println("Sorry, this book is not available.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    private void returnBook() {
        System.out.print("Enter the title of the book you want to return: ");
        String title = scanner.nextLine();
        Book book = Database.getInstance().getBook(title);
        if (book != null) {
            if (borrowedBooks.remove(book)) {
                book.toggleAvailability();
                System.out.println("You have returned: " + book.getTitle());
                // Save the updated state of the book
                Database.getInstance().saveBooks();
            } else {
                System.out.println("This book was not borrowed.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    private void checkBookStatus() {
        System.out.print("Enter the title of the book to check its status: ");
        String title = scanner.nextLine();
        Book book = Database.getInstance().getBook(title);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private void addBook() {
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the ISBN of the book: ");
        String isbn = scanner.nextLine();

        Book newBook = new Book(title, author, isbn);
        Database.getInstance().addBook(newBook);
        System.out.println("Book added: " + newBook);
    }

    private void deleteBook() {
        System.out.print("Enter the title of the book you want to delete: ");
        String title = scanner.nextLine();
        Book book = Database.getInstance().getBook(title);
        if (book != null) {
            Database.getInstance().removeBook(book);
            System.out.println("Book deleted: " + book.getTitle());
        } else {
            System.out.println("Book not found.");
        }
    }
}

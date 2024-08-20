package Library;

import java.util.Scanner;

public class Login {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Database database = Database.getInstance();
        System.out.println("Welcome to Library Management System!");
        int num;
        do {
            System.out.println("0. Exit\n1. Login\n2. New User");
            num = scanner.nextInt();
            switch (num) {
                case 1:
                    login();
                    break;
                case 2:
                    newUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (num != 0);
    }

    private static void login() {
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.next();
        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please try again:");
            phoneNumber = scanner.next();
        }
        System.out.println("Enter email:");
        String email = scanner.next();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email. Please try again:");
            email = scanner.next();
        }
        int index = Database.getInstance().login(phoneNumber, email);
        if (index != -1) {
            User user = Database.getInstance().getUser(index);
            user.menu();
        } else {
            System.out.println("User doesn't exist!");
        }
    }

    private static void newUser() {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.next();
        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please try again:");
            phoneNumber = scanner.next();
        }
        System.out.println("Enter email:");
        String email = scanner.next();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email. Please try again:");
            email = scanner.next();
        }
        if (Database.getInstance().userExists(phoneNumber, email)) {
            System.out.println("User already exists in the system!");
            return;
        }
        System.out.println("1. Normal User");
        int userType = scanner.nextInt();
        User user = new NormalUser(name, email, phoneNumber);
        Database.getInstance().addUser(user);
        user.menu();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }
}





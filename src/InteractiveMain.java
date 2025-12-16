import domain.*;
import system.*;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.*;
import patterns.decorator.*;
import patterns.strategy.*;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;

/**
 * Interactive Main class - Menu-driven Smart Library Management System.
 * 
 * Student ID: k2559671
 * Kingston University - CI6115 PROGRAMMING III
 * 
 * Design Patterns Demonstrated:
 * 1. Observer Pattern - Notification system
 * 2. Strategy Pattern - Fine calculation
 * 3. State Pattern - Book availability states
 * 4. Command Pattern - User actions with undo
 * 5. Decorator Pattern - Book features
 * 6. Builder Pattern - Complex book creation
 */
public class InteractiveMain {
    private static k2559671_LibrarySystem library;
    private static Scanner scanner;
    private static k2559671_BookBuilder bookBuilder;

    public static void main(String[] args) {
        library = new k2559671_LibrarySystem();
        scanner = new Scanner(System.in);
        bookBuilder = new k2559671_BookBuilder();

        printHeader();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    viewAllBooks();
                    break;
                case 4:
                    viewAllUsers();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    reserveBook();
                    break;
                case 8:
                    decorateBook();
                    break;
                case 9:
                    generateReports();
                    break;
                case 10:
                    runDemo();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n✓ Thank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("\n⚠ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printHeader() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   SMART LIBRARY MANAGEMENT SYSTEM - k2559671             ║");
        System.out.println("║   Kingston University - CI6115 PROGRAMMING III           ║");
        System.out.println("║   Interactive Mode                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
    }

    private static void printMainMenu() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("                    MAIN MENU");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  1. Add Book (Builder Pattern)");
        System.out.println("  2. Add User (Strategy Pattern)");
        System.out.println("  3. View All Books");
        System.out.println("  4. View All Users");
        System.out.println("  5. Borrow Book (State & Command Pattern)");
        System.out.println("  6. Return Book (State & Command Pattern)");
        System.out.println("  7. Reserve Book (State Pattern)");
        System.out.println("  8. Decorate Book (Decorator Pattern)");
        System.out.println("  9. Generate Reports");
        System.out.println(" 10. Run Full Demo");
        System.out.println("  0. Exit");
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    private static void addBook() {
        System.out.println("\n--- ADD NEW BOOK (Builder Pattern) ---");

        String bookId = getStringInput("Enter Book ID (e.g., B001): ");
        String title = getStringInput("Enter Title: ");
        String author = getStringInput("Enter Author: ");
        String category = getStringInput("Enter Category: ");
        String isbn = getStringInput("Enter ISBN: ");

        bookBuilder.reset();
        bookBuilder.setBookId(bookId)
                .setTitle(title)
                .setAuthor(author)
                .setCategory(category)
                .setISBN(isbn);

        String addMetadata = getStringInput("Add metadata? (y/n): ");
        if (addMetadata.equalsIgnoreCase("y")) {
            k2559671_BookMetadata metadata = new k2559671_BookMetadata();
            metadata.setPublisher(getStringInput("Publisher: "));
            metadata.setEdition(getStringInput("Edition: "));
            metadata.setPublicationYear(getIntInput("Publication Year: "));

            String addTags = getStringInput("Add tags? (y/n): ");
            if (addTags.equalsIgnoreCase("y")) {
                while (true) {
                    String tag = getStringInput("Enter tag (or 'done' to finish): ");
                    if (tag.equalsIgnoreCase("done"))
                        break;
                    metadata.addTag(tag);
                }
            }

            bookBuilder.setMetadata(metadata);
        }

        k2559671_Book book = bookBuilder.build();
        library.addBook(book);

        System.out.println("\n✓ Book added successfully!");
        System.out.println(book.getDetails());
    }

    private static void addUser() {
        System.out.println("\n--- ADD NEW USER (Strategy Pattern) ---");

        String userId = getStringInput("Enter User ID (e.g., U001): ");
        String name = getStringInput("Enter Name: ");
        String email = getStringInput("Enter Email: ");
        String phone = getStringInput("Enter Phone: ");

        System.out.println("\nMembership Types:");
        System.out.println("1. STUDENT (5 books, LKR 50/day fine)");
        System.out.println("2. FACULTY (10 books, LKR 25/day fine)");
        System.out.println("3. GUEST (2 books, LKR 100/day fine)");

        int typeChoice = getIntInput("Select membership type (1-3): ");
        k2559671_MembershipType membershipType;
        k2559671_FineStrategy fineStrategy;

        switch (typeChoice) {
            case 1:
                membershipType = k2559671_MembershipType.STUDENT;
                fineStrategy = new k2559671_StudentFineStrategy();
                break;
            case 2:
                membershipType = k2559671_MembershipType.FACULTY;
                fineStrategy = new k2559671_FacultyFineStrategy();
                break;
            case 3:
                membershipType = k2559671_MembershipType.GUEST;
                fineStrategy = new k2559671_GuestFineStrategy();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to STUDENT.");
                membershipType = k2559671_MembershipType.STUDENT;
                fineStrategy = new k2559671_StudentFineStrategy();
        }

        k2559671_User user = new k2559671_User(userId, name, email, phone, membershipType);
        user.setFineStrategy(fineStrategy);
        library.addUser(user);

        System.out.println("\n✓ User added successfully!");
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Membership: " + membershipType);
    }

    private static void viewAllBooks() {
        System.out.println("\n--- ALL BOOKS IN LIBRARY ---");

        if (library.getAllBooks().isEmpty()) {
            System.out.println("No books in the library yet.");
            return;
        }

        for (k2559671_Book book : library.getAllBooks()) {
            System.out.println("\n" + book.getDetails());
            System.out.println("---");
        }
    }

    private static void viewAllUsers() {
        System.out.println("\n--- ALL REGISTERED USERS ---");

        if (library.getAllUsers().isEmpty()) {
            System.out.println("No users registered yet.");
            return;
        }

        for (k2559671_User user : library.getAllUsers()) {
            System.out.println("\nUser ID: " + user.getUserId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Membership: " + user.getMembershipType());
            System.out.println("---");
        }
    }

    private static void borrowBook() {
        System.out.println("\n--- BORROW BOOK (State & Command Pattern) ---");

        String userId = getStringInput("Enter User ID: ");
        String bookId = getStringInput("Enter Book ID: ");

        k2559671_User user = library.findUserById(userId);
        k2559671_Book book = library.findBookById(bookId);

        if (user == null) {
            System.out.println("⚠ User not found!");
            return;
        }

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }

        k2559671_Command borrowCmd = new k2559671_BorrowCommand(user, book, library.getLogger());
        library.processCommand(borrowCmd);

        System.out.println("\n✓ Borrow operation completed!");
        System.out.println("Book Status: " + book.getAvailabilityStatus());
    }

    private static void returnBook() {
        System.out.println("\n--- RETURN BOOK (State & Command Pattern) ---");

        String userId = getStringInput("Enter User ID: ");
        String bookId = getStringInput("Enter Book ID: ");

        k2559671_User user = library.findUserById(userId);
        k2559671_Book book = library.findBookById(bookId);

        if (user == null) {
            System.out.println("⚠ User not found!");
            return;
        }

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }

        k2559671_Command returnCmd = new k2559671_ReturnCommand(user, book, library.getLogger());
        library.processCommand(returnCmd);

        System.out.println("\n✓ Return operation completed!");
        System.out.println("Book Status: " + book.getAvailabilityStatus());
    }

    private static void reserveBook() {
        System.out.println("\n--- RESERVE BOOK (State Pattern) ---");

        String bookId = getStringInput("Enter Book ID: ");
        k2559671_Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }

        System.out.println("Current Status: " + book.getAvailabilityStatus());
        book.reserve();
        System.out.println("New Status: " + book.getAvailabilityStatus());
    }

    private static void decorateBook() {
        System.out.println("\n--- DECORATE BOOK (Decorator Pattern) ---");

        String bookId = getStringInput("Enter Book ID: ");
        k2559671_Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }

        System.out.println("\nDecorator Options:");
        System.out.println("1. Featured (Trending Book)");
        System.out.println("2. Recommended (Professor Recommendation)");
        System.out.println("3. Special Edition");

        int choice = getIntInput("Select decorator (1-3): ");

        switch (choice) {
            case 1:
                k2559671_FeaturedDecorator featured = new k2559671_FeaturedDecorator(book);
                System.out.println("\n" + featured.getDescription());
                break;
            case 2:
                int rating = getIntInput("Enter rating (1-10): ");
                String professor = getStringInput("Professor name: ");
                k2559671_RecommendedDecorator recommended = new k2559671_RecommendedDecorator(book, rating, professor);
                System.out.println("\n" + recommended.getDescription());
                break;
            case 3:
                String feature = getStringInput("Special feature: ");
                int edition = getIntInput("Edition number: ");
                k2559671_SpecialEditionDecorator special = new k2559671_SpecialEditionDecorator(book, feature, edition);
                System.out.println("\n" + special.getDescription());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void generateReports() {
        System.out.println("\n--- GENERATE REPORTS ---");

        System.out.println("\n1. Most Borrowed Books");
        System.out.println("2. Active Borrowers");
        System.out.println("3. Revenue Report");
        System.out.println("4. User Report");

        int choice = getIntInput("Select report (1-4): ");

        switch (choice) {
            case 1:
                library.getReportManager().generateMostBorrowedBooks();
                break;
            case 2:
                library.getReportManager().generateActiveBorrowers();
                break;
            case 3:
                library.getReportManager().generateRevenueReport();
                break;
            case 4:
                String userId = getStringInput("Enter User ID: ");
                library.getReportManager().generateUserReport(userId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void runDemo() {
        System.out.println("\n--- RUNNING FULL DEMO ---");
        System.out.println("This will demonstrate all 6 design patterns...\n");

        // Run the original demo from Main.java
        String[] args = {};
        Main.main(args);
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Please enter a valid number.");
            }
        }
    }
}

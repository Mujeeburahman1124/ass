import java.util.List;
import java.util.Scanner;

import domain.k2559671_Book;
import domain.k2559671_BookMetadata;
import domain.k2559671_MembershipType;
import domain.k2559671_User;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.k2559671_BorrowCommand;
import patterns.command.k2559671_Command;
import patterns.command.k2559671_ReturnCommand;
import patterns.decorator.k2559671_FeaturedDecorator;
import patterns.decorator.k2559671_RecommendedDecorator;
import patterns.decorator.k2559671_SpecialEditionDecorator;
import patterns.strategy.k2559671_FacultyFineStrategy;
import patterns.strategy.k2559671_FineStrategy;
import patterns.strategy.k2559671_GuestFineStrategy;
import patterns.strategy.k2559671_StudentFineStrategy;
import system.k2559671_LibrarySystem;

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
    private static k2559671_Command lastCommand;

    public static void main(String[] args) {
        library = new k2559671_LibrarySystem();
        scanner = new Scanner(System.in);
        bookBuilder = new k2559671_BookBuilder();

        printHeader();
        
        // Add sample books for testing
        addSampleBooks();

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
                    calculateFine();
                    break;
                case 10:
                    viewNotifications();
                    break;
                case 11:
                    sendNotification();
                    break;
                case 12:
                    undoLastCommand();
                    break;
                case 13:
                    generateReports();
                    break;
                case 14:
                    runDemo();
                    break;
                case 15:
                    viewAllReservations();
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
        System.out.println("  9. Calculate Fine (Strategy Pattern)");
        System.out.println(" 10. View Notifications (Observer Pattern)");
        System.out.println(" 11. Send Notification (Observer Pattern)");
        System.out.println(" 12. Undo Last Command (Command Pattern)");
        System.out.println(" 13. Generate Reports");
        System.out.println(" 14. Run Full Demo");
        System.out.println(" 15. View All Reservations");
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
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              ALL BOOKS IN LIBRARY                                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");

        if (library.getAllBooks().isEmpty()) {
            System.out.println("⚠ No books in the library yet.");
            return;
        }

        System.out.println("\n┌─────────┬──────────────────────────────┬──────────────────────────┬─────────────────┬────────────────┐");
        System.out.println("│ Book ID │ Title                        │ Author                   │ Category        │ Status         │");
        System.out.println("├─────────┼──────────────────────────────┼──────────────────────────┼─────────────────┼────────────────┤");

        for (k2559671_Book book : library.getAllBooks()) {
            System.out.printf("│ %-7s │ %-28s │ %-24s │ %-15s │ %-14s │%n",
                truncate(book.getBookId(), 7),
                truncate(book.getTitle(), 28),
                truncate(book.getAuthor(), 24),
                truncate(book.getCategory(), 15),
                truncate(book.getAvailabilityStatus(), 14));
        }

        System.out.println("└─────────┴──────────────────────────────┴──────────────────────────┴─────────────────┴────────────────┘");
        System.out.println("\nTotal Books: " + library.getAllBooks().size());
    }

    private static void viewAllUsers() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            ALL REGISTERED USERS                                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");

        if (library.getAllUsers().isEmpty()) {
            System.out.println("⚠ No users registered yet.");
            return;
        }

        System.out.println("\n┌──────────┬─────────────────────────┬─────────────────────────────┬──────────────┬────────────┐");
        System.out.println("│ User ID  │ Name                    │ Email                       │ Membership   │ Borrowed   │");
        System.out.println("├──────────┼─────────────────────────┼─────────────────────────────┼──────────────┼────────────┤");

        for (k2559671_User user : library.getAllUsers()) {
            System.out.printf("│ %-8s │ %-23s │ %-27s │ %-12s │ %d/%-8d │%n",
                truncate(user.getUserId(), 8),
                truncate(user.getName(), 23),
                truncate(user.getEmail(), 27),
                user.getMembershipType(),
                user.getCurrentBorrowedCount(),
                user.getMembershipType().getBorrowingLimit());
        }

        System.out.println("└──────────┴─────────────────────────┴─────────────────────────────┴──────────────┴────────────┘");
        System.out.println("\nTotal Users: " + library.getAllUsers().size());
    }

    private static void borrowBook() {
        System.out.println("\n--- BORROW BOOK (State & Command Pattern) ---");

        String userId = getStringInput("Enter User ID: ");
        String bookId = getStringInput("Enter Book ID: ");

        k2559671_User user = library.findUserById(userId);
        k2559671_Book book = library.findBookById(bookId);

        if (user == null) {
            System.out.println(" User not found!");
            return;
        }

        if (book == null) {
            System.out.println(" Book not found!");
            return;
        }

        k2559671_Command borrowCmd = new k2559671_BorrowCommand(user, book, library.getLogger());
        library.processCommand(borrowCmd);        lastCommand = borrowCmd;
        System.out.println("\n Borrow operation completed!");
        System.out.println("Book Status: " + book.getAvailabilityStatus());
    }

    private static void returnBook() {
        System.out.println("\n--- RETURN BOOK (State & Command Pattern) ---");

        String userId = getStringInput("Enter User ID: ");
        String bookId = getStringInput("Enter Book ID: ");

        k2559671_User user = library.findUserById(userId);
        k2559671_Book book = library.findBookById(bookId);

        if (user == null) {
            System.out.println(" User not found!");
            return;
        }

        if (book == null) {
            System.out.println(" Book not found!");
            return;
        }

        k2559671_Command returnCmd = new k2559671_ReturnCommand(user, book, library.getLogger());
        library.processCommand(returnCmd);        lastCommand = returnCmd;
        System.out.println("\n Return operation completed!");
        System.out.println("Book Status: " + book.getAvailabilityStatus());
    }

    private static void reserveBook() {
        System.out.println("\n--- RESERVE BOOK (State Pattern) ---");

        if (library.getAllUsers().isEmpty()) {
            System.out.println("⚠ No users in the system.");
            return;
        }

        viewAllUsers();
        String userId = getStringInput("\nEnter User ID: ");
        k2559671_User user = library.getUser(userId);

        if (user == null) {
            System.out.println("⚠ User not found!");
            return;
        }

        if (library.getAllBooks().isEmpty()) {
            System.out.println("⚠ No books in the system.");
            return;
        }

        viewAllBooks();
        String bookId = getStringInput("\nEnter Book ID: ");
        k2559671_Book book = library.getBook(bookId);

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }

        System.out.println("\nCurrent Book Status: " + book.getAvailabilityStatus());
        
        // Reserve the book
        book.reserve();
        user.reserveBook(book);
        
        // Create reservation object with auto-generated ID
        String reservationId = "RES" + String.format("%03d", library.getAllReservations().size() + 1);
        domain.k2559671_Reservation reservation = new domain.k2559671_Reservation(reservationId, userId, bookId);
        library.addReservation(reservation);
        
        System.out.println("\n✓ Reservation Created Successfully!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Reservation ID     : " + reservation.getReservationId());
        System.out.println("User               : " + user.getName() + " (" + userId + ")");
        System.out.println("Book               : " + book.getTitle() + " (" + bookId + ")");
        System.out.println("Reservation Date   : " + reservation.getReservationDate());
        System.out.println("Expiry Date        : " + reservation.getExpiryDate() + " (3 days)");
        System.out.println("Status             : " + reservation.getStatus());
        System.out.println("Is Active          : " + reservation.isActive());
        System.out.println("Has Expired        : " + reservation.hasExpired());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("New Book Status    : " + book.getAvailabilityStatus());
        System.out.println("\n📝 Note: Reservation will expire in 3 days if not fulfilled");
    }

    private static void decorateBook() {
        System.out.println("\n--- DECORATE BOOK (Decorator Pattern) ---");

        String bookId = getStringInput("Enter Book ID: ");
        k2559671_Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println(" Book not found!");
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

    private static void addSampleBooks() {
        System.out.println("\n📚 Loading sample books into the library...\n");
        
        // Book 1: Design Patterns in Java
        k2559671_BookMetadata metadata1 = new k2559671_BookMetadata();
        metadata1.setPublisher("O'Reilly Media");
        metadata1.setEdition("3rd Edition");
        metadata1.setPublicationYear(2020);
        metadata1.addTag("Programming");
        metadata1.addTag("Java");
        metadata1.addTag("Design Patterns");
        
        bookBuilder.reset();
        k2559671_Book book1 = bookBuilder
            .setBookId("B001")
            .setTitle("Design Patterns in Java")
            .setAuthor("Joshua Bloch")
            .setCategory("Programming")
            .setISBN("978-0134685991")
            .setMetadata(metadata1)
            .build();
        library.addBook(book1);
        
        // Book 2: Clean Code
        k2559671_BookMetadata metadata2 = new k2559671_BookMetadata();
        metadata2.setPublisher("Prentice Hall");
        metadata2.setEdition("1st Edition");
        metadata2.setPublicationYear(2008);
        metadata2.addTag("Software Engineering");
        metadata2.addTag("Best Practices");
        
        bookBuilder.reset();
        k2559671_Book book2 = bookBuilder
            .setBookId("B002")
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin")
            .setCategory("Programming")
            .setISBN("978-0132350884")
            .setMetadata(metadata2)
            .build();
        library.addBook(book2);
        
        // Book 3: The Pragmatic Programmer
        k2559671_BookMetadata metadata3 = new k2559671_BookMetadata();
        metadata3.setPublisher("Addison-Wesley");
        metadata3.setEdition("2nd Edition");
        metadata3.setPublicationYear(2019);
        metadata3.addTag("Software Development");
        metadata3.addTag("Career");
        
        bookBuilder.reset();
        k2559671_Book book3 = bookBuilder
            .setBookId("B003")
            .setTitle("The Pragmatic Programmer")
            .setAuthor("Andrew Hunt & David Thomas")
            .setCategory("Software Engineering")
            .setISBN("978-0135957059")
            .setMetadata(metadata3)
            .build();
        library.addBook(book3);
        
        // Book 4: Effective Java
        k2559671_BookMetadata metadata4 = new k2559671_BookMetadata();
        metadata4.setPublisher("Addison-Wesley");
        metadata4.setEdition("3rd Edition");
        metadata4.setPublicationYear(2018);
        metadata4.addTag("Java");
        metadata4.addTag("Best Practices");
        
        bookBuilder.reset();
        k2559671_Book book4 = bookBuilder
            .setBookId("B004")
            .setTitle("Effective Java")
            .setAuthor("Joshua Bloch")
            .setCategory("Programming")
            .setISBN("978-0134685991")
            .setMetadata(metadata4)
            .build();
        library.addBook(book4);
        
        // Book 5: Head First Design Patterns
        k2559671_BookMetadata metadata5 = new k2559671_BookMetadata();
        metadata5.setPublisher("O'Reilly Media");
        metadata5.setEdition("2nd Edition");
        metadata5.setPublicationYear(2021);
        metadata5.addTag("Design Patterns");
        metadata5.addTag("OOP");
        
        bookBuilder.reset();
        k2559671_Book book5 = bookBuilder
            .setBookId("B005")
            .setTitle("Head First Design Patterns")
            .setAuthor("Eric Freeman & Elisabeth Robson")
            .setCategory("Programming")
            .setISBN("978-1492078005")
            .setMetadata(metadata5)
            .build();
        library.addBook(book5);
        
        System.out.println("✓ Loaded 5 sample books successfully!\n");
    }

    private static void calculateFine() {
        System.out.println("\n--- CALCULATE FINE (Strategy Pattern) ---");
        
        if (library.getAllUsers().isEmpty()) {
            System.out.println("⚠ No users in the system. Please add a user first.");
            return;
        }
        
        viewAllUsers();
        String userId = getStringInput("\nEnter User ID: ");
        k2559671_User user = library.getUser(userId);
        
        if (user == null) {
            System.out.println("⚠ User not found!");
            return;
        }
        
        int daysOverdue = getIntInput("Enter days overdue: ");
        
        if (daysOverdue < 0) {
            System.out.println("⚠ Days overdue cannot be negative!");
            return;
        }
        
        double fine = user.calculateFine(daysOverdue);
        
        System.out.println("\n--- FINE CALCULATION RESULT ---");
        System.out.println("User: " + user.getName());
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Membership Type: " + user.getMembershipType());
        System.out.println("Days Overdue: " + daysOverdue);
        System.out.println("\n CALCULATED FINE: LKR " + fine);
        
        System.out.println("\n--- Fine Rates by Membership Type ---");
        System.out.println("• STUDENT: LKR 50 per day");
        System.out.println("• FACULTY: LKR 20 per day");
        System.out.println("• GUEST: LKR 100 per day");
        
        System.out.println("\n✓ Strategy Pattern Applied: " + user.getMembershipType() + " Fine Strategy");
    }
    
    private static void viewNotifications() {
        System.out.println("\n--- VIEW NOTIFICATIONS (Observer Pattern) ---");
        
        if (library.getAllUsers().isEmpty()) {
            System.out.println(" No users in the system.");
            return;
        }
        
        viewAllUsers();
        String userId = getStringInput("\nEnter User ID to view notifications: ");
        k2559671_User user = library.getUser(userId);
        
        if (user == null) {
            System.out.println(" User not found!");
            return;
        }
        
        System.out.println("\n--- Notifications for " + user.getName() + " ---");
        List<String> notifications = user.getNotifications();
        
        if (notifications.isEmpty()) {
            System.out.println(" No notifications yet.");
        } else {
            System.out.println(" You have " + notifications.size() + " notification(s):\n");
            for (int i = 0; i < notifications.size(); i++) {
                System.out.println((i + 1) + ". " + notifications.get(i));
            }
        }
        
        System.out.println("\n✓ Observer Pattern: User is subscribed to library notifications");
    }
    
    private static void sendNotification() {
        System.out.println("\n--- SEND NOTIFICATION (Observer Pattern) ---");
        System.out.println("\nNotification Types:");
        System.out.println("1. Due Date Reminder");
        System.out.println("2. Overdue Alert");
        System.out.println("3. Reservation Available");
        System.out.println("4. Custom Notification");
        
        int type = getIntInput("\nSelect notification type: ");
        
        if (library.getAllUsers().isEmpty()) {
            System.out.println(" No users in the system.");
            return;
        }
        
        viewAllUsers();
        String userId = getStringInput("\nEnter User ID: ");
        k2559671_User user = library.getUser(userId);
        
        if (user == null) {
            System.out.println(" User not found!");
            return;
        }
        
        switch (type) {
            case 1:
                if (library.getAllBooks().isEmpty()) {
                    System.out.println(" No books in the system.");
                    return;
                }
                viewAllBooks();
                String bookId1 = getStringInput("\nEnter Book ID: ");
                k2559671_Book book1 = library.getBook(bookId1);
                if (book1 != null) {
                    library.getNotificationManager().sendDueDateReminder(user, book1);
                    System.out.println("\n✓ Due date reminder sent!");
                } else {
                    System.out.println("⚠ Book not found!");
                }
                break;
            case 2:
                if (library.getAllBooks().isEmpty()) {
                    System.out.println("⚠ No books in the system.");
                    return;
                }
                viewAllBooks();
                String bookId2 = getStringInput("\nEnter Book ID: ");
                k2559671_Book book2 = library.getBook(bookId2);
                if (book2 != null) {
                    library.getNotificationManager().sendOverdueAlert(user, book2);
                    System.out.println("\n✓ Overdue alert sent!");
                } else {
                    System.out.println("⚠ Book not found!");
                }
                break;
            case 3:
                if (library.getAllBooks().isEmpty()) {
                    System.out.println("⚠ No books in the system.");
                    return;
                }
                viewAllBooks();
                String bookId3 = getStringInput("\nEnter Book ID: ");
                k2559671_Book book3 = library.getBook(bookId3);
                if (book3 != null) {
                    library.getNotificationManager().sendReservationAlert(user, book3);
                    System.out.println("\n✓ Reservation alert sent!");
                } else {
                    System.out.println("⚠ Book not found!");
                }
                break;
            case 4:
                String customMessage = getStringInput("\nEnter custom message: ");
                user.update(customMessage);
                System.out.println("\n✓ Custom notification sent!");
                break;
            default:
                System.out.println("⚠ Invalid notification type!");
        }
        
        System.out.println("\n✓ Observer Pattern Applied: User notified successfully");
    }
    
    private static void undoLastCommand() {
        System.out.println("\n--- UNDO LAST COMMAND (Command Pattern) ---");
        
        if (lastCommand == null) {
            System.out.println("⚠ No command to undo!");
            return;
        }
        
        System.out.println("Last Command: " + lastCommand.getDescription());
        System.out.println("\nExecuting UNDO...\n");
        
        lastCommand.undo();
        
        System.out.println("\n✓ Command undone successfully!");
        System.out.println("✓ Command Pattern: Undo operation completed");
        
        lastCommand = null;
    }

    private static void viewAllReservations() {
        System.out.println("\n--- VIEW ALL RESERVATIONS ---");
        
        List<domain.k2559671_Reservation> reservations = library.getAllReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("⚠ No reservations in the system.");
            return;
        }
        
        System.out.println("\nTotal Reservations: " + reservations.size());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (domain.k2559671_Reservation res : reservations) {
            k2559671_User user = library.getUser(res.getUserId());
            k2559671_Book book = library.getBook(res.getBookId());
            
            System.out.println("\nReservation ID     : " + res.getReservationId());
            System.out.println("User               : " + (user != null ? user.getName() : "Unknown") + " (" + res.getUserId() + ")");
            System.out.println("Book               : " + (book != null ? book.getTitle() : "Unknown") + " (" + res.getBookId() + ")");
            System.out.println("Reservation Date   : " + res.getReservationDate());
            System.out.println("Expiry Date        : " + res.getExpiryDate() + " (3 days from creation)");
            System.out.println("Status             : " + res.getStatus());
            System.out.println("Is Active          : " + res.isActive());
            System.out.println("Has Expired        : " + res.hasExpired());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        
        System.out.println("\n✓ Showing " + reservations.size() + " reservation(s)");
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

    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}

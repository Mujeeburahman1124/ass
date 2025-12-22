import java.util.Calendar;
import java.util.Date;

import domain.k2559671_Book;
import domain.k2559671_BookMetadata;
import domain.k2559671_BorrowRecord;
import domain.k2559671_Fine;
import domain.k2559671_MembershipType;
import domain.k2559671_Reservation;
import domain.k2559671_User;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.k2559671_BorrowCommand;
import patterns.command.k2559671_Command;
import patterns.command.k2559671_ReserveCommand;
import patterns.decorator.k2559671_FeaturedDecorator;
import patterns.decorator.k2559671_RecommendedDecorator;
import patterns.decorator.k2559671_SpecialEditionDecorator;
import patterns.strategy.k2559671_FacultyFineStrategy;
import patterns.strategy.k2559671_GuestFineStrategy;
import patterns.strategy.k2559671_StudentFineStrategy;
import system.k2559671_LibrarySystem;

/**
 * Main class - Demonstrates all 6 design patterns in the Smart Library Management System.
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
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   SMART LIBRARY MANAGEMENT SYSTEM - k2559671             ║");
        System.out.println("║   Kingston University - CI6115 PROGRAMMING III           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Initialize the library system
        k2559671_LibrarySystem library = new k2559671_LibrarySystem();
        
        // Create books using Builder Pattern
        k2559671_BookMetadata metadata1 = new k2559671_BookMetadata();
        metadata1.setPublisher("O'Reilly Media");
        metadata1.setEdition("3rd Edition");
        metadata1.setPublicationYear(2020);
        metadata1.addTag("Programming");
        metadata1.addTag("Java");
        metadata1.addReview("Excellent book for learning design patterns!");
        
        k2559671_BookBuilder builder = new k2559671_BookBuilder();
        k2559671_Book book1 = builder
            .setBookId("B001")
            .setTitle("Design Patterns in Java")
            .setAuthor("Joshua Bloch")
            .setCategory("Programming")
            .setISBN("978-0134685991")
            .setMetadata(metadata1)
            .build();
        
        library.addBook(book1);
        
        // Create more books
        builder.reset();
        k2559671_Book book2 = builder
            .setBookId("B002")
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin")
            .setCategory("Programming")
            .setISBN("978-0132350884")
            .build();
        library.addBook(book2);
        
        builder.reset();
        k2559671_Book book3 = builder
            .setBookId("B003")
            .setTitle("The Pragmatic Programmer")
            .setAuthor("Andrew Hunt")
            .setCategory("Software Engineering")
            .setISBN("978-0135957059")
            .build();
        library.addBook(book3);
        
        System.out.println("\n✓ Created 3 books in the library\n");
        
        // Decorate books with special features
        k2559671_FeaturedDecorator featuredBook = new k2559671_FeaturedDecorator(book1);
        System.out.println(featuredBook.getDescription());
        
        k2559671_RecommendedDecorator recommendedBook = new k2559671_RecommendedDecorator(book2, 9, "Dr. Smith");
        System.out.println(recommendedBook.getDescription());
        
        k2559671_SpecialEditionDecorator specialBook = new k2559671_SpecialEditionDecorator(book3, "Signed by Author", 1);
        System.out.println(specialBook.getDescription());
        
        System.out.println();
        
        // Create users with different membership types
        k2559671_User student = new k2559671_User("U001", "Alice Johnson", "alice@student.com", 
                                            "0771234567", k2559671_MembershipType.STUDENT);
        student.setFineStrategy(new k2559671_StudentFineStrategy());
        
        k2559671_User faculty = new k2559671_User("U002", "Dr. Bob Smith", "bob@faculty.com", 
                                                "0777654321", k2559671_MembershipType.FACULTY);
        faculty.setFineStrategy(new k2559671_FacultyFineStrategy());
        
        k2559671_User guest = new k2559671_User("U003", "Charlie Brown", "charlie@guest.com", 
                                                "0779876543", k2559671_MembershipType.GUEST);
        guest.setFineStrategy(new k2559671_GuestFineStrategy());
        
        library.addUser(student);
        library.addUser(faculty);
        library.addUser(guest);
        
        // Demonstrate fine calculation with different strategies
        int daysOverdue = 5;
        System.out.println("\nFine for " + daysOverdue + " days overdue:");
        System.out.println("- Student: LKR " + student.calculateFine(daysOverdue));
        System.out.println("- Faculty: LKR " + faculty.calculateFine(daysOverdue));
        System.out.println("- Guest: LKR " + guest.calculateFine(daysOverdue));
        
        System.out.println();
        
        // Send notifications to users (Observer Pattern)
        library.getNotificationManager().sendDueDateReminder(student, book1);
        library.getNotificationManager().sendOverdueAlert(faculty, book2);
        library.getNotificationManager().sendReservationAlert(guest, book3);
        
        System.out.println("\n--- Book State Changes ---");
        System.out.println("Initial state: " + book1.getAvailabilityStatus());
        
        // Borrow the book (Available -> Borrowed)
        book1.borrow();
        System.out.println("After borrowing: " + book1.getAvailabilityStatus());
        
        // Try to borrow again (should fail)
        book1.borrow();
        
        // Reserve the book (Borrowed -> Reserved)
        book1.reserve();
        System.out.println("After reserving: " + book1.getAvailabilityStatus());
        
        // Return the book (Reserved -> Available)
        book1.returnBook();
        System.out.println("After returning: " + book1.getAvailabilityStatus());
        
        System.out.println("\n--- Processing User Commands ---");
        
        // Create and execute commands
        k2559671_Command borrowCmd = new k2559671_BorrowCommand(student, book2, library.getLogger());
        System.out.println("\nExecuting: " + borrowCmd.getDescription());
        library.processCommand(borrowCmd);
        
        k2559671_Command reserveCmd = new k2559671_ReserveCommand(faculty, book3, library.getLogger());
        System.out.println("\nExecuting: " + reserveCmd.getDescription());
        library.processCommand(reserveCmd);
        
        // Demonstrate undo functionality
        System.out.println("\n--- Undo Command ---");
        System.out.println("Undoing borrow command...");
        borrowCmd.undo();
        
        System.out.println();
        
        // Create borrow records
        Calendar cal = Calendar.getInstance();
        Date borrowDate = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 14);
        Date dueDate = cal.getTime();
        
        k2559671_BorrowRecord record1 = new k2559671_BorrowRecord("R001", "U001", "B001", borrowDate, dueDate);
        library.addBorrowRecord(record1);
        
        // Create reservation
        k2559671_Reservation reservation1 = new k2559671_Reservation("RES001", "U002", "B002");
        library.addReservation(reservation1);
        
        // Create fine
        k2559671_Fine fine1 = new k2559671_Fine("F001", "U003", "B003", 250.0);
        library.addFine(fine1);
        
        System.out.println("\n--- Library Reports ---\n");
        
        // Generate reports
        library.getReportManager().generateMostBorrowedBooks();
        library.getReportManager().generateActiveBorrowers();
        library.getReportManager().generateOverdueBooks();
        library.getReportManager().generateRevenueReport();
        library.getReportManager().generateUserReport("U001");
        
        System.out.println("\n--- System Summary ---");
        
        System.out.println("Total Books: " + library.getAllBooks().size());
        System.out.println("Total Users: " + library.getAllUsers().size());
        System.out.println("Total Borrow Records: " + library.getAllBorrowRecords().size());
        System.out.println("Total Reservations: " + library.getAllReservations().size());
        System.out.println("Total Fines: " + library.getAllFines().size());
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  ALL 6 DESIGN PATTERNS SUCCESSFULLY DEMONSTRATED!        ║");
        System.out.println("║   Observer    Strategy    State                       ║");
        System.out.println("║   Command     Decorator   Builder                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
}

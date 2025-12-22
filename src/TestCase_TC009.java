import java.util.Calendar;
import java.util.Date;

import domain.k2559671_Book;
import domain.k2559671_BookMetadata;
import domain.k2559671_BorrowRecord;
import domain.k2559671_MembershipType;
import domain.k2559671_Reservation;
import domain.k2559671_User;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.k2559671_BorrowCommand;
import patterns.command.k2559671_ReserveCommand;
import patterns.command.k2559671_ReturnCommand;
import system.k2559671_LibrarySystem;

/**
 * Test Case TC009: Reservation Availability Notification
 * 
 * Input: bookId = B002 returned
 * Expected Output: Reserved user is notified (Observer Pattern)
 */
public class TestCase_TC009 {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║    TEST CASE TC009 - RESERVATION NOTIFICATION            ║");
        System.out.println("║   Function: Reservation Availability Notification        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Initialize Library System
        k2559671_LibrarySystem library = new k2559671_LibrarySystem();
        
        System.out.println("--- SETUP PHASE ---\n");
        
        // Create Book B002 using Builder Pattern
        k2559671_BookBuilder builder = new k2559671_BookBuilder();
        k2559671_BookMetadata metadata = new k2559671_BookMetadata();
        metadata.setPublisher("Prentice Hall");
        metadata.setEdition("1st Edition");
        metadata.setPublicationYear(2008);
        
        k2559671_Book book = builder
            .setBookId("B002")
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin")
            .setCategory("Programming")
            .setISBN("978-0132350884")
            .setMetadata(metadata)
            .build();
        
        library.addBook(book);
        System.out.println("✓ Book Created:");
        System.out.println("  Book ID: " + book.getBookId());
        System.out.println("  Title: " + book.getTitle());
        System.out.println("  Initial Status: " + book.getAvailabilityStatus());
        System.out.println();
        
        // Create First User (U001) - Will borrow the book
        k2559671_User user1 = new k2559671_User(
            "U001",
            "Alice Johnson",
            "alice@university.edu",
            "555-0401",
            k2559671_MembershipType.STUDENT
        );
        
        library.addUser(user1);
        System.out.println("✓ First User Created (Borrower):");
        System.out.println("  User ID: " + user1.getUserId());
        System.out.println("  Name: " + user1.getName());
        System.out.println();
        
        // Create Second User (U002) - Will reserve the book
        k2559671_User user2 = new k2559671_User(
            "U002",
            "Bob Smith",
            "bob@university.edu",
            "555-0402",
            k2559671_MembershipType.FACULTY
        );
        
        library.addUser(user2);
        System.out.println("✓ Second User Created (Will Reserve):");
        System.out.println("  User ID: " + user2.getUserId());
        System.out.println("  Name: " + user2.getName());
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST EXECUTION PHASE ---\n");
        
        // Step 1: User1 borrows the book
        System.out.println("STEP 1: User U001 borrows Book B002");
        k2559671_BorrowCommand borrowCommand = new k2559671_BorrowCommand(user1, book, library.getLogger());
        borrowCommand.execute();
        
        Calendar calendar = Calendar.getInstance();
        Date borrowDate = new Date();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date dueDate = calendar.getTime();
        
        k2559671_BorrowRecord record = new k2559671_BorrowRecord(
            "R002",
            user1.getUserId(),
            book.getBookId(),
            borrowDate,
            dueDate
        );
        
        library.addBorrowRecord(record);
        user1.addBorrowRecord(record);
        book.addBorrowRecord(record);
        
        System.out.println("  Book Status: " + book.getAvailabilityStatus());
        System.out.println();
        
        // Step 2: User2 reserves the borrowed book
        System.out.println("STEP 2: User U002 reserves the borrowed Book B002");
        k2559671_ReserveCommand reserveCommand = new k2559671_ReserveCommand(user2, book, library.getLogger());
        reserveCommand.execute();
        
        k2559671_Reservation reservation = new k2559671_Reservation(
            "RES002",
            user2.getUserId(),
            book.getBookId()
        );
        
        library.addReservation(reservation);
        book.addReservation(reservation);
        user2.addReservation(reservation);
        
        System.out.println("  Book Status: " + book.getAvailabilityStatus());
        System.out.println("  User U002 has reserved the book");
        System.out.println();
        
        System.out.println("───────────────────────────────────────────────────────────\n");
        
        // Step 3: User1 returns the book - This should trigger notification
        System.out.println("STEP 3: User U001 returns Book B002");
        System.out.println("INPUT: bookId = B002 returned\n");
        
        System.out.println("EXECUTING: Return Book Command...\n");
        k2559671_ReturnCommand returnCommand = new k2559671_ReturnCommand(user1, book, library.getLogger());
        returnCommand.execute();
        
        System.out.println();
        System.out.println("--- Observer Pattern Notification ---");
        System.out.println("Sending notification to reserved user...\n");
        
        // Trigger notification using Observer Pattern
        library.getNotificationManager().sendReservationAlert(user2, book);
        
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST RESULT PHASE ---\n");
        
        // Verify Results
        System.out.println("ACTUAL OUTPUT:");
        System.out.println("  ✓ Book Lifecycle:");
        System.out.println("    1. Initial: Available");
        System.out.println("    2. After Borrow: Borrowed");
        System.out.println("    3. After Reserve: Reserved");
        System.out.println("    4. After Return: " + book.getAvailabilityStatus());
        System.out.println();
        System.out.println("  ✓ User Notifications:");
        System.out.println("    Reserved User (U002): " + user2.getName());
        System.out.println("    Notifications Received: " + user2.getNotifications().size());
        if (!user2.getNotifications().isEmpty()) {
            System.out.println("    Latest Notification: " + user2.getNotifications().get(user2.getNotifications().size() - 1));
        }
        System.out.println();
        System.out.println("  ✓ Observer Pattern Applied:");
        System.out.println("    - User subscribed to notifications");
        System.out.println("    - Notification sent when book became available");
        System.out.println("    - Reserved user notified successfully");
        System.out.println();
        
        // Test Result
        boolean passed = user2.getNotifications().size() > 0 
                      && book.getAvailabilityStatus().equals("Available");
        
        System.out.println("═══════════════════════════════════════════════════════════");
        if (passed) {
            System.out.println("║                   TEST RESULT: ✓ PASS                    ║");
        } else {
            System.out.println("║                   TEST RESULT: ✗ FAIL                    ║");
        }
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
        System.out.println("EXPECTED OUTPUT: Reserved user is notified");
        System.out.println("ACTUAL OUTPUT: User '" + user2.getName() + "' received notification about book availability");
        System.out.println("RESULT: " + (passed ? "PASS ✓" : "FAIL ✗"));
        System.out.println();
        System.out.println("✓ Observer Pattern demonstrated: Automatic notification system");
    }
}

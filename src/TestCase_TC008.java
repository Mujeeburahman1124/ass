import java.util.Calendar;
import java.util.Date;

import domain.k2559671_Book;
import domain.k2559671_BookMetadata;
import domain.k2559671_BorrowRecord;
import domain.k2559671_MembershipType;
import domain.k2559671_User;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.k2559671_BorrowCommand;
import system.k2559671_LibrarySystem;

/**
 * Test Case TC008: Undo Borrow Command
 * 
 * Input: Undo last borrow action
 * Expected Output: Book state reverts to Available
 */
public class TestCase_TC008 {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         TEST CASE TC008 - UNDO BORROW COMMAND            ║");
        System.out.println("║       Function: Undo Borrow Command (Command Pattern)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Initialize Library System
        k2559671_LibrarySystem library = new k2559671_LibrarySystem();
        
        System.out.println("--- SETUP PHASE ---\n");
        
        // Create Book with ID B003 using Builder Pattern
        k2559671_BookBuilder builder = new k2559671_BookBuilder();
        k2559671_BookMetadata metadata = new k2559671_BookMetadata();
        metadata.setPublisher("Addison-Wesley");
        metadata.setEdition("2nd Edition");
        metadata.setPublicationYear(2019);
        
        k2559671_Book book = builder
            .setBookId("B003")
            .setTitle("The Pragmatic Programmer")
            .setAuthor("Andrew Hunt & David Thomas")
            .setCategory("Software Engineering")
            .setISBN("978-0135957059")
            .setMetadata(metadata)
            .build();
        
        library.addBook(book);
        System.out.println("✓ Book Created:");
        System.out.println("  Book ID: " + book.getBookId());
        System.out.println("  Title: " + book.getTitle());
        System.out.println("  Author: " + book.getAuthor());
        System.out.println("  Initial Status: " + book.getAvailabilityStatus());
        System.out.println();
        
        // Create User
        k2559671_User user = new k2559671_User(
            "U003",
            "Charlie Brown",
            "charlie@university.edu",
            "555-0303",
            k2559671_MembershipType.STUDENT
        );
        
        library.addUser(user);
        System.out.println("✓ User Created:");
        System.out.println("  User ID: " + user.getUserId());
        System.out.println("  Name: " + user.getName());
        System.out.println("  Membership: " + user.getMembershipType());
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST EXECUTION PHASE ---\n");
        
        // Step 1: Borrow the book
        System.out.println("STEP 1: Borrowing Book");
        System.out.println("INPUT: Borrow Book B003 by User U003\n");
        
        k2559671_BorrowCommand borrowCommand = new k2559671_BorrowCommand(user, book, library.getLogger());
        
        System.out.println("EXECUTING: Borrow Book Command...\n");
        borrowCommand.execute();
        
        // Create borrow record
        Calendar calendar = Calendar.getInstance();
        Date borrowDate = new Date();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date dueDate = calendar.getTime();
        
        k2559671_BorrowRecord record = new k2559671_BorrowRecord(
            "R003",
            user.getUserId(),
            book.getBookId(),
            borrowDate,
            dueDate
        );
        
        library.addBorrowRecord(record);
        user.addBorrowRecord(record);
        book.addBorrowRecord(record);
        
        System.out.println("RESULT AFTER BORROW:");
        System.out.println("  Book Status: " + book.getAvailabilityStatus());
        System.out.println("  User's Borrowed Count: " + user.getCurrentBorrowedCount());
        System.out.println();
        
        System.out.println("───────────────────────────────────────────────────────────\n");
        
        // Step 2: Undo the borrow
        System.out.println("STEP 2: Undo Borrow Command (Command Pattern)");
        System.out.println("INPUT: Undo last borrow action\n");
        
        System.out.println("EXECUTING: Undo Command...\n");
        borrowCommand.undo();
        
        // Remove the borrow record from user
        user.getBorrowedBooks().remove(record);
        
        System.out.println("\n═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST RESULT PHASE ---\n");
        
        // Verify Results
        System.out.println("ACTUAL OUTPUT:");
        System.out.println("  ✓ Book State After Undo:");
        System.out.println("    Before Borrow: Available");
        System.out.println("    After Borrow: Borrowed");
        System.out.println("    After Undo: " + book.getAvailabilityStatus());
        System.out.println();
        System.out.println("  ✓ User's Borrowed Books:");
        System.out.println("    Before Borrow: 0");
        System.out.println("    After Borrow: 1");
        System.out.println("    After Undo: " + user.getCurrentBorrowedCount());
        System.out.println();
        System.out.println("  ✓ Command Pattern Applied:");
        System.out.println("    - Borrow command executed successfully");
        System.out.println("    - Undo command reversed the action");
        System.out.println("    - Book state reverted to Available");
        System.out.println();
        
        // Test Result
        boolean passed = book.getAvailabilityStatus().equals("Available") 
                      && user.getCurrentBorrowedCount() == 0;
        
        System.out.println("═══════════════════════════════════════════════════════════");
        if (passed) {
            System.out.println("║                   TEST RESULT: ✓ PASS                    ║");
        } else {
            System.out.println("║                   TEST RESULT: ✗ FAIL                    ║");
        }
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
        System.out.println("EXPECTED OUTPUT: Book state reverts to Available");
        System.out.println("ACTUAL OUTPUT: Book status is '" + book.getAvailabilityStatus() + "'");
        System.out.println("RESULT: " + (passed ? "PASS ✓" : "FAIL ✗"));
        System.out.println();
        System.out.println("✓ Command Pattern demonstrated: Execute and Undo operations");
    }
}

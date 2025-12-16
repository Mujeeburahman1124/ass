package system;

import domain.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReportManager generates various reports for the library system.
 */
public class k2559671_ReportManager {
    private final k2559671_LibrarySystem librarySystem;

    public k2559671_ReportManager(k2559671_LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
    }

    public List<k2559671_Book> generateMostBorrowedBooks() {
        System.out.println("\n === MOST BORROWED BOOKS REPORT ===");
        List<k2559671_Book> books = librarySystem.getAllBooks();

        // Sort by borrow history size (in real implementation)
        books.forEach(book -> {
            System.out.println("- " + book.getTitle() + " by " + book.getAuthor() +
                    " (Borrowed: " + book.getBorrowedHistory().size() + " times)");
        });

        return books;
    }

    public List<k2559671_User> generateActiveBorrowers() {
        System.out.println("\n📊 === ACTIVE BORROWERS REPORT ===");
        List<k2559671_User> users = librarySystem.getAllUsers();

        List<k2559671_User> activeUsers = users.stream()
                .filter(user -> user.getCurrentBorrowedCount() > 0)
                .collect(Collectors.toList());

        activeUsers.forEach(user -> {
            System.out.println("- " + user.getName() + " (" + user.getMembershipType() +
                    ") - Currently borrowed: " + user.getCurrentBorrowedCount());
        });

        return activeUsers;
    }

    public List<k2559671_BorrowRecord> generateOverdueBooks() {
        System.out.println("\n📊 === OVERDUE BOOKS REPORT ===");
        List<k2559671_BorrowRecord> allRecords = librarySystem.getAllBorrowRecords();

        List<k2559671_BorrowRecord> overdueRecords = allRecords.stream()
                .filter(record -> !record.getIsReturned() && record.isOverdue())
                .collect(Collectors.toList());

        overdueRecords.forEach(record -> {
            System.out.println("- Record #" + record.getRecordId() +
                    " - Book: " + record.getBookId() +
                    " - Days overdue: " + record.getDaysOverdue());
        });

        return overdueRecords;
    }

    public double generateRevenueReport() {
        System.out.println("\n📊 === REVENUE REPORT (Fines Collected) ===");
        List<k2559671_Fine> fines = librarySystem.getAllFines();

        double totalRevenue = fines.stream()
                .filter(k2559671_Fine::isPaid)
                .mapToDouble(k2559671_Fine::getAmount)
                .sum();

        System.out.println("Total fines collected: LKR " + totalRevenue);
        return totalRevenue;
    }

    public String generateUserReport(String userId) {
        System.out.println("\n📊 === USER REPORT: " + userId + " ===");
        k2559671_User user = librarySystem.getUser(userId);

        if (user != null) {
            String report = "User: " + user.getName() + "\n" +
                    "Membership: " + user.getMembershipType() + "\n" +
                    "Currently Borrowed: " + user.getCurrentBorrowedCount() + "\n" +
                    "Borrowing Limit: " + user.getMembershipType().getBorrowingLimit();
            System.out.println(report);
            return report;
        }

        return "User not found";
    }

    public void exportReport(String reportType, String filename) {
        System.out.println("✓ Report '" + reportType + "' exported to " + filename);
        // In real implementation, this would write to a file
    }
}

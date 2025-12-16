package domain;

import patterns.observer.k2559671_Observer;
import patterns.strategy.k2559671_FineStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * User class represents a library user.
 * Implements Observer pattern to receive notifications.
 * Uses Strategy pattern for fine calculation based on membership type.
 */
public class k2559671_User implements k2559671_Observer {
    private final String userId;
    private final String name;
    private final String email;
    private final String contactNumber;
    private final k2559671_MembershipType membershipType;
    private final List<k2559671_BorrowRecord> borrowedBooksHistory;
    private k2559671_FineStrategy fineStrategy;
    private int borrowingLimit;
    private int currentBorrowedCount;
    
    public k2559671_User(String userId, String name, String email, String contactNumber, 
                         k2559671_MembershipType membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
        this.borrowedBooksHistory = new ArrayList<>();
        this.borrowingLimit = membershipType.getBorrowingLimit();
        this.currentBorrowedCount = 0;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public k2559671_MembershipType getMembershipType() {
        return membershipType;
    }
    
    public boolean borrowBook(k2559671_Book book) {
        if (!canBorrow()) {
            System.out.println("Cannot borrow: Borrowing limit reached!");
            return false;
        }
        currentBorrowedCount++;
        return true;
    }
    
    public void returnBook(k2559671_Book book) {
        if (currentBorrowedCount > 0) {
            currentBorrowedCount--;
        }
    }
    
    public void reserveBook(k2559671_Book book) {
        System.out.println(name + " reserved book: " + book.getTitle());
    }
    
    @Override
    public void update(String message) {
        System.out.println("📧 Notification for " + name + ": " + message);
    }
    
    public double calculateFine(int daysOverdue) {
        if (fineStrategy != null) {
            return fineStrategy.calculateFine(daysOverdue);
        }
        return membershipType.getFineRate() * daysOverdue;
    }
    
    public boolean canBorrow() {
        return currentBorrowedCount < borrowingLimit;
    }
    
    public List<k2559671_Book> getBorrowedBooks() {
        // This would return currently borrowed books in a real implementation
        return new ArrayList<>();
    }
    
    public void setBorrowingLimit(int limit) {
        this.borrowingLimit = limit;
    }
    
    public void setFineStrategy(k2559671_FineStrategy strategy) {
        this.fineStrategy = strategy;
    }
    
    public int getCurrentBorrowedCount() {
        return currentBorrowedCount;
    }
    
    public void addBorrowRecord(k2559671_BorrowRecord record) {
        borrowedBooksHistory.add(record);
    }
    
    @Override
    public String toString() {
        return name + " (" + membershipType + ") - " + email;
    }
}

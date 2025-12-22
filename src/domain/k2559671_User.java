package domain;

import java.util.ArrayList;
import java.util.List;

import patterns.observer.k2559671_Observer;
import patterns.strategy.k2559671_FineStrategy;

/**
 * User class represents a library user in the Smart Library Management System.
 * 
 * Design Pattern: Observer Pattern
 * - Implements k2559671_Observer interface to receive real-time notifications
 *   about due dates, overdue books, and reservation availability.
 * 
 * Features:
 * - Manages user personal information and membership type
 * - Tracks borrowing history and current borrowed books
 * - Handles reservations for books
 * - Enforces borrowing limits based on membership type
 * - Receives automated notifications via Observer pattern
 * 
 * Relationships (as per class diagram):
 * - Has many k2559671_BorrowRecord objects (borrowing history) [1→0..*]
 * - Has many k2559671_Reservation objects (reservation history) [1→0..*]
 * - Associated with k2559671_MembershipType enum [1→1]
 * - Associated with k2559671_FineStrategy for fine calculations [1→1]
 */
public class k2559671_User implements k2559671_Observer {
    private final String userId;
    private final String name;
    private final String email;
    private final String contactNo;
    private final k2559671_MembershipType membershipType;
    private final List<k2559671_BorrowRecord> borrowedBooks;
    private final List<k2559671_Reservation> reservations;
    private final List<String> notifications;
    private k2559671_FineStrategy fineStrategy;

    /**
     * Constructor for creating a new library user.
     * 
     * @param userId Unique identifier for the user
     * @param name Full name of the user
     * @param email Email address for notifications
     * @param contactNo Contact number for communication
     * @param membershipType Type of membership (STUDENT, FACULTY, GUEST)
     */
    public k2559671_User(String userId, String name, String email, String contactNo,
            k2559671_MembershipType membershipType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.membershipType = membershipType;
        this.borrowedBooks = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.notifications = new ArrayList<>();
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

    public String getContactNo() {
        return contactNo;
    }

    public k2559671_MembershipType getMembershipType() {
        return membershipType;
    }

    public List<k2559671_BorrowRecord> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<k2559671_Reservation> getReservations() {
        return reservations;
    }

    /**
     * Get the borrowing limit based on membership type.
     * Student: 5 books, Faculty: 10 books, Guest: 2 books
     * 
     * @return Maximum number of books that can be borrowed simultaneously
     */
    public int getBorrowLimit() {
        return membershipType.getBorrowingLimit();
    }

    /**
     * Get the current count of books borrowed by the user.
     * Only counts books that haven't been returned yet.
     * 
     * @return Number of currently borrowed books
     */
    public int getCurrentBorrowedCount() {
        return (int) borrowedBooks.stream()
                .filter(record -> !record.getIsReturned())
                .count();
    }

    /**
     * Check if the user can borrow more books based on their limit.
     * 
     * @return true if user hasn't reached their borrowing limit
     */
    public boolean canBorrowMore() {
        return getCurrentBorrowedCount() < getBorrowLimit();
    }

    /**
     * Strategy Pattern: Set the fine calculation strategy.
     * Different strategies for Student, Faculty, and Guest members.
     * Multiplicity: User [1] → FineStrategy [1]
     * 
     * @param fineStrategy Strategy to calculate fines
     */
    public void setFineStrategy(k2559671_FineStrategy fineStrategy) {
        this.fineStrategy = fineStrategy;
    }

    /**
     * Strategy Pattern: Get the assigned fine calculation strategy.
     * 
     * @return Current fine strategy
     */
    public k2559671_FineStrategy getFineStrategy() {
        return fineStrategy;
    }

    /**
     * Strategy Pattern: Calculate fine using the assigned strategy.
     * Uses membership-specific fine rates:
     * - Student: LKR 50/day
     * - Faculty: LKR 20/day
     * - Guest: LKR 100/day
     * 
     * @param daysOverdue Number of days the book is overdue
     * @return Fine amount in LKR
     */
    public double calculateFine(int daysOverdue) {
        if (fineStrategy == null) {
            // Default strategy based on membership type
            return membershipType.getFineRate() * daysOverdue;
        }
        return fineStrategy.calculateFine(daysOverdue);
    }

    /**
     * Alias for canBorrowMore() for backward compatibility.
     * 
     * @return true if user can borrow more books
     */
    public boolean canBorrow() {
        return canBorrowMore();
    }

    /**
     * Observer Pattern Implementation.
     * Receives notifications about overdue books, due dates, and reservations.
     * 
     * @param message Notification message to display
     */
    @Override
    public void update(String message) {
        notifications.add(message);
        System.out.println("📧 Notification for " + name + ": " + message);
    }
    
    /**
     * Get all notifications received by this user.
     * 
     * @return List of notification messages
     */
    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public void borrowBook(k2559671_Book book) {
        System.out.println(name + " is borrowing: " + book.getTitle());
    }

    public void returnBook(k2559671_Book book) {
        System.out.println(name + " is returning: " + book.getTitle());
    }

    public void reserveBook(k2559671_Book book) {
        System.out.println(name + " is reserving: " + book.getTitle());
    }

    /**
     * Add a borrow record to the user's history.
     * 
     * @param record The borrow record to add
     */
    public void addBorrowRecord(k2559671_BorrowRecord record) {
        borrowedBooks.add(record);
    }

    public void addReservation(k2559671_Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public String toString() {
        return name + " (" + membershipType + ")";
    }
}

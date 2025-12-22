package domain;

import java.util.ArrayList;
import java.util.List;

import patterns.state.k2559671_AvailableState;
import patterns.state.k2559671_BookState;

/**
 * Book class represents a book in the Smart Library Management System.
 * 
 * Design Pattern: State Pattern
 * - Uses k2559671_BookState to manage availability states dynamically
 * - States: Available, Borrowed, Reserved
 * - State transitions are handled automatically based on actions
 * 
 * Design Pattern: Decorator Pattern (as base component)
 * - Can be wrapped with decorators for special features
 * - Supports FeaturedDecorator, RecommendedDecorator, SpecialEditionDecorator
 * 
 * Design Pattern: Builder Pattern (constructed via)
 * - Created using k2559671_BookBuilder for complex object construction
 * - Supports optional metadata, tags, reviews, and edition details
 * 
 * Features:
 * - Comprehensive book information (ID, title, author, category, ISBN)
 * - Dynamic state management (Available/Borrowed/Reserved)
 * - Borrowed history tracking for reporting
 * - Optional metadata (publisher, edition, year, tags, reviews)
 * - State-based behavior changes
 * 
 * Relationships (as per class diagram):
 * - Has one k2559671_BookState (current state) [1→1]
 * - Has zero or one k2559671_BookMetadata (composition) [1→1]
 * - Has many k2559671_BorrowRecord objects (history) [1→0..*]
 * - Has many k2559671_Reservation objects [1→0..*]
 */
public class k2559671_Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final String category;
    private final String isbn;
    private String availabilityStatus;
    private final List<k2559671_BorrowRecord> borrowedHistory;
    private final List<k2559671_Reservation> reservations;
    private k2559671_BookState currentState;
    private k2559671_BookMetadata metadata;
    
    /**
     * Constructor for creating a new book.
     * Initializes with Available state by default.
     *
     * @param bookId Unique identifier for the book
     * @param title Title of the book
     * @param author Author of the book
     * @param category Category/Genre of the book
     * @param isbn International Standard Book Number
     */
    public k2559671_Book(String bookId, String title, String author, String category, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.borrowedHistory = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.currentState = new k2559671_AvailableState();
        this.availabilityStatus = "Available";
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getISBN() {
        return isbn;
    }
    
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    /**
     * Get detailed information about the book including metadata.
     *
     * @return Formatted string with book details
     */
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Book ID: ").append(bookId).append("\n");
        details.append("Title: ").append(title).append("\n");
        details.append("Author: ").append(author).append("\n");
        details.append("Category: ").append(category).append("\n");
        details.append("ISBN: ").append(isbn).append("\n");
        details.append("Status: ").append(availabilityStatus).append("\n");
        details.append("Times Borrowed: ").append(borrowedHistory.size()).append("\n");
        
        if (metadata != null) {
            details.append("Publisher: ").append(metadata.getPublisher()).append("\n");
            details.append("Edition: ").append(metadata.getEdition()).append("\n");
            if (metadata.getPublicationYear() > 0) {
                details.append("Year: ").append(metadata.getPublicationYear()).append("\n");
            }
        }
        
        return details.toString();
    }
    
    /**
     * Update the book's availability status.
     * 
     * @param status New status (Available, Borrowed, Reserved)
     */
    public void updateStatus(String status) {
        this.availabilityStatus = status;
    }
    
    /**
     * State Pattern: Change the current state of the book.
     * Updates both the state object and availability status string.
     * 
     * @param state New book state
     */
    public void setState(k2559671_BookState state) {
        this.currentState = state;
        this.availabilityStatus = state.getStateName();
    }
    
    public k2559671_BookState getState() {
        return currentState;
    }
    
    /**
     * State Pattern: Borrow action - delegates to current state.
     * State determines if borrowing is allowed and handles transition.
     */
    public void borrow() {
        currentState.borrow(this);
    }
    
    /**
     * State Pattern: Return action - delegates to current state.
     * State handles transition back to Available state.
     */
    public void returnBook() {
        currentState.returnBook(this);
    }
    
    /**
     * State Pattern: Reserve action - delegates to current state.
     * State determines if reservation is allowed and handles transition.
     */
    public void reserve() {
        currentState.reserve(this);
    }
    
    /**
     * Builder Pattern: Set optional metadata for the book.
     * Metadata includes publisher, edition, publication year, tags, and reviews.
     * 
     * @param metadata Book metadata object
     */
    public void setMetadata(k2559671_BookMetadata metadata) {
        this.metadata = metadata;
    }
    
    public k2559671_BookMetadata getMetadata() {
        return metadata;
    }
    
    /**
     * Add a borrow record to the book's history.
     * Used for tracking popularity and generating reports.
     * 
     * @param record Borrow record to add
     */
    public void addBorrowRecord(k2559671_BorrowRecord record) {
        borrowedHistory.add(record);
    }
    
    /**
     * Get the complete borrowed history of this book.
     * Returns a copy to maintain encapsulation.
     * 
     * @return List of all borrow records for this book
     */
    public List<k2559671_BorrowRecord> getBorrowedHistory() {
        return new ArrayList<>(borrowedHistory);
    }
    
    /**
     * Add a reservation to the book's reservation list.
     * Multiplicity: Book [1] → Reservation [0..*]
     * 
     * @param reservation Reservation to add
     */
    public void addReservation(k2559671_Reservation reservation) {
        reservations.add(reservation);
    }
    
    /**
     * Remove a reservation from the book's reservation list.
     * 
     * @param reservation Reservation to remove
     */
    public void removeReservation(k2559671_Reservation reservation) {
        reservations.remove(reservation);
    }
    
    /**
     * Get all reservations for this book.
     * Returns a copy to maintain encapsulation.
     * 
     * @return List of all reservations for this book
     */
    public List<k2559671_Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
    
    /**
     * Get count of active reservations for this book.
     * 
     * @return Number of active reservations
     */
    public int getActiveReservationCount() {
        return (int) reservations.stream()
                .filter(k2559671_Reservation::isActive)
                .count();
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " [" + availabilityStatus + "]";
    }
}

package domain;

import patterns.state.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Book class represents a book in the library system.
 * Uses State Pattern to manage availability states.
 * Can be decorated using Decorator Pattern for special features.
 */
public class k2559671_Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final String category;
    private final String isbn;
    private String availabilityStatus;
    private final List<k2559671_BorrowRecord> borrowedHistory;
    private k2559671_BookState currentState;
    private k2559671_BookMetadata metadata;
    
    public k2559671_Book(String bookId, String title, String author, String category, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.borrowedHistory = new ArrayList<>();
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
    
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Book ID: ").append(bookId).append("\n");
        details.append("Title: ").append(title).append("\n");
        details.append("Author: ").append(author).append("\n");
        details.append("Category: ").append(category).append("\n");
        details.append("ISBN: ").append(isbn).append("\n");
        details.append("Status: ").append(availabilityStatus).append("\n");
        
        if (metadata != null) {
            details.append("Publisher: ").append(metadata.getPublisher()).append("\n");
            details.append("Edition: ").append(metadata.getEdition()).append("\n");
        }
        
        return details.toString();
    }
    
    public void updateStatus(String status) {
        this.availabilityStatus = status;
    }
    
    public void setState(k2559671_BookState state) {
        this.currentState = state;
        this.availabilityStatus = state.getStateName();
    }
    
    public k2559671_BookState getState() {
        return currentState;
    }
    
    public void borrow() {
        currentState.borrow(this);
    }
    
    public void returnBook() {
        currentState.returnBook(this);
    }
    
    public void reserve() {
        currentState.reserve(this);
    }
    
    public void setMetadata(k2559671_BookMetadata metadata) {
        this.metadata = metadata;
    }
    
    public k2559671_BookMetadata getMetadata() {
        return metadata;
    }
    
    public void addBorrowRecord(k2559671_BorrowRecord record) {
        borrowedHistory.add(record);
    }
    
    public List<k2559671_BorrowRecord> getBorrowedHistory() {
        return new ArrayList<>(borrowedHistory);
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " [" + availabilityStatus + "]";
    }
}

package patterns.decorator;

import domain.k2559671_Book;

/**
 * Decorator Pattern: Abstract decorator for books.
 * Allows adding optional features to books dynamically.
 * 
 * Pattern Justification:
 * - Books can have optional features (Featured, Recommended, Special Edition)
 * - Features can be added/removed at runtime
 * - Avoids class explosion from creating subclasses for every combination
 */
public abstract class k2559671_BookDecorator {
    protected k2559671_Book decoratedBook;
    
    public k2559671_BookDecorator(k2559671_Book book) {
        this.decoratedBook = book;
    }
    
    public abstract String getDescription();
    
    public String getDetails() {
        return decoratedBook.getDetails();
    }
    
    // Delegate all book methods to the decorated book
    public String getBookId() {
        return decoratedBook.getBookId();
    }
    
    public String getTitle() {
        return decoratedBook.getTitle();
    }
    
    public String getAuthor() {
        return decoratedBook.getAuthor();
    }
}

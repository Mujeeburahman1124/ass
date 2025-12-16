package patterns.builder;

import domain.k2559671_Book;
import domain.k2559671_BookMetadata;

/**
 * Builder Pattern: Book builder for creating complex book objects.
 * 
 * Pattern Justification:
 * - Books have many optional parameters (metadata, reviews, tags, etc.)
 * - Provides a fluent interface for book creation
 * - Separates construction logic from representation
 * - Makes code more readable and maintainable
 */
public class k2559671_BookBuilder {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private k2559671_BookMetadata metadata;
    
    public k2559671_BookBuilder setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }
    
    public k2559671_BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public k2559671_BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }
    
    public k2559671_BookBuilder setCategory(String category) {
        this.category = category;
        return this;
    }
    
    public k2559671_BookBuilder setISBN(String isbn) {
        this.isbn = isbn;
        return this;
    }
    
    public k2559671_BookBuilder setMetadata(k2559671_BookMetadata metadata) {
        this.metadata = metadata;
        return this;
    }
    
    public k2559671_Book build() {
        // Validate required fields
        if (bookId == null || title == null || author == null) {
            throw new IllegalStateException("Book must have ID, title, and author");
        }
        
        k2559671_Book book = new k2559671_Book(bookId, title, author, category, isbn);
        
        if (metadata != null) {
            book.setMetadata(metadata);
        }
        
        return book;
    }
    
    public void reset() {
        this.bookId = null;
        this.title = null;
        this.author = null;
        this.category = null;
        this.isbn = null;
        this.metadata = null;
    }
}

package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store additional metadata about a book.
 * Used with Builder pattern to optionally add metadata to books.
 */
public class k2559671_BookMetadata {
    private String publisher;
    private String edition;
    private int pageCount;
    private String language;
    private int publicationYear;
    private List<String> tags;
    private List<String> reviews;
    
    public k2559671_BookMetadata() {
        this.tags = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }
    
    public k2559671_BookMetadata(String publisher, String edition, int pageCount, String language) {
        this();
        this.publisher = publisher;
        this.edition = edition;
        this.pageCount = pageCount;
        this.language = language;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getEdition() {
        return edition;
    }
    
    public void setEdition(String edition) {
        this.edition = edition;
    }
    
    public int getPageCount() {
        return pageCount;
    }
    
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public int getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void addTag(String tag) {
        this.tags.add(tag);
    }
    
    public List<String> getReviews() {
        return reviews;
    }
    
    public void addReview(String review) {
        this.reviews.add(review);
    }
}

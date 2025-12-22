package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * BookMetadata class stores additional information about books.
 *
 * Design Pattern: Builder Pattern (component)
 * - Used with k2559671_BookBuilder to construct books with optional metadata
 * - Allows flexible book creation without constructor overload
 *
 * Features:
 * - Publisher and publication details
 * - Edition information
 * - Tags for categorization
 * - User reviews and ratings
 * - Physical book properties (pages, language)
 *
 * Relationships:
 * - Associated with k2559671_Book (optional one-to-one)
 * - Created via k2559671_BookBuilder
 */
public class k2559671_BookMetadata {
    private String publisher;
    private int publicationYear;
    private String edition;
    private final List<String> tags;
    private final List<String> reviews;
    private String summary;
    private String language;
    private int pageCount;

    /**
     * Constructor initializes default metadata values.
     */
    public k2559671_BookMetadata() {
        this.tags = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.publisher = "Unknown Publisher";
        this.publicationYear = 0;
        this.edition = "1st Edition";
        this.summary = "No summary available";
        this.language = "English";
        this.pageCount = 0;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getFullMetadata() {
        StringBuilder metadata = new StringBuilder();
        metadata.append("Publisher: ").append(publisher);
        if (publicationYear > 0) {
            metadata.append(", Year: ").append(publicationYear);
        }
        metadata.append(", Edition: ").append(edition);
        metadata.append(", Language: ").append(language);
        metadata.append(", Pages: ").append(pageCount);
        if (!tags.isEmpty()) {
            metadata.append(", Tags: ").append(String.join(", ", tags));
        }
        return metadata.toString();
    }
}

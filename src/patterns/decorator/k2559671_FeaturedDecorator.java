package patterns.decorator;

import domain.k2559671_Book;
import java.util.Date;

/**
 * Decorator Pattern: Featured book decorator.
 * Adds "Featured" badge to books.
 */
public class k2559671_FeaturedDecorator extends k2559671_BookDecorator {
    private Date featuredDate;
    
    public k2559671_FeaturedDecorator(k2559671_Book book) {
        super(book);
        this.featuredDate = new Date();
    }
    
    @Override
    public String getDescription() {
        return "⭐ FEATURED: " + decoratedBook.getTitle();
    }
    
    public String getFeaturedBadge() {
        return "⭐ Featured Book";
    }
    
    public boolean isFeatured() {
        return true;
    }
    
    @Override
    public String getDetails() {
        return super.getDetails() + "\n" + getFeaturedBadge() + " (since " + featuredDate + ")";
    }
}

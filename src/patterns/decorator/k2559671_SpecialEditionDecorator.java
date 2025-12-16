package patterns.decorator;

import domain.k2559671_Book;

/**
 * Decorator Pattern: Special Edition book decorator.
 * Adds special edition information to books.
 */
public class k2559671_SpecialEditionDecorator extends k2559671_BookDecorator {
    private String editionDetails;
    private int editionNumber;
    
    public k2559671_SpecialEditionDecorator(k2559671_Book book, String details, int editionNumber) {
        super(book);
        this.editionDetails = details;
        this.editionNumber = editionNumber;
    }
    
    @Override
    public String getDescription() {
        return "💎 SPECIAL EDITION: " + decoratedBook.getTitle() + " (" + editionDetails + ")";
    }
    
    public String getEditionInfo() {
        return editionDetails;
    }
    
    public int getEditionNumber() {
        return editionNumber;
    }
    
    @Override
    public String getDetails() {
        return super.getDetails() + "\n💎 Special Edition #" + editionNumber + 
               ": " + editionDetails;
    }
}

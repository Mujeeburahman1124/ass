package patterns.decorator;

import domain.k2559671_Book;

/**
 * Decorator Pattern: Recommended book decorator.
 * Adds recommendation information to books.
 */
public class k2559671_RecommendedDecorator extends k2559671_BookDecorator {
    private int recommendationScore;
    private String recommendedBy;
    
    public k2559671_RecommendedDecorator(k2559671_Book book, int score, String recommendedBy) {
        super(book);
        this.recommendationScore = score;
        this.recommendedBy = recommendedBy;
    }
    
    @Override
    public String getDescription() {
        return "👍 RECOMMENDED: " + decoratedBook.getTitle() + " (Score: " + recommendationScore + "/10)";
    }
    
    public int getRecommendationScore() {
        return recommendationScore;
    }
    
    public String getRecommendedBy() {
        return recommendedBy;
    }
    
    @Override
    public String getDetails() {
        return super.getDetails() + "\n👍 Recommended by " + recommendedBy + 
               " (Score: " + recommendationScore + "/10)";
    }
}

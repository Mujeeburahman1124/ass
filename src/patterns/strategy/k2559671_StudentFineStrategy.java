package patterns.strategy;

/**
 * Strategy Pattern: Student fine calculation strategy.
 * Students pay LKR 50 per day for overdue books.
 */
public class k2559671_StudentFineStrategy implements k2559671_FineStrategy {
    private static final double DAILY_RATE = 50.0;
    
    @Override
    public double calculateFine(int daysOverdue) {
        return DAILY_RATE * daysOverdue;
    }
}

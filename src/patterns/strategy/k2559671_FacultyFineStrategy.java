package patterns.strategy;

/**
 * Strategy Pattern: Faculty fine calculation strategy.
 * Faculty members pay LKR 20 per day for overdue books.
 */
public class k2559671_FacultyFineStrategy implements k2559671_FineStrategy {
    private static final double DAILY_RATE = 20.0;
    
    @Override
    public double calculateFine(int daysOverdue) {
        return DAILY_RATE * daysOverdue;
    }
}

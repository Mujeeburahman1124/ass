package patterns.strategy;

/**
 * Strategy Pattern: Guest fine calculation strategy.
 * Guests pay LKR 100 per day for overdue books.
 */
public class k2559671_GuestFineStrategy implements k2559671_FineStrategy {
    private static final double DAILY_RATE = 100.0;
    
    @Override
    public double calculateFine(int daysOverdue) {
        return DAILY_RATE * daysOverdue;
    }
}

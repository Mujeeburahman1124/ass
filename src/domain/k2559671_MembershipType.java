package domain;

/**
 * Enum representing different membership types in the library system.
 * Each type has different borrowing limits and fine rates.
 */
public enum k2559671_MembershipType {
    STUDENT(5, 0.5),
    FACULTY(10, 0.25),
    GUEST(2, 1.0);
    
    private final int borrowingLimit;
    private final double fineRate;
    
    k2559671_MembershipType(int borrowingLimit, double fineRate) {
        this.borrowingLimit = borrowingLimit;
        this.fineRate = fineRate;
    }
    
    public int getBorrowingLimit() {
        return borrowingLimit;
    }
    
    public double getFineRate() {
        return fineRate;
    }
}

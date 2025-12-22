package domain;

import java.util.Date;

/**
 * BorrowRecord class tracks book borrowing transactions in the library system.
 *
 * Features:
 * - Records complete borrowing transaction details
 * - Tracks borrowing date, due date, and return date
 * - Calculates overdue days and fine amounts
 * - Supports fine calculation based on membership type
 * - Maintains historical data for reporting purposes
 *
 * Relationships:
 * - Associated with k2559671_User (borrower)
 * - Associated with k2559671_Book (borrowed item)
 * - Linked to k2559671_Fine for penalty tracking
 *
 * Business Logic:
 * - Due dates are calculated based on membership type (14/30 days)
 * - Fines are calculated: Student LKR50/day, Faculty LKR20/day, Guest LKR100/day
 * - Tracks return status and actual return date
 */
public class k2559671_BorrowRecord {
    private final String recordId;
    private final String userId;
    private final String bookId;
    private final Date borrowDate;
    private final Date dueDate;
    private Date returnDate;
    private double fineAmount;
    private boolean isReturned;
    
    /**
     * Constructor for creating a new borrow record.
     *
     * @param recordId Unique identifier for this borrow record
     * @param userId ID of the user borrowing the book
     * @param bookId ID of the book being borrowed
     * @param borrowDate Date when the book was borrowed
     * @param dueDate Date when the book should be returned
     */
    public k2559671_BorrowRecord(String recordId, String userId, String bookId, Date borrowDate, Date dueDate) {
        this.recordId = recordId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.isReturned = false;
        this.fineAmount = 0.0;
    }
    
    public String getRecordId() {
        return recordId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public Date getBorrowDate() {
        return borrowDate;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    /**
     * Calculate fine amount based on overdue days.
     * Returns the stored fine amount if overdue, otherwise 0.
     *
     * @return Fine amount in LKR
     */
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        return fineAmount;
    }
    
    /**
     * Check if the book is overdue (past due date).
     * If returned, checks if it was returned late.
     * If not returned, checks against current date.
     *
     * @return true if book is/was overdue
     */
    public boolean isOverdue() {
        if (isReturned) {
            return returnDate.after(dueDate);
        }
        return new Date().after(dueDate);
    }
    
    /**
     * Mark the book as returned with current timestamp.
     * Sets return date and updates return status.
     */
    public void markReturned() {
        this.returnDate = new Date();
        this.isReturned = true;
    }
    
    /**
     * Calculate number of days the book is overdue.
     * Compares due date with return date (if returned) or current date.
     *
     * @return Number of days overdue, or 0 if not overdue
     */
    public int getDaysOverdue() {
        Date compareDate = isReturned ? returnDate : new Date();
        if (compareDate.after(dueDate)) {
            long diff = compareDate.getTime() - dueDate.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        }
        return 0;
    }
    
    /**
     * Set the fine amount for this borrow record.
     * Called when fine is calculated based on membership type and days overdue.
     *
     * @param amount Fine amount in LKR
     */
    public void setFineAmount(double amount) {
        this.fineAmount = amount;
    }
    
    /**
     * Get the return status of this borrow record.
     *
     * @return true if book has been returned
     */
    public boolean getIsReturned() {
        return isReturned;
    }
    
    /**
     * Get fine amount for this record.
     *
     * @return Fine amount in LKR
     */
    public double getFineAmount() {
        return fineAmount;
    }
    
    @Override
    public String toString() {
        return "Record #" + recordId + " - Book: " + bookId + ", User: " + userId + 
        ", Due: " + dueDate + ", Returned: " + isReturned;
    }
}

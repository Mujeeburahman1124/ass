package domain;

import java.util.Date;

/**
 * BorrowRecord class tracks book borrowing transactions.
 * Records borrowing date, due date, return date, and fine information.
 */
public class k2559671_BorrowRecord {
    private String recordId;
    private String userId;
    private String bookId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private double fineAmount;
    private boolean isReturned;
    
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
    
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        return fineAmount;
    }
    
    public boolean isOverdue() {
        if (isReturned) {
            return returnDate.after(dueDate);
        }
        return new Date().after(dueDate);
    }
    
    public void markReturned() {
        this.returnDate = new Date();
        this.isReturned = true;
    }
    
    public int getDaysOverdue() {
        Date compareDate = isReturned ? returnDate : new Date();
        if (compareDate.after(dueDate)) {
            long diff = compareDate.getTime() - dueDate.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        }
        return 0;
    }
    
    public void setFineAmount(double amount) {
        this.fineAmount = amount;
    }
    
    public boolean getIsReturned() {
        return isReturned;
    }
    
    @Override
    public String toString() {
        return "Record #" + recordId + " - Book: " + bookId + ", User: " + userId + 
               ", Due: " + dueDate + ", Returned: " + isReturned;
    }
}

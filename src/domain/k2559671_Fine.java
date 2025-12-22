package domain;

import java.util.Date;

/**
 * Fine class represents a fine issued to a user for late book returns.
 */
public class k2559671_Fine {
    private final String fineId;
    private final String userId;
    private final String bookId;
    private double amount;
    private boolean isPaid;
    private final Date dateIssued;
    private Date datePaid;
    
    public k2559671_Fine(String fineId, String userId, String bookId, double amount) {
        this.fineId = fineId;
        this.userId = userId;
        this.bookId = bookId;
        this.amount = amount;
        this.isPaid = false;
        this.dateIssued = new Date();
    }
    
    public String getFineId() {
        return fineId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public void payFine() {
        this.isPaid = true;
        this.datePaid = new Date();
    }
    
    public String getFineDetails() {
        return "Fine #" + fineId + " - Amount: LKR " + amount + 
        ", User: " + userId + ", Book: " + bookId + 
        ", Paid: " + (isPaid ? "Yes" : "No");
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public Date getDateIssued() {
        return dateIssued;
    }
    
    public Date getDatePaid() {
        return datePaid;
    }
    
    @Override
    public String toString() {
        return getFineDetails();
    }
}

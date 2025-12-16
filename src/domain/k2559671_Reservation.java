package domain;

import java.util.Date;

/**
 * Reservation class manages book reservations.
 * Users can reserve books that are currently borrowed.
 */
public class k2559671_Reservation {
    private String reservationId;
    private String userId;
    private String bookId;
    private Date reservationDate;
    private String status;
    private Date expiryDate;
    
    public k2559671_Reservation(String reservationId, String userId, String bookId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = new Date();
        this.status = "Active";
        
        // Reservation expires in 3 days
        long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000L;
        this.expiryDate = new Date(System.currentTimeMillis() + threeDaysInMillis);
    }
    
    public String getReservationId() {
        return reservationId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public Date getReservationDate() {
        return reservationDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    public void notify(String message) {
        System.out.println("Notification for Reservation #" + reservationId + ": " + message);
    }
    
    public void cancel() {
        this.status = "Cancelled";
    }
    
    public boolean isActive() {
        return status.equals("Active") && !hasExpired();
    }
    
    public boolean hasExpired() {
        return new Date().after(expiryDate);
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Reservation #" + reservationId + " - Book: " + bookId + 
               ", User: " + userId + ", Status: " + status;
    }
}

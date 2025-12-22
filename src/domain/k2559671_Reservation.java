package domain;

import java.util.Date;

/**
 * Reservation class manages book reservations in the library system.
 *
 * Features:
 * - Allows users to reserve books that are currently borrowed
 * - Automatically notifies users when reserved books become available
 * - Tracks reservation status and expiry dates
 * - Supports reservation cancellation
 *
 * Relationships (as per class diagram):
 * - Associated with k2559671_User [User 1 → Reservation 0..*]
 * - Associated with k2559671_Book [Book 1 → Reservation 0..*]
 * - Linked to k2559671_Observer for notification handling
 * Business Logic:
 * - Reservations expire after 3 days if not fulfilled
 * - Users are notified via Observer pattern when books become available
 * - Active reservations are prioritized when books are returned
 */
public class k2559671_Reservation {
    private final String reservationId;
    private final String userId;
    private final String bookId;
    private final Date reservationDate;
    private String status;
    private final Date expiryDate;
    
    /**
     * Constructor for creating a new reservation.
     * Automatically sets reservation date to current time.
     * Expiry date is set to 3 days from reservation.
     *
     * @param reservationId Unique identifier for the reservation
     * @param userId ID of the user making the reservation
     * @param bookId ID of the book being reserved
     */
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
    
    /**
     * Send notification about this reservation.
     * Used when reserved book becomes available.
     * 
     * @param message Notification message
     */
    public void notify(String message) {
        System.out.println("📬 Notification for Reservation #" + reservationId + ": " + message);
    }
    
    /**
     * Cancel this reservation.
     * Changes status to "Cancelled".
     */
    public void cancel() {
        this.status = "Cancelled";
    }
    
    /**
     * Check if reservation is currently active.
     * Active means status is "Active" and hasn't expired.
     *
     * @return true if reservation is active and valid
     */
    public boolean isActive() {
        return status.equals("Active") && !hasExpired();
    }
    
    /**
     * Check if reservation has expired.
     * Reservations expire 3 days after creation.
     *
     * @return true if current date is past expiry date
     */
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

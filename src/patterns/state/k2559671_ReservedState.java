package patterns.state;

import domain.k2559671_Book;

/**
 * State Pattern: Reserved state implementation.
 * Book is reserved and waiting for pickup.
 */
public class k2559671_ReservedState implements k2559671_BookState {
    
    @Override
    public void borrow(k2559671_Book book) {
        System.out.println("✓ Reserved book '" + book.getTitle() + "' has been borrowed by the reserver.");
        book.setState(new k2559671_BorrowedState());
    }
    
    @Override
    public void returnBook(k2559671_Book book) {
        System.out.println("✓ Reservation cancelled. Book '" + book.getTitle() + "' is now available.");
        book.setState(new k2559671_AvailableState());
    }
    
    @Override
    public void reserve(k2559671_Book book) {
        System.out.println("⚠ Book '" + book.getTitle() + "' is already reserved.");
    }
    
    @Override
    public String getStateName() {
        return "Reserved";
    }
}

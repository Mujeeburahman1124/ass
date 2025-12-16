package patterns.state;

import domain.k2559671_Book;

/**
 * State Pattern: Available state implementation.
 * Book is available for borrowing or reservation.
 */
public class k2559671_AvailableState implements k2559671_BookState {
    
    @Override
    public void borrow(k2559671_Book book) {
        System.out.println("✓ Book '" + book.getTitle() + "' has been borrowed.");
        book.setState(new k2559671_BorrowedState());
    }
    
    @Override
    public void returnBook(k2559671_Book book) {
        System.out.println("⚠ Book is already available. Cannot return.");
    }
    
    @Override
    public void reserve(k2559671_Book book) {
        System.out.println("✓ Book '" + book.getTitle() + "' has been reserved.");
        book.setState(new k2559671_ReservedState());
    }
    
    @Override
    public String getStateName() {
        return "Available";
    }
}

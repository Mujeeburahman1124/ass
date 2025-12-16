package patterns.state;

import domain.k2559671_Book;

/**
 * State Pattern: Borrowed state implementation.
 * Book is currently borrowed and can be returned or reserved.
 */
public class k2559671_BorrowedState implements k2559671_BookState {
    
    @Override
    public void borrow(k2559671_Book book) {
        System.out.println("⚠ Book '" + book.getTitle() + "' is already borrowed. Cannot borrow again.");
    }
    
    @Override
    public void returnBook(k2559671_Book book) {
        System.out.println("✓ Book '" + book.getTitle() + "' has been returned.");
        book.setState(new k2559671_AvailableState());
    }
    
    @Override
    public void reserve(k2559671_Book book) {
        System.out.println("✓ Book '" + book.getTitle() + "' has been reserved (currently borrowed).");
        book.setState(new k2559671_ReservedState());
    }
    
    @Override
    public String getStateName() {
        return "Borrowed";
    }
}

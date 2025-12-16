package patterns.state;

import domain.k2559671_Book;

/**
 * State Pattern: Interface for book availability states.
 * Allows book to change behavior based on its current state.
 * 
 * Pattern Justification:
 * - Books have distinct states (Available, Borrowed, Reserved)
 * - Each state has different behavior for borrow/return/reserve operations
 * - State transitions are well-defined and need to be consistent
 */
public interface k2559671_BookState {
    void borrow(k2559671_Book book);

    void returnBook(k2559671_Book book);

    void reserve(k2559671_Book book);

    String getStateName();
}

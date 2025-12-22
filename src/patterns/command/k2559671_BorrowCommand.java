package patterns.command;

import domain.k2559671_Book;
import domain.k2559671_User;
import system.k2559671_Logger;

/**
 * Command Pattern: Borrow book command.
 * Encapsulates the action of borrowing a book.
 */
public class k2559671_BorrowCommand implements k2559671_Command {
    private final k2559671_User user;
    private final k2559671_Book book;
    private final k2559671_Logger logger;
    private boolean executed;
    
    public k2559671_BorrowCommand(k2559671_User user, k2559671_Book book, k2559671_Logger logger) {
        this.user = user;
        this.book = book;
        this.logger = logger;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        if (user.canBorrow()) {
            book.borrow();
            user.borrowBook(book);
            executed = true;
            logger.log("User " + user.getName() + " borrowed book: " + book.getTitle());
        } else {
            logger.logError("User " + user.getName() + " cannot borrow more books (limit reached)");
        }
    }
    
    @Override
    public void undo() {
        if (executed) {
            book.returnBook();
            user.returnBook(book);
            executed = false;
            logger.log("UNDO: Borrow command reversed for book: " + book.getTitle());
        }
    }
    
    @Override
    public String getDescription() {
        return "Borrow: " + book.getTitle() + " by " + user.getName();
    }
}

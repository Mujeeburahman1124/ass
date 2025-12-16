package patterns.command;

import domain.k2559671_User;
import domain.k2559671_Book;
import system.k2559671_Logger;

/**
 * Command Pattern: Reserve book command.
 * Encapsulates the action of reserving a book.
 */
public class k2559671_ReserveCommand implements k2559671_Command {
    private k2559671_User user;
    private k2559671_Book book;
    private k2559671_Logger logger;
    private boolean executed;
    
    public k2559671_ReserveCommand(k2559671_User user, k2559671_Book book, k2559671_Logger logger) {
        this.user = user;
        this.book = book;
        this.logger = logger;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        book.reserve();
        user.reserveBook(book);
        executed = true;
        logger.log("User " + user.getName() + " reserved book: " + book.getTitle());
    }
    
    @Override
    public void undo() {
        if (executed) {
            // Cancel reservation by returning to previous state
            book.returnBook();
            executed = false;
            logger.log("UNDO: Reservation cancelled for book: " + book.getTitle());
        }
    }
    
    @Override
    public String getDescription() {
        return "Reserve: " + book.getTitle() + " by " + user.getName();
    }
}

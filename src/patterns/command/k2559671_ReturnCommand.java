package patterns.command;

import domain.k2559671_User;
import domain.k2559671_Book;
import system.k2559671_Logger;

/**
 * Command Pattern: Return book command.
 * Encapsulates the action of returning a book.
 */
public class k2559671_ReturnCommand implements k2559671_Command {
    private k2559671_User user;
    private k2559671_Book book;
    private k2559671_Logger logger;
    private boolean executed;
    
    public k2559671_ReturnCommand(k2559671_User user, k2559671_Book book, k2559671_Logger logger) {
        this.user = user;
        this.book = book;
        this.logger = logger;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        book.returnBook();
        user.returnBook(book);
        executed = true;
        logger.log("User " + user.getName() + " returned book: " + book.getTitle());
    }
    
    @Override
    public void undo() {
        if (executed) {
            book.borrow();
            user.borrowBook(book);
            executed = false;
            logger.log("UNDO: Return command reversed for book: " + book.getTitle());
        }
    }
    
    @Override
    public String getDescription() {
        return "Return: " + book.getTitle() + " by " + user.getName();
    }
}

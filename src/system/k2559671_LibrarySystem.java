package system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.k2559671_Book;
import domain.k2559671_BorrowRecord;
import domain.k2559671_Fine;
import domain.k2559671_Reservation;
import domain.k2559671_User;
import patterns.builder.k2559671_BookBuilder;
import patterns.command.k2559671_Command;
import patterns.observer.k2559671_NotificationManager;

/**
 * LibrarySystem - Main controller class for the Smart Library Management System.
 * 
 * Data Structures Used:
 * - HashMap<String, k2559671_Book>: O(1) lookup for books by ID
 * - HashMap<String, k2559671_User>: O(1) lookup for users by ID
 * - ArrayList: For maintaining order and iteration of records
 * 
 * Manages all books, users, borrow records, reservations, and fines.
 * Integrates all design patterns.
 */
public class k2559671_LibrarySystem {
    // HashMap for fast O(1) lookups by ID
    private Map<String, k2559671_Book> bookMap;
    private Map<String, k2559671_User> userMap;
    
    // ArrayList for maintaining collections
    private List<k2559671_Book> books;
    private List<k2559671_User> users;
    private List<k2559671_BorrowRecord> borrowRecords;
    private List<k2559671_Reservation> reservations;
    private List<k2559671_Fine> fines;
    
    private k2559671_NotificationManager notificationManager;
    private k2559671_ReportManager reportManager;
    private k2559671_Logger logger;

    public k2559671_LibrarySystem() {
        // Initialize HashMaps for efficient lookups
        this.bookMap = new HashMap<>();
        this.userMap = new HashMap<>();
        
        // Initialize ArrayLists for collections
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.borrowRecords = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.fines = new ArrayList<>();
        
        this.notificationManager = new k2559671_NotificationManager();
        this.logger = new k2559671_Logger();
        this.reportManager = new k2559671_ReportManager(this);

        logger.log("Library System initialized with HashMap for efficient lookups");
    }

    // Book Management
    /**
     * Add a book to the library system.
     * Uses HashMap for O(1) access and ArrayList for iteration.
     * 
     * @param book Book to add
     */
    public void addBook(k2559671_Book book) {
        books.add(book);
        bookMap.put(book.getBookId(), book); // HashMap for fast lookup
        logger.log("Book added: " + book.getTitle());
    }

    /**
     * Remove a book from the library system.
     * 
     * @param bookId ID of the book to remove
     * @return true if book was removed, false otherwise
     */
    public boolean removeBook(String bookId) {
        k2559671_Book book = getBook(bookId);
        if (book != null) {
            books.remove(book);
            bookMap.remove(bookId); // Remove from HashMap
            logger.log("Book removed: " + bookId);
            return true;
        }
        return false;
    }

    public void updateBook(k2559671_Book book) {
        logger.log("Book updated: " + book.getTitle());
    }

    /**
     * Get a book by ID using HashMap for O(1) lookup.
     * This is much faster than O(n) linear search with ArrayList.
     * 
     * @param bookId Book ID to search for
     * @return Book object or null if not found
     */
    public k2559671_Book getBook(String bookId) {
        return bookMap.get(bookId); // O(1) HashMap lookup instead of O(n) stream
    }

    public List<k2559671_Book> searchBook(String query) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<k2559671_Book> getAvailableBooks() {
        return books.stream()
                .filter(b -> b.getAvailabilityStatus().equals("Available"))
                .collect(Collectors.toList());
    }

    public List<k2559671_Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // User Management
    /**
     * Add a user to the library system.
     * Uses HashMap for O(1) access by user ID.
     * 
     * @param user User to add
     */
    public void addUser(k2559671_User user) {
        users.add(user);
        userMap.put(user.getUserId(), user); // HashMap for fast lookup
        notificationManager.addObserver(user);
        logger.log("User added: " + user.getName());
    }

    /**
     * Remove a user from the library system.
     * 
     * @param userId ID of the user to remove
     * @return true if user was removed, false otherwise
     */
    public boolean removeUser(String userId) {
        k2559671_User user = getUser(userId);
        if (user != null) {
            users.remove(user);
            userMap.remove(userId); // Remove from HashMap
            notificationManager.removeObserver(user);
            logger.log("User removed: " + userId);
            return true;
        }
        return false;
    }

    /**
     * Get a user by ID using HashMap for O(1) lookup.
     * 
     * @param userId User ID to search for
     * @return User object or null if not found
     */
    public k2559671_User getUser(String userId) {
        return userMap.get(userId); // O(1) HashMap lookup
    }

    public List<k2559671_User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Command Processing
    public void processCommand(k2559671_Command command) {
        command.execute();
        logger.logCommand(command);
    }

    // Borrow Record Management
    public void addBorrowRecord(k2559671_BorrowRecord record) {
        borrowRecords.add(record);
        logger.log("Borrow record created: " + record.getRecordId());
    }

    public k2559671_BorrowRecord getBorrowRecord(String recordId) {
        return borrowRecords.stream()
                .filter(r -> r.getRecordId().equals(recordId))
                .findFirst()
                .orElse(null);
    }

    public List<k2559671_BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }

    // Reservation Management
    public void addReservation(k2559671_Reservation reservation) {
        reservations.add(reservation);
        logger.log("Reservation created: " + reservation.getReservationId());
    }

    public k2559671_Reservation getReservation(String reservationId) {
        return reservations.stream()
                .filter(r -> r.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    public List<k2559671_Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    // Fine Management
    public void addFine(k2559671_Fine fine) {
        fines.add(fine);
        logger.log("Fine created: " + fine.getFineId() + " - Amount: LKR " + fine.getAmount());
    }

    public k2559671_Fine getFine(String fineId) {
        return fines.stream()
                .filter(f -> f.getFineId().equals(fineId))
                .findFirst()
                .orElse(null);
    }

    public List<k2559671_Fine> getAllFines() {
        return new ArrayList<>(fines);
    }

    // Getters for managers
    public k2559671_NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public k2559671_ReportManager getReportManager() {
        return reportManager;
    }

    public k2559671_Logger getLogger() {
        return logger;
    }

    // Helper methods for finding by ID
    public k2559671_Book findBookById(String bookId) {
        return getBook(bookId);
    }

    public k2559671_User findUserById(String userId) {
        return getUser(userId);
    }

    // Utility method to create books using Builder
    public k2559671_Book createBookWithBuilder(k2559671_BookBuilder builder) {
        k2559671_Book book = builder.build();
        addBook(book);
        return book;
    }
}

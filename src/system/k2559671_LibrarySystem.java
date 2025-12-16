package system;

import domain.*;
import patterns.observer.k2559671_NotificationManager;
import patterns.command.k2559671_Command;
import patterns.builder.k2559671_BookBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LibrarySystem - Main controller class for the Smart Library Management
 * System.
 * Manages all books, users, borrow records, reservations, and fines.
 * Integrates all design patterns.
 */
public class k2559671_LibrarySystem {
    private List<k2559671_Book> books;
    private List<k2559671_User> users;
    private List<k2559671_BorrowRecord> borrowRecords;
    private List<k2559671_Reservation> reservations;
    private List<k2559671_Fine> fines;
    private k2559671_NotificationManager notificationManager;
    private k2559671_ReportManager reportManager;
    private k2559671_Logger logger;

    public k2559671_LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.borrowRecords = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.fines = new ArrayList<>();
        this.notificationManager = new k2559671_NotificationManager();
        this.logger = new k2559671_Logger();
        this.reportManager = new k2559671_ReportManager(this);

        logger.log("Library System initialized");
    }

    // Book Management
    public void addBook(k2559671_Book book) {
        books.add(book);
        logger.log("Book added: " + book.getTitle());
    }

    public boolean removeBook(String bookId) {
        k2559671_Book book = getBook(bookId);
        if (book != null) {
            books.remove(book);
            logger.log("Book removed: " + bookId);
            return true;
        }
        return false;
    }

    public void updateBook(k2559671_Book book) {
        logger.log("Book updated: " + book.getTitle());
    }

    public k2559671_Book getBook(String bookId) {
        return books.stream()
                .filter(b -> b.getBookId().equals(bookId))
                .findFirst()
                .orElse(null);
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
    public void addUser(k2559671_User user) {
        users.add(user);
        notificationManager.addObserver(user);
        logger.log("User added: " + user.getName());
    }

    public boolean removeUser(String userId) {
        k2559671_User user = getUser(userId);
        if (user != null) {
            users.remove(user);
            notificationManager.removeObserver(user);
            logger.log("User removed: " + userId);
            return true;
        }
        return false;
    }

    public k2559671_User getUser(String userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
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

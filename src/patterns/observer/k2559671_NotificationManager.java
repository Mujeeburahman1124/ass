package patterns.observer;

import java.util.ArrayList;
import java.util.List;

import domain.k2559671_Book;
import domain.k2559671_User;

/**
 * Observer Pattern: Notification manager (Subject).
 * Manages observers and sends notifications about library events.
 */
public class k2559671_NotificationManager {
    private final List<k2559671_Observer> observers;
    private final List<String> notifications;
    
    public k2559671_NotificationManager() {
        this.observers = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }
    
    public void addObserver(k2559671_Observer observer) {
        observers.add(observer);
        System.out.println("✓ Observer added to notification system");
    }
    
    public void removeObserver(k2559671_Observer observer) {
        observers.remove(observer);
        System.out.println("✓ Observer removed from notification system");
    }
    
    public void notifyObservers(String message) {
        notifications.add(message);
        for (k2559671_Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public void sendDueDateReminder(k2559671_User user, k2559671_Book book) {
        String message = "Reminder: Book '" + book.getTitle() + "' is due soon!";
        user.update(message);
    }
    
    public void sendOverdueAlert(k2559671_User user, k2559671_Book book) {
        String message = "⚠ OVERDUE: Book '" + book.getTitle() + "' is overdue! Please return immediately.";
        user.update(message);
    }
    
    public void sendReservationAlert(k2559671_User user, k2559671_Book book) {
        String message = "✓ Your reserved book '" + book.getTitle() + "' is now available for pickup!";
        user.update(message);
    }
    
    public void checkOverdueBooks() {
        notifyObservers("System check: Scanning for overdue books...");
    }
    
    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }
}

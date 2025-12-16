package patterns.observer;

/**
 * Observer Pattern: Observer interface for receiving notifications.
 * 
 * Pattern Justification:
 * - Multiple users need to be notified about various events
 * - Decouples notification logic from core business logic
 * - Allows dynamic subscription/unsubscription to notifications
 */
public interface k2559671_Observer {
    void update(String message);
}

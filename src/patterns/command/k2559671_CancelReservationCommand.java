package patterns.command;

import domain.k2559671_Reservation;
import system.k2559671_Logger;

/**
 * Command Pattern: Cancel reservation command.
 * Encapsulates the action of cancelling a book reservation.
 */
public class k2559671_CancelReservationCommand implements k2559671_Command {
    private final k2559671_Reservation reservation;
    private final k2559671_Logger logger;
    private boolean executed;
    private String previousStatus;
    
    public k2559671_CancelReservationCommand(k2559671_Reservation reservation, k2559671_Logger logger) {
        this.reservation = reservation;
        this.logger = logger;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        previousStatus = reservation.getStatus();
        reservation.cancel();
        executed = true;
        logger.log("Reservation #" + reservation.getReservationId() + " cancelled");
    }
    
    @Override
    public void undo() {
        if (executed) {
            reservation.setStatus(previousStatus);
            executed = false;
            logger.log("UNDO: Reservation #" + reservation.getReservationId() + " restored");
        }
    }
    
    @Override
    public String getDescription() {
        return "Cancel Reservation: #" + reservation.getReservationId();
    }
}

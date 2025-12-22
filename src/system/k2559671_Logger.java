package system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import patterns.command.k2559671_Command;

/**
 * Logger class for logging system activities and commands.
 * Maintains a history of all actions for auditing purposes.
 */
public class k2559671_Logger {
    private final List<String> logHistory;
    private final String logFile;
    private final SimpleDateFormat dateFormat;
    
    public k2559671_Logger() {
        this.logHistory = new ArrayList<>();
        this.logFile = "library_system.log";
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public void log(String message) {
        String timestamp = dateFormat.format(new Date());
        String logEntry = "[" + timestamp + "] " + message;
        logHistory.add(logEntry);
        System.out.println("📝 LOG: " + logEntry);
    }
    
    public void logCommand(k2559671_Command command) {
        log("COMMAND EXECUTED: " + command.getDescription());
    }
    
    public void logError(String error) {
        log("ERROR: " + error);
    }
    
    public List<String> getHistory() {
        return new ArrayList<>(logHistory);
    }
    
    public void clearLog() {
        logHistory.clear();
        log("Log cleared");
    }
    
    public void saveToFile() {
        log("Log saved to " + logFile);
        // In a real implementation, this would write to an actual file
    }
}

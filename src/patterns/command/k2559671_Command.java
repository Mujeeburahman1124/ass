package patterns.command;

/**
 * Command Pattern: Interface for executable commands.
 * 
 * Pattern Justification:
 * - Encapsulates user actions as objects
 * - Enables undo/redo functionality
 * - Allows logging and auditing of all actions
 * - Decouples action execution from action invocation
 */
public interface k2559671_Command {
    void execute();
    void undo();
    String getDescription();
}

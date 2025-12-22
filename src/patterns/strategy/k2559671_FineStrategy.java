package patterns.strategy;

/**
 * Strategy Pattern: Interface for fine calculation strategies.
 *
 * Pattern Justification:
 * - Different membership types have different fine calculation rules
 * - Allows easy addition of new membership types without modifying existing code
 * - Encapsulates fine calculation algorithms
 */
public interface k2559671_FineStrategy {
    double calculateFine(int daysOverdue);
}

import domain.k2559671_MembershipType;
import domain.k2559671_User;
import patterns.strategy.k2559671_FacultyFineStrategy;
import patterns.strategy.k2559671_FineStrategy;

/**
 * Test Case TC006: Fine Calculation (Faculty)
 * 
 * Input: daysOverdue = 3
 * Expected Output: Fine = LKR 60
 */
public class TestCase_TC006 {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         TEST CASE TC006 - FINE CALCULATION               ║");
        System.out.println("║          Function: Fine Calculation (Faculty)            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.out.println("--- SETUP PHASE ---\n");
        
        // Create Faculty User
        k2559671_User facultyUser = new k2559671_User(
            "U003",
            "Dr. Robert Johnson",
            "robert@university.edu",
            "555-0303",
            k2559671_MembershipType.FACULTY
        );
        
        System.out.println("✓ Faculty User Created:");
        System.out.println("  User ID: " + facultyUser.getUserId());
        System.out.println("  Name: " + facultyUser.getName());
        System.out.println("  Membership: " + facultyUser.getMembershipType());
        System.out.println("  Borrowing Limit: " + facultyUser.getBorrowLimit());
        System.out.println();
        
        // Set Faculty Fine Strategy (Strategy Pattern)
        k2559671_FineStrategy fineStrategy = new k2559671_FacultyFineStrategy();
        facultyUser.setFineStrategy(fineStrategy);
        
        System.out.println("✓ Fine Strategy Set: Faculty Fine Strategy");
        System.out.println("  Rate: LKR 20 per day");
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST EXECUTION PHASE ---\n");
        System.out.println("INPUT:");
        System.out.println("  daysOverdue = 3");
        System.out.println("  Membership Type = FACULTY");
        System.out.println("  Daily Rate = LKR 20");
        System.out.println();
        
        // Calculate fine
        int daysOverdue = 3;
        double calculatedFine = facultyUser.calculateFine(daysOverdue);
        
        System.out.println("EXECUTING: Calculate Fine...\n");
        System.out.println("  Calculation: " + daysOverdue + " days × LKR 20/day = LKR " + calculatedFine);
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
        System.out.println("--- TEST RESULT PHASE ---\n");
        
        // Verify Results
        double expectedFine = 60.0;
        
        System.out.println("ACTUAL OUTPUT:");
        System.out.println("  ✓ Fine Calculated:");
        System.out.println("    Days Overdue: " + daysOverdue);
        System.out.println("    Calculated Fine: LKR " + calculatedFine);
        System.out.println();
        System.out.println("  ✓ Strategy Pattern Applied:");
        System.out.println("    Strategy: Faculty Fine Strategy");
        System.out.println("    Rate: LKR 20 per day");
        System.out.println();
        
        // Demonstrate different strategies
        System.out.println("--- Comparison with Other Membership Types ---");
        System.out.println("For " + daysOverdue + " days overdue:");
        System.out.println("  • Student (LKR 50/day): LKR " + (daysOverdue * 50));
        System.out.println("  • Faculty (LKR 20/day): LKR " + (daysOverdue * 20));
        System.out.println("  • Guest (LKR 100/day): LKR " + (daysOverdue * 100));
        System.out.println();
        
        // Test Result
        boolean passed = (calculatedFine == expectedFine);
        
        System.out.println("═══════════════════════════════════════════════════════════");
        if (passed) {
            System.out.println("║                   TEST RESULT: ✓ PASS                    ║");
        } else {
            System.out.println("║                   TEST RESULT: ✗ FAIL                    ║");
        }
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println();
        System.out.println("EXPECTED OUTPUT: Fine = LKR " + expectedFine);
        System.out.println("ACTUAL OUTPUT: Fine = LKR " + calculatedFine);
        System.out.println("RESULT: " + (passed ? "PASS ✓" : "FAIL ✗"));
    }
}

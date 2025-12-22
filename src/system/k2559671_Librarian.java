package system;

import domain.k2559671_User;

/**
 * Librarian class represents library staff members.
 * Manages books and users in the system.
 */
public class k2559671_Librarian {
    private final String librarianId;
    private final String name;
    private final String email;

    public k2559671_Librarian(String librarianId, String name, String email) {
        this.librarianId = librarianId;
        this.name = name;
        this.email = email;
    }

    public String getLibrarianId() {
        return librarianId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean verifyMember(k2559671_User user) {
        System.out.println("Verifying member: " + user.getName());
        return user.getBorrowLimit() > 0;
    }

    public void collectFine(k2559671_User user) {
        System.out.println("Collecting fine from member: " + user.getName());
    }

    @Override
    public String toString() {
        return "Librarian: " + name + " (" + librarianId + ")";
    }
}

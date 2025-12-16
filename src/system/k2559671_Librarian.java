package system;

import domain.k2559671_Book;
import domain.k2559671_User;

/**
 * Librarian class represents library staff members.
 * Manages books and users in the system.
 */
public class k2559671_Librarian {
    private String librarianId;
    private String name;
    private String email;
    private String password;
    
    public k2559671_Librarian(String librarianId, String name, String email, String password) {
        this.librarianId = librarianId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public String getLibrarianId() {
        return librarianId;
    }
    
    public String getName() {
        return name;
    }
    
    public void addBook(k2559671_Book book) {
        System.out.println("Librarian " + name + " added book: " + book.getTitle());
    }
    
    public void updateBook(k2559671_Book book) {
        System.out.println("Librarian " + name + " updated book: " + book.getTitle());
    }
    
    public void removeBook(String bookId) {
        System.out.println("Librarian " + name + " removed book ID: " + bookId);
    }
    
    public void manageUser(k2559671_User user) {
        System.out.println("Librarian " + name + " managing user: " + user.getName());
    }
    
    public void generateReports() {
        System.out.println("Librarian " + name + " generating reports...");
    }
    
    public boolean authenticate(String inputEmail, String inputPassword) {
        return this.email.equals(inputEmail) && this.password.equals(inputPassword);
    }
    
    @Override
    public String toString() {
        return "Librarian: " + name + " (" + librarianId + ")";
    }
}

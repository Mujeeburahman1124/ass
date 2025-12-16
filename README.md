# Smart Library Management System (SLMS)

**Kingston University - CI6115 PROGRAMMING III**  
**Design Patterns & Algorithms Coursework**

## Project Overview
A comprehensive Smart Library Management System implementing 6 design patterns as per coursework requirements.

## Design Patterns Implemented
1. **Observer Pattern** - Notification system for overdue books, due dates, and reservations
2. **Strategy Pattern** - Different fine calculation strategies (Student/Faculty/Guest)
3. **State Pattern** - Book availability states (Available/Borrowed/Reserved)
4. **Command Pattern** - User actions (Borrow/Return/Reserve/Cancel) with undo capability
5. **Decorator Pattern** - Optional book features (Featured/Recommended/SpecialEdition)
6. **Builder Pattern** - Complex book object creation with metadata

## Project Structure
```
src/
├── patterns/
│   ├── observer/      - Observer pattern (notifications)
│   ├── strategy/      - Strategy pattern (fine calculation)
│   ├── state/         - State pattern (book states)
│   ├── command/       - Command pattern (actions)
│   ├── decorator/     - Decorator pattern (book features)
│   └── builder/       - Builder pattern (book creation)
├── domain/            - Core domain classes
├── system/            - Main system and utilities
└── Main.java          - Application entry point
```

## Features
- Book Management (Add, Update, Remove, Search)
- User Management (Student, Faculty, Guest membership types)
- Borrowing & Returning with due dates
- Reservation system with notifications
- Fine calculation based on membership type
- Real-time notifications for due dates and overdue books
- Comprehensive reporting system
- Command logging with undo capability

## How to Run
```bash
# Compile
javac -d bin src/**/*.java

# Run
java -cp bin Main
```

## Important Notes
⚠️ **REPLACE K0000000 with your Kingston University Student ID in ALL class names!**

## Academic Integrity
This project was developed with AI assistance for planning and implementation guidance.
All design decisions and pattern justifications are documented in the accompanying report.

## Author
[Your Name] - [Your Student ID]
Kingston University - BSc (Hons) Computing (Top-up)

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

// ================= BOOK CLASS =================
class Book {
    int bookId;
    String title;
    String author;
    boolean available;

    Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    void displayBook() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Status: " + (available ? "Available" : "Issued"));
        System.out.println("--------------------------------");
    }
}

// ================= MEMBER CLASS =================
class Member {
    int memberId;
    String name;
    String phone;

    Member(int memberId, String name, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
    }

    void displayMember() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("--------------------------------");
    }
}

// ================= ISSUE RECORD CLASS =================
class IssueRecord {
    int bookId;
    int memberId;
    LocalDate issueDate;
    LocalDate dueDate;

    IssueRecord(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = LocalDate.now();

        // Book is due after 14 days
        this.dueDate = issueDate.plusDays(14);
    }

    void displayRecord() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Member ID: " + memberId);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("--------------------------------");
    }
}

// ================= MAIN CLASS =================
public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();
    static ArrayList<IssueRecord> issueRecords = new ArrayList<>();

    // Fine per day
    static final double FINE_PER_DAY = 5.0;

    // ================= ADD BOOK =================
    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author));

        System.out.println("Book added successfully!");
    }

    // ================= ADD MEMBER =================
    static void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        members.add(new Member(id, name, phone));

        System.out.println("Member added successfully!");
    }

    // ================= DISPLAY ALL BOOKS =================
    static void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\n===== ALL BOOKS =====");

        for (Book book : books) {
            book.displayBook();
        }
    }

    // ================= DISPLAY ALL MEMBERS =================
    static void displayMembers() {

        if (members.isEmpty()) {
            System.out.println("No members available.");
            return;
        }

        System.out.println("\n===== ALL MEMBERS =====");

        for (Member member : members) {
            member.displayMember();
        }
    }

    // ================= SEARCH BOOK =================
    static void searchBook() {

        sc.nextLine();

        System.out.print("Enter book title or author to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Book book : books) {

            if (book.title.toLowerCase().contains(keyword)
                    || book.author.toLowerCase().contains(keyword)) {

                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    // ================= SEARCH MEMBER =================
    static void searchMember() {

        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();

        boolean found = false;

        for (Member member : members) {

            if (member.memberId == id) {
                member.displayMember();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Member not found.");
        }
    }

    // ================= FIND BOOK =================
    static Book findBook(int id) {

        for (Book book : books) {

            if (book.bookId == id) {
                return book;
            }
        }

        return null;
    }

    // ================= FIND MEMBER =================
    static Member findMember(int id) {

        for (Member member : members) {

            if (member.memberId == id) {
                return member;
            }
        }

        return null;
    }

    // ================= ISSUE BOOK =================
    static void issueBook() {

        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        Book book = findBook(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.available) {
            System.out.println("Book is already issued.");
            return;
        }

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        IssueRecord record = new IssueRecord(bookId, memberId);

        issueRecords.add(record);

        book.available = false;

        System.out.println("\nBook issued successfully!");
        System.out.println("Issue Date: " + record.issueDate);
        System.out.println("Due Date: " + record.dueDate);
    }

    // ================= RETURN BOOK =================
    static void returnBook() {

        System.out.print("Enter Book ID to return: ");
        int bookId = sc.nextInt();

        IssueRecord recordToRemove = null;

        for (IssueRecord record : issueRecords) {

            if (record.bookId == bookId) {

                LocalDate returnDate = LocalDate.now();

                long lateDays =
                        ChronoUnit.DAYS.between(record.dueDate, returnDate);

                double fine = 0;

                if (lateDays > 0) {
                    fine = lateDays * FINE_PER_DAY;
                }

                System.out.println("\n===== RETURN DETAILS =====");
                System.out.println("Issue Date: " + record.issueDate);
                System.out.println("Due Date: " + record.dueDate);
                System.out.println("Return Date: " + returnDate);
                System.out.println("Late Days: " + Math.max(lateDays, 0));
                System.out.println("Fine: Rs. " + fine);

                Book book = findBook(bookId);

                if (book != null) {
                    book.available = true;
                }

                recordToRemove = record;

                System.out.println("Book returned successfully!");

                break;
            }
        }

        if (recordToRemove != null) {
            issueRecords.remove(recordToRemove);
        } else {
            System.out.println("This book is not currently issued.");
        }
    }

    // ================= AVAILABLE BOOK REPORT =================
    static void availableBooksReport() {

        System.out.println("\n===== AVAILABLE BOOKS =====");

        boolean found = false;

        for (Book book : books) {

            if (book.available) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    // ================= ISSUED BOOK REPORT =================
    static void issuedBooksReport() {

        System.out.println("\n===== ISSUED BOOKS =====");

        if (issueRecords.isEmpty()) {
            System.out.println("No books are currently issued.");
            return;
        }

        for (IssueRecord record : issueRecords) {

            Book book = findBook(record.bookId);
            Member member = findMember(record.memberId);

            System.out.println("Book: " +
                    (book != null ? book.title : "Unknown"));

            System.out.println("Member: " +
                    (member != null ? member.name : "Unknown"));

            System.out.println("Issue Date: " + record.issueDate);
            System.out.println("Due Date: " + record.dueDate);

            System.out.println("--------------------------------");
        }
    }

    // ================= LIBRARY SUMMARY =================
    static void librarySummary() {

        int available = 0;
        int issued = 0;

        for (Book book : books) {

            if (book.available) {
                available++;
            } else {
                issued++;
            }
        }

        System.out.println("\n===== LIBRARY SUMMARY =====");

        System.out.println("Total Books: " + books.size());
        System.out.println("Available Books: " + available);
        System.out.println("Issued Books: " + issued);
        System.out.println("Total Members: " + members.size());
        System.out.println("Active Issues: " + issueRecords.size());
    }

    // ================= MAIN MENU =================
    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("     LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Display All Books");
            System.out.println("4. Display All Members");
            System.out.println("5. Search Book");
            System.out.println("6. Search Member");
            System.out.println("7. Issue Book");
            System.out.println("8. Return Book");
            System.out.println("9. Available Books Report");
            System.out.println("10. Issued Books Report");
            System.out.println("11. Library Summary");
            System.out.println("12. Exit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    addMember();
                    break;

                case 3:
                    displayBooks();
                    break;

                case 4:
                    displayMembers();
                    break;

                case 5:
                    searchBook();
                    break;

                case 6:
                    searchMember();
                    break;

                case 7:
                    issueBook();
                    break;

                case 8:
                    returnBook();
                    break;

                case 9:
                    availableBooksReport();
                    break;

                case 10:
                    issuedBooksReport();
                    break;

                case 11:
                    librarySummary();
                    break;

                case 12:
                    System.out.println("Thank you for using the Library System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 12);

        sc.close();
    }
}
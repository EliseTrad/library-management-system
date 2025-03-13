package main;

import java.util.*;

/**
 * Represents a book in the library system.
 * Extends the LibraryItem class to inherit common attributes and behaviors.
 */
public class Book extends LibraryItem {

    // Static variable representing the rental cost for all books
    private static double BOOK_COST = 8;

    // Instance variable representing the number of pages in the book
    private int numberOfPages;

    /**
     * Default constructor for the Book class.
     * Initializes a Book object with default values.
     */
    public Book() {
        this(0, "", "", "", 'a', "", new Date(), 10);
    }

    /**
     * Parameterized constructor for the Book class.
     * Initializes a Book object with the specified title, author, publisher, status, genre, and number of pages.
     *
     * @param title          The title of the book.
     * @param author         The author of the book.
     * @param publisher      The publisher of the book.
     * @param status         The status of the book ('a' for available, 'o' for on loan, 'r' for reserved).
     * @param genre          The genre of the book.
     * @param numberOfPages  The number of pages in the book.
     */
    public Book(String title, String author, String publisher, char status, String genre, int numberOfPages) {
        this(0, title, author, publisher, status, genre, new Date(), numberOfPages);
    }

    /**
     * Fully parameterized constructor for the Book class.
     * Initializes a Book object with the specified serial number, title, author, publisher, status, genre, date available, and number of pages.
     *
     * @param serialNumber   The serial number of the book.
     * @param title          The title of the book.
     * @param author         The author of the book.
     * @param publisher      The publisher of the book.
     * @param status         The status of the book.
     * @param genre          The genre of the book.
     * @param dateAvailable  The date the book will be available.
     * @param numberOfPages  The number of pages in the book.
     */
    public Book(long serialNumber, String title, String author, String publisher, char status, String genre,
            Date dateAvailable, int numberOfPages) {
        super(serialNumber, title, author, publisher, status, genre, dateAvailable);
        setNumberOfPages(numberOfPages);
    }

    /**
     * Sets the rental cost for all books.
     *
     * @param bookCost The new rental cost for books.
     */
    public static void setBookCost(double bookCost) {
        if (bookCost > 0)
            Book.BOOK_COST = bookCost;
        else
            Book.BOOK_COST = 8;
    }

    /**
     * Sets the number of pages in the book.
     *
     * @param numberOfPages The number of pages in the book.
     */
    public void setNumberOfPages(int numberOfPages) {
        if (numberOfPages > 0)
            this.numberOfPages = numberOfPages;
        else
            this.numberOfPages = 10;
    }

    /**
     * Returns the rental cost for books.
     *
     * @return The rental cost for books.
     */
    public static double getPrice() {
        return BOOK_COST;
    }

    /**
     * Returns the number of pages in the book.
     *
     * @return The number of pages in the book.
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string containing the book's details.
     */
    @Override
    public String toString() {
        return "Book:\n" + super.toString() + "\nnumber of pages: " + numberOfPages;
    }
}
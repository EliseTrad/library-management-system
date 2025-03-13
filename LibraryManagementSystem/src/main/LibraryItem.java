package main;

import java.util.*;
import java.time.*;

/**
 * Represents a library item in the library system.
 * This is the base class for Book and DVD.
 */
public class LibraryItem {

    // Static variable representing the general cost of a library item
    private static double GENERAL_COST = 0;

    // Attributes: serial number, title, author, publisher, status, genre, date available, and past owners
    private long serialNumber;
    private String title;
    private String author;
    private String publisher;
    private char status;
    private String genre;
    private Date dateAvailable;
    private ArrayList<Person> pastOwners;

    /**
     * Default constructor for the LibraryItem class.
     * Initializes a LibraryItem object with default values.
     */
    public LibraryItem() {
        this(0, "", "", "", 'a', "", new Date());
    }

    /**
     * Parameterized constructor for the LibraryItem class.
     * Initializes a LibraryItem object with the specified title, author, publisher, status, and genre.
     *
     * @param title     The title of the library item.
     * @param author    The author of the library item.
     * @param publisher The publisher of the library item.
     * @param status    The status of the library item ('a' for available, 'o' for on loan, 'r' for reserved).
     * @param genre     The genre of the library item.
     */
    public LibraryItem(String title, String author, String publisher, char status, String genre) {
        this(0, title, author, publisher, status, genre, new Date());
    }

    /**
     * Fully parameterized constructor for the LibraryItem class.
     * Initializes a LibraryItem object with the specified serial number, title, author, publisher, status, genre, and date available.
     *
     * @param serialNumber   The serial number of the library item.
     * @param title          The title of the library item.
     * @param author         The author of the library item.
     * @param publisher      The publisher of the library item.
     * @param status         The status of the library item.
     * @param genre          The genre of the library item.
     * @param dateAvailable  The date the library item will be available.
     */
    public LibraryItem(long serialNumber, String title, String author, String publisher, char status, String genre,
            Date dateAvailable) {
        setLibraryItem(serialNumber, title, author, publisher, status, genre, dateAvailable);
    }

    /**
     * Sets the attributes of the library item.
     *
     * @param serialNumber   The serial number of the library item.
     * @param title          The title of the library item.
     * @param author         The author of the library item.
     * @param publisher      The publisher of the library item.
     * @param status         The status of the library item.
     * @param genre          The genre of the library item.
     * @param dateAvailable  The date the library item will be available.
     */
    public void setLibraryItem(long serialNumber, String title, String author, String publisher, char status,
            String genre, Date dateAvailable) {
        setSerialNumber(serialNumber);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setStatus(status);
        setGenre(genre);
        this.dateAvailable = dateAvailable;
        pastOwners = new ArrayList();
    }

    /**
     * Sets the general cost of a library item.
     *
     * @param generalCost The new general cost of a library item.
     */
    public static void setPrice(double generalCost) {
        LibraryItem.GENERAL_COST = generalCost;
    }

    /**
     * Sets the serial number of the library item.
     * If the serial number is invalid, it generates a new one automatically.
     *
     * @param serialNumber The serial number of the library item.
     */
    private void setSerialNumber(long serialNumber) {
        if (serialNumber > 0)
            this.serialNumber = serialNumber;
        else
            setSerialNumberAutomatically();
    }

    /**
     * Sets the title of the library item.
     *
     * @param title The title of the library item.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the author of the library item.
     *
     * @param author The author of the library item.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the publisher of the library item.
     *
     * @param publisher The publisher of the library item.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Sets the status of the library item.
     * Valid statuses are 'a' (available), 'o' (on loan), and 'r' (reserved).
     *
     * @param status The status of the library item.
     */
    public void setStatus(char status) {
        if (status == 'a' || status == 'o' || status == 'r')
            this.status = status;
        else
            this.status = 'a';
    }

    /**
     * Sets the genre of the library item.
     *
     * @param genre The genre of the library item.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the registration date of the library item.
     * If the item is on loan, it sets the availability date to 3 months from the current date.
     */
    public void setRegistration() {
        if (this.status == 'o') {
            int currentYear = dateAvailable.getYear();
            int currentMonth = dateAvailable.getMonth();
            int newMonth = (currentMonth + 3) % 12;
            int newYear = currentYear + (currentMonth + 3) / 12;
            dateAvailable.setMonth(newMonth);
            dateAvailable.setYear(newYear);
        } else
            dateAvailable = new Date();
    }

    /**
     * Automatically generates a serial number for the library item based on the current date and time.
     */
    private void setSerialNumberAutomatically() {
        Date date = new Date();
        String theDate = new Date().toString();
        String year = theDate.substring(theDate.length() - 1);
        int month = date.getMonth() + 1;
        String day = theDate.substring(8, 10);
        String hour = theDate.substring(11, 13);
        String minute = theDate.substring(14, 16);
        String second = theDate.substring(17, 19);
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        if (month < 10)
            builder.append("0");
        builder.append(month).append(day).append(hour).append(minute).append(second);
        this.serialNumber = Long.parseLong(builder.toString());
    }

    /**
     * Returns the number of days remaining until the library item is available.
     *
     * @return The number of days remaining.
     */
    public int getTimeRemainingDays() {
        Date currentDate = new Date();
        long timeDiffInMs = dateAvailable.getTime() - currentDate.getTime();
        long daysRemaining = timeDiffInMs / 86400000;
        return (int) daysRemaining;
    }

    /**
     * Returns the general cost of a library item.
     *
     * @return The general cost of a library item.
     */
    public static double getPrice() {
        return GENERAL_COST;
    }

    /**
     * Returns the serial number of the library item.
     *
     * @return The serial number of the library item.
     */
    public long getSerialNumber() {
        return serialNumber;
    }

    /**
     * Returns the title of the library item.
     *
     * @return The title of the library item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the library item.
     *
     * @return The author of the library item.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the publisher of the library item.
     *
     * @return The publisher of the library item.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Returns the status of the library item.
     *
     * @return The status of the library item.
     */
    public char getStatus() {
        return status;
    }

    /**
     * Returns the genre of the library item.
     *
     * @return The genre of the library item.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns the availability date of the library item.
     *
     * @return The availability date of the library item.
     */
    public Date getDateAvailable() {
        return dateAvailable;
    }

    /**
     * Returns the list of past owners of the library item.
     *
     * @return The list of past owners.
     */
    public ArrayList<Person> getPastOwners() {
        return pastOwners;
    }

    /**
     * Returns a string representation of the LibraryItem object.
     *
     * @return A string containing the library item's details.
     */
    @Override
    public String toString() {
        return "SN: " + serialNumber + "\n" + title + " [" + genre + "]\nby " + author + " published by " + publisher
                + "\nis" +
                ((status == 'o') ? (" on loan, available on " + dateAvailable.toString())
                        : ((status == 'a') ? " available" : " a reference item"));
    }
}
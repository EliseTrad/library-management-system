package main;

import java.util.*;

/**
 * Represents a DVD in the library system.
 * Extends the LibraryItem class to inherit common attributes and behaviors.
 */
public class DVD extends LibraryItem {

    // Static variable representing the rental cost for all DVDs
    private static double DVD_COST = 5;

    // Instance variable representing the size of the DVD in MB
    private double sizeInMB;

    /**
     * Default constructor for the DVD class.
     * Initializes a DVD object with default values.
     */
    public DVD() {
        this(0, "", "", "", 'a', "", new Date(), 10);
    }

    /**
     * Parameterized constructor for the DVD class.
     * Initializes a DVD object with the specified title, director, producer, status, genre, and size in MB.
     *
     * @param title     The title of the DVD.
     * @param director  The director of the DVD.
     * @param producer  The producer of the DVD.
     * @param status    The status of the DVD ('a' for available, 'o' for on loan, 'r' for reserved).
     * @param genre     The genre of the DVD.
     * @param sizeInMB  The size of the DVD in MB.
     */
    public DVD(String title, String director, String producer, char status, String genre, double sizeInMB) {
        this(0, title, director, producer, status, genre, new Date(), sizeInMB);
    }

    /**
     * Fully parameterized constructor for the DVD class.
     * Initializes a DVD object with the specified serial number, title, director, producer, status, genre, date available, and size in MB.
     *
     * @param serialNumber   The serial number of the DVD.
     * @param title          The title of the DVD.
     * @param director       The director of the DVD.
     * @param producer       The producer of the DVD.
     * @param status         The status of the DVD.
     * @param genre          The genre of the DVD.
     * @param dateAvailable  The date the DVD will be available.
     * @param sizeInMB       The size of the DVD in MB.
     */
    public DVD(long serialNumber, String title, String director, String producer, char status, String genre,
            Date dateAvailable, double sizeInMB) {
        super(serialNumber, title, director, producer, status, genre, dateAvailable);
        setSizeInMB(sizeInMB);
    }

    /**
     * Sets the rental cost for all DVDs.
     *
     * @param DVD_COST The new rental cost for DVDs.
     */
    public static void setPrice(double DVD_COST) {
        DVD.DVD_COST = DVD_COST;
    }

    /**
     * Sets the size of the DVD in MB.
     *
     * @param sizeInMB The size of the DVD in MB.
     */
    public void setSizeInMB(double sizeInMB) {
        this.sizeInMB = sizeInMB;
    }

    /**
     * Returns the rental cost for DVDs.
     *
     * @return The rental cost for DVDs.
     */
    public static double getPrice() {
        return DVD_COST;
    }

    /**
     * Returns the size of the DVD in MB.
     *
     * @return The size of the DVD in MB.
     */
    public double getSizeInMB() {
        return sizeInMB;
    }

    /**
     * Returns a string representation of the DVD object.
     *
     * @return A string containing the DVD's details.
     */
    @Override
    public String toString() {
        return "DVD:\nSN: " + getSerialNumber() + "\n" + getTitle() + " [" + getGenre() + "]" + "directed by "
                + getAuthor() + "produced by " + getPublisher() + "\nMedia is"
                + ((getStatus() == 'o') ? (" on loan, available on " + getDateAvailable().toString())
                        : ((getStatus() == 'a') ? " available" : " a reference item"))
                + "\nDVD size: " + sizeInMB + "MB";
    }
}
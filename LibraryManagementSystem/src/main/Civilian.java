package main;

/**
 * Represents a civilian in the library system.
 * Extends the Person class to inherit common attributes and behaviors.
 */
public class Civilian extends Person {

    // Attributes: ID and current balance
    private String id;
    private double currentBal;

    /**
     * Parameterized constructor for the Civilian class.
     * Initializes a Civilian object with the specified name, address, gender, age, phone number, ID, and current balance.
     *
     * @param name        The name of the civilian.
     * @param address     The address of the civilian.
     * @param gender      The gender of the civilian.
     * @param age         The age of the civilian.
     * @param phoneNumber The phone number of the civilian.
     * @param id          The ID of the civilian.
     * @param currentBal  The current balance of the civilian.
     */
    public Civilian(String name, String address, char gender, int age, String phoneNumber, String id,
            double currentBal) {
        super(name, address, gender, age, phoneNumber);
        this.id = id;
        setCurrentBal(currentBal);
    }

    /**
     * Default constructor for the Civilian class.
     * Initializes a Civilian object with default values.
     */
    public Civilian() {
        this("unkown", "unknown", 'm', 18, "00-000000", "unknown", 50);
    }

    /**
     * Sets the current balance of the civilian.
     * Ensures the balance is not negative.
     *
     * @param currentBal The current balance of the civilian.
     */
    private void setCurrentBal(double currentBal) {
        if (currentBal > 0)
            this.currentBal = currentBal;
        else
            this.currentBal = 50;
    }

    /**
     * Adds credit to the civilian's balance.
     *
     * @param amount The amount to add to the balance.
     */
    public void addCredit(double amount) {
        if (amount > 0)
            setCurrentBal(currentBal + amount);
    }

    /**
     * Returns the current balance of the civilian.
     *
     * @return The current balance of the civilian.
     */
    public double getCurrentBal() {
        return currentBal;
    }

    /**
     * Returns the ID of the civilian.
     *
     * @return The ID of the civilian.
     */
    public String getId() {
        return id;
    }

    /**
     * Allows a civilian to borrow a library item.
     *
     * @param item The library item to borrow.
     * @return True if the item was borrowed successfully, false otherwise.
     */
    public boolean borrowItem(LibraryItem item) {
        if (item.getPrice() <= currentBal && item.getStatus() == 'a') {
            currentBal -= item.getPrice();
            item.setStatus('o');
            item.setRegistration();
            item.getPastOwners().add(this);
            getBorrowedItem().add(item);
            return true;
        }
        return false;
    }

    /**
     * Allows a civilian to return a library item.
     *
     * @param item The library item to return.
     * @return True if the item was returned successfully, false otherwise.
     */
    public boolean returnItem(LibraryItem item) {
        if (getBorrowedItem().contains(item)) {
            item.setStatus('a');
            item.setRegistration();
            getBorrowedItem().remove(item);
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the Civilian object.
     *
     * @return A string containing the civilian's details.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n" + super.toString() + "\ncurrent Balance: " + currentBal
                + "\nnumber of borrowed items: " + super.getBorrowedItem().size();
    }
}
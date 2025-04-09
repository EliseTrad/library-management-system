package main;

import java.util.ArrayList;

/**
 * Represents a person in the library system.
 * This class serves as the base class for Student and Civilian.
 */
public class Person {

    // Attributes: name, gender, age, address, phone number, and list of borrowed items
    private String name, address;
    char gender;
    private int age;
    String phoneNumber;
    private ArrayList<LibraryItem> borrowedItem;

    /**
     * Parameterized constructor for the Person class.
     * Initializes a Person object with the specified name, address, gender, age, and phone number.
     *
     * @param name        The name of the person.
     * @param address     The address of the person.
     * @param gender      The gender of the person ('M' for male, 'F' for female).
     * @param age         The age of the person.
     * @param phoneNumber The phone number of the person.
     */
    public Person(String name, String address, char gender, int age, String phoneNumber) {
        this.name = name;
        this.address = address;
        setGender(gender);
        setAge(age);
        setPhoneNumber(phoneNumber);
        borrowedItem = new ArrayList<>();
    }

    /**
     * Default constructor for the Person class.
     * Initializes a Person object with default values.
     */
    public Person() {
        this("unknown", "unknown", 'm', 0, "00-000000");
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    /**
     * Sets the gender of the person.
     *
     * @param gender The gender of the person ('M' for male, 'F' for female).
     */
    public void setGender(char gender) {
        if (gender == 'f' || gender == 'F')
            this.gender = 'F';
        else
            this.gender = 'M';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the person.
     * Ensures the age is at least 18.
     *
     * @param age The age of the person.
     */
    public void setAge(int age) {
        if (age >= 18)
            this.age = age;
        else
            this.age = 18;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     * Validates the phone number format before setting it.
     *
     * @param phoneNumber The phone number of the person.
     */
    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        if (checkValidNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = "00-000000";
    }

    /**
     * Checks if the phone number is valid.
     * Valid formats: 9 digits (e.g., 12-345678) or 14 digits (e.g., +961-70-123456).
     *
     * @param phoneNb The phone number to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    public static boolean checkValidNumber(String phoneNb) {
        if (phoneNb.length() == 9) {
            for (int i = 0; i < 9; i++) {
                if (i == 2) {
                    if (phoneNb.charAt(i) != '-')
                        return false;
                } else if (!Character.isDigit(phoneNb.charAt(i)))
                    return false;
            }
            return true;
        } else if (phoneNb.length() == 14) {
            for (int i = 0; i < 14; i++) {
                if (i == 0) {
                    if (phoneNb.charAt(i) != '+')
                        return false;
                } else if (i == 4 || i == 7) {
                    if (phoneNb.charAt(i) != '-')
                        return false;
                } else {
                    if (!Character.isDigit(phoneNb.charAt(i)))
                        return false;
                }
            }
            return true;
        }

        return false;
    }

    public ArrayList<LibraryItem> getBorrowedItem() {
        return borrowedItem;
    }

    /**
     * Returns a string representation of the Person object.
     *
     * @return A string containing the person's details.
     */
    @Override
    public String toString() {
        return name + ", " + age + " " + gender + ", residence:" + address + "\nphone number: " + phoneNumber;
    }
}
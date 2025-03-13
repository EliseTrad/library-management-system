package main;

/**
 * Represents a student in the library system.
 * Extends the Person class to inherit common attributes and behaviors.
 */
public class Student extends Person {

    // Attributes: student ID and maximum number of items a student can borrow
    private String studentId;
    private static int maxNumberToBorrow = 3;

    /**
     * Parameterized constructor for the Student class.
     * Initializes a Student object with the specified name, address, gender, age, phone number, and student ID.
     *
     * @param name        The name of the student.
     * @param address     The address of the student.
     * @param gender      The gender of the student.
     * @param age         The age of the student.
     * @param phoneNumber The phone number of the student.
     * @param studentId   The student ID.
     */
    public Student(String name, String address, char gender, int age, String phoneNumber, String studentId) {
        super(name, address, gender, age, phoneNumber);
        this.studentId = studentId;
    }

    /**
     * Default constructor for the Student class.
     * Initializes a Student object with default values.
     */
    public Student() {
        this("unkown", "unknown", 'm', 18, "00-000000", "unknown");
    }

    /**
     * Allows a student to borrow a library item.
     *
     * @param item The library item to borrow.
     * @return True if the item was borrowed successfully, false otherwise.
     */
    public boolean borrowItem(LibraryItem item) {
        if (getBorrowedItem().size() < maxNumberToBorrow && item.getStatus() == 'a') {
            item.setStatus('o');
            item.setRegistration();
            item.getPastOwners().add(this);
            getBorrowedItem().add(item);
            return true;
        }
        return false;
    }

    /**
     * Allows a student to return a library item.
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

    public String getStudentId() {
        return studentId;
    }

    public static int getMaxNumberToBorrow() {
        return maxNumberToBorrow;
    }

    /**
     * Sets the maximum number of items a student can borrow.
     *
     * @param maxNumberToBorrow The maximum number of items a student can borrow.
     */
    public static void setMaxNumberToBorrow(int maxNumberToBorrow) {
        Student.maxNumberToBorrow = maxNumberToBorrow;
    }

    /**
     * Returns a string representation of the Student object.
     *
     * @return A string containing the student's details.
     */
    @Override
    public String toString() {
        return "Student ID: " + studentId + "\n" + super.toString() + "\nnumber of borrowed items: "
                + super.getBorrowedItem().size();
    }
}
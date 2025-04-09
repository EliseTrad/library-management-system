package main;

import java.util.*;
import java.io.*;

/**
 * The main class for the library management system.
 * This class handles the user interface and program flow.
 */
public class Main {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<LibraryItem> myItems = new ArrayList<>();
        ArrayList<Person> myMembers = new ArrayList<>();

        try {
            LoadFromFiles(myMembers, myItems);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("\n*******************************");
            int choice = getChoice();
            switch (choice) {
                case 1:
                    System.out.println("Enter the new item information:"
                                    + "\n---------------------------------");
                    addLibraryItem(myItems);
                    break;
                case 2:
                    System.out.println("Modifying an item.\nEnter the id of"
                                    + " the item you want to modify: ");
                    long idToModify = input.nextLong();
                    modifyItem(myItems, idToModify);
                    break;
                case 3:
                    System.out.println("Deleting an item.\nEnter the id of the item"
                                    + " you want to delete: ");
                    long idToDelete = input.nextLong();
                    deleteItem(myItems, idToDelete);
                    break;
                case 4:
                    System.out.println("Adding a new member:");
                    addMember(myMembers);
                    break;
                case 5:
                    System.out.println("Modifying a member:\nEnter the member's" 
                                    + " id you want to modify:");
                    String memberIdToModify = input.next();
                    modifyMember(myMembers, memberIdToModify);
                    break;
                case 6:
                    System.out.println("Deleting member info:\nEnter the member" 
                                    + "id you want to delete:");
                    String memberIdToDelete = input.next();
                    deleteMember(myMembers, memberIdToDelete);
                    break;
                case 7:
                    System.out.println("Search an item:\nEnter item's id:");
                    long searchId = input.nextLong();
                    if (searchItemBySerialNb(searchId, myItems) != null)
                        System.out.println(searchItemBySerialNb(searchId, myItems).toString());
                    else
                        System.out.println("Item not found!");
                    break;
                case 8:
                    System.out.println("Search a member:\nEnter member's id:");
                    String searchMemberId = input.next();
                    if (searchMemberById(searchMemberId, myMembers) != null)
                        System.out.println(searchMemberById(searchMemberId, myMembers).toString());
                    else
                        System.out.println("Member not found!");
                    break;
                case 9:
                    System.out.println("Borrow item:\nEnter the item title or serial number: ");
                    String titleOrId = input.next();
                    System.out.println("Enter the id or the name of a member : ");
                    String nameOrId = input.next();
                    borrowItem(myItems, titleOrId, myMembers, nameOrId);
                    break;
                case 10:
                    System.out.println("Return item:\nEnter the title or serial number: ");
                    String titleOrSerial = input.next();
                    returnItem(titleOrSerial, myItems);
                    break;
                case 11:
                    System.out.println("Displaying all items:\n-------------------------------\n");
                    displayItems(myItems);
                    break;
                case 12:
                    System.out.println("Displaying all members:\n-------------------------------\n");
                    displayMembers(myMembers);
                    break;

            }

            if (choice == 13) {
                try {
                    saveAllToFiles(myMembers, myItems);
                } catch (Exception e) {
                    System.out.println("error in save");
                }
                break;
            }
        }
    }

    /**
     * Saves all members to a file.
     *
     * @param members  The list of members to save.
     * @param filePath The path of the file to save the members to.
     * @throws FileNotFoundException If the file is not found.
     */
    public static void saveAllMembers(ArrayList<Person> members, String filePath)
            throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists())
            return;

        PrintWriter write = new PrintWriter(myFile);
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) instanceof Student) {
                write.print("S&" + members.get(i).getName() + "&" + members.get(i).getAddress() + "&" +
                        members.get(i).getGender() + "&" + members.get(i).getPhoneNumber() + "&" +
                        ((Student) members.get(i)).getStudentId() + "&");
                for (int j = 0; j < members.get(i).getBorrowedItem().size(); j++) {
                    write.print(members.get(i).getBorrowedItem().get(j).getSerialNumber() + "##");
                }
                write.print("\n");

            } else {
                write.print("C&" + members.get(i).getName() + "&" + members.get(i).getAddress() + "&" +
                        members.get(i).getGender() + "&" + members.get(i).getPhoneNumber() + "&" +
                        ((Civilian) members.get(i)).getId() + "&" + ((Civilian) members.get(i))
                                .getCurrentBal()
                        + "&");
                for (int j = 0; j < members.get(i).getBorrowedItem().size(); j++) {
                    write.print(members.get(i).getBorrowedItem().get(j).getSerialNumber() + "##");
                }
                write.print("\n");

            }
        }
        write.close();
    }

    /**
     * Saves all library items to a file.
     *
     * @param items    The list of items to save.
     * @param filePath The path of the file to save the items to.
     * @throws FileNotFoundException If the file is not found.
     */
    public static void saveAllItems(ArrayList<LibraryItem> items, String filePath)
            throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists())
            return;

        PrintWriter write = new PrintWriter(myFile);
        for (int i = 0; i < items.size(); i++) {

            write.print(((items.get(i) instanceof DVD) ? "D#" : "B#") +
                    items.get(i).getSerialNumber() + "#" + items.get(i).getTitle() +
                    "#" + items.get(i).getAuthor() + "#" + items.get(i).getPublisher() +
                    "#" + items.get(i).getStatus() + "#" + items.get(i).getGenre() +
                    "#" + Long.parseLong(items.get(i).getDateAvailable().toString()) +
                    "#" + ((items.get(i) instanceof DVD) ? ((DVD) items.get(i)).getSizeInMB()
                            : ((Book) items.get(i)).getNumberOfPages())
                    + "#");
            for (int j = 0; j < items.get(i).getPastOwners().size(); j++) {
                write.print((items.get(i).getPastOwners().get(j) instanceof Student)
                        ? ((Student) items.get(i).getPastOwners().get(j)).getStudentId()
                        : ((Civilian) items.get(i).getPastOwners().get(j)).getId() + "&&");
            }

            write.print("\n");
        }
        write.close();
    }

    /**
     * Saves all members and items to their respective files.
     *
     * @param members The list of members to save.
     * @param items   The list of items to save.
     * @throws FileNotFoundException If the files are not found.
     */
    public static void saveAllToFiles(ArrayList<Person> members, ArrayList<LibraryItem> items)
            throws FileNotFoundException {
        saveAllItems(items, 
            "C:\\dev\\LibraryManagementSystem\\LibraryManagementSystem\\data\\items.txt");
        saveAllMembers(members,
            "C:\\dev\\LibraryManagementSystem\\LibraryManagementSystem\\data\\members.txt");
    }

    /**
     * Loads members and items from their respective files.
     *
     * @param members The list to store loaded members.
     * @param items   The list to store loaded items.
     * @throws FileNotFoundException If the files are not found.
     */
    public static void LoadFromFiles(ArrayList<Person> members, ArrayList<LibraryItem> items)
            throws FileNotFoundException {
        ArrayList<String> borrowed = new ArrayList<>();
        ArrayList<String> owners = new ArrayList<>();
        loadAllmembers(members, borrowed, 
            "C:\\dev\\LibraryManagementSystem\\LibraryManagementSystem\\data\\items.txt");
        loadAllItems(items, owners, 
            "C:\\dev\\LibraryManagementSystem\\LibraryManagementSystem\\data\\members.txt");
        adjustOwners(members, items, owners);
        adjustBorrowed(members, items, borrowed);
    }

    /**
     * Adjusts the borrowed items for each member based on the loaded data.
     *
     * @param members  The list of members.
     * @param items    The list of items.
     * @param borrowed The list of borrowed item serial numbers.
     */
    public static void adjustBorrowed(ArrayList<Person> members, ArrayList<LibraryItem> items,
            ArrayList<String> borrowed) {
        for (int i = 0; i < members.size(); i++) {
            if (borrowed.get(i) != null) {
                String[] itemsBorrowed = borrowed.get(i).split("##");
                for (String serial : itemsBorrowed) {
                    if (serial != null) {
                        LibraryItem item = searchItemBySerialNb(Long.parseLong(serial), items);
                        if (item != null)
                            members.get(i).getBorrowedItem().add(item);
                    }
                }
            }
        }
    }

    /**
     * Adjusts the past owners for each item based on the loaded data.
     *
     * @param members The list of members.
     * @param items   The list of items.
     * @param owners  The list of past owner IDs.
     */
    public static void adjustOwners(ArrayList<Person> members, ArrayList<LibraryItem> items,
            ArrayList<String> owners) {
        for (int i = 0; i < items.size(); i++) {
            if (owners.get(i) != null) {
                String[] oldOwners = owners.get(i).split("&&");
                for (String id : oldOwners) {
                    if (id != null) {
                        Person member = searchMemberById(id, members);
                        if (member != null)
                            items.get(i).getPastOwners().add(member);
                    }
                }
            }
        }
    }

    /**
     * Loads all members from a file.
     *
     * @param members  The list to store loaded members.
     * @param borrowed The list to store borrowed item serial numbers.
     * @param filePath The path of the file to load members from.
     * @throws FileNotFoundException If the file is not found.
     */
    public static void loadAllmembers(ArrayList<Person> members, ArrayList<String> borrowed, 
                                    String filePath) throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists())
            return;

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("&");
            if (tokens[0].equals("C")) {
                members.add(new Civilian(tokens[1], tokens[2], tokens[3].charAt(0),
                        Integer.parseInt(tokens[4]),
                        tokens[5], tokens[6],
                        Double.parseDouble(tokens[7])));
                borrowed.add(tokens.length == 9 ? tokens[tokens.length - 1] : null);
            } else {
                members.add(new Student(tokens[1], tokens[2], tokens[3].charAt(0), Integer
                        .parseInt(tokens[4]),
                        tokens[5], tokens[6]));
                borrowed.add(tokens.length == 8 ? tokens[tokens.length - 1] : null);
            }
        }
    }

    /**
     * Loads all items from a file.
     *
     * @param items    The list to store loaded items.
     * @param owners   The list to store past owner IDs.
     * @param filePath The path of the file to load items from.
     * @throws FileNotFoundException If the file is not found.
     */
    public static void loadAllItems(ArrayList<LibraryItem> items, ArrayList<String> owners, String filePath)
            throws FileNotFoundException {
        File myFile = new File(filePath);
        if (!myFile.exists())
            return;

        Scanner reader = new Scanner(myFile);
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] tokens = line.split("#");
            if (tokens[0].equals("D"))
                items.add(new DVD(Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4], tokens[5]
                        .charAt(0),
                        tokens[6], new Date(Long.parseLong(tokens[7])), Double.parseDouble(tokens[8])));
            else
                items.add(new Book(Long.parseLong(tokens[1]), tokens[2], tokens[3], tokens[4], tokens[5]
                        .charAt(0),
                        tokens[6], new Date(Long.parseLong(tokens[7])), Integer.parseInt(tokens[8])));

            if (tokens.length == 10)
                owners.add(tokens[9]);
            else
                owners.add(null);
        }
    }

    /**
     * Searches for a library item by its serial number.
     *
     * @param serialNb The serial number of the item to search for.
     * @param items    The list of items to search in.
     * @return The found item, or null if not found.
     */
    public static LibraryItem searchItemBySerialNb(long serialNb, ArrayList<LibraryItem> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getSerialNumber() == serialNb)
                return items.get(i);
        }
        return null;
    }

    /**
     * Searches for a member by their ID.
     *
     * @param id      The ID of the member to search for.
     * @param members The list of members to search in.
     * @return The found member, or null if not found.
     */
    public static Person searchMemberById(String id, ArrayList<Person> members) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) instanceof Student) {
                if (((Student) members.get(i)).getStudentId().equals(id))
                    return members.get(i);
            } else if (members.get(i) instanceof Civilian) {
                if (((Civilian) members.get(i)).getId().equals(id))
                    return members.get(i);
            }
        }
        return null;
    }

    /**
     * Searches for a library item by its title.
     *
     * @param title The title of the item to search for.
     * @param items The list of items to search in.
     * @return The found item, or null if not found.
     */
    public static LibraryItem searchItemByTitle(String title, ArrayList<LibraryItem> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equals(title))
                return items.get(i);
        }
        return null;
    }

    /**
     * Searches for a member by their name or ID.
     *
     * @param nameOrId The name or ID of the member to search for.
     * @param members  The list of members to search in.
     * @return The found member, or null if not found.
     */
    public static Person searchMemberByNameOrId(String nameOrId, ArrayList<Person> members) {
        if (searchMemberById(nameOrId, members) != null)
            return searchMemberById(nameOrId, members);
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) instanceof Student) {
                if (((Student) members.get(i)).getName().equals(nameOrId))
                    return members.get(i);
            } else if (members.get(i) instanceof Civilian) {
                if (((Civilian) members.get(i)).getName().equals(nameOrId))
                    return members.get(i);
            }
        }
        return null;
    }

    /**
     * Adds a new library item to the system.
     *
     * @param items The list of items to add to.
     */
    public static void addLibraryItem(ArrayList<LibraryItem> items) {
        System.out.println("What is the type of the new item [B] Book or [D] DVD?");

        char answer1 = input.next().charAt(0);
        input.nextLine();

        switch (answer1) {
            case 'D':
            case 'd':
                System.out.println("Enter the DVD's title : ");
                String dvdTitle = input.nextLine();

                System.out.println("Enter the director name : ");
                String dvdDirector = input.nextLine();

                System.out.println("Enter the producer name : ");
                String dvdProducer = input.nextLine();

                System.out.println("Enter the status [a] for available or [r] for reference : ");
                char dvdStatus = input.next().charAt(0);
                input.nextLine();

                System.out.println("Enter the DVD's genre : ");
                String dvdGenre = input.nextLine();

                System.out.println("Enter the DVD's size in MB : ");
                double dvdSize = input.nextDouble();
                input.nextLine();

                items.add(new DVD(dvdTitle, dvdDirector, dvdProducer,
                        dvdStatus, dvdGenre, dvdSize));

                System.out.println("New DVD added correctly.\n\n*******************************\n\n");

                break;

            case 'B':
            case 'b':

                System.out.println("Enter the Book title : ");
                String bookTitle = input.nextLine();

                System.out.println("Enter the author name : ");
                String bookAuthor = input.nextLine();

                System.out.println("Enter the publisher : ");
                String bookPublisher = input.nextLine();

                System.out.println("Enter the status [a] for available or [r] for reference : ");
                char bookStatus = input.next().charAt(0);
                input.nextLine();

                System.out.println("Enter the book's genre : ");
                String bookGenre = input.nextLine();

                System.out.println("Enter the number of pages : ");
                int bookNumberOfPages = input.nextInt();
                input.nextLine();

                items.add(new Book(bookTitle, bookAuthor, bookPublisher, bookStatus,
                        bookGenre, bookNumberOfPages));

                System.out.println("New Book added correctly.\n\n*******************************\n\n");

                break;

            default:
                System.out.println("Invalid choice!");
        }
    }

    /**
     * Modifies an existing library item.
     *
     * @param items      The list of items to modify.
     * @param idToModify The serial number of the item to modify.
     */
    public static void modifyItem(ArrayList<LibraryItem> items, long idToModify) {
        LibraryItem itemToModify = searchItemBySerialNb(idToModify, items);

        if (itemToModify != null) {
            System.out.println("What do you want to modify:" +
                    "\n1- Title" +
                    "\n2- Author" +
                    "\n3- Publisher" +
                    "\n4- Status" +
                    "\n5- Genre");

            if (itemToModify instanceof DVD)
                System.out.println("\n6- Size in MB");
            else
                System.out.println("\n6- Number of pages");

            int choice = input.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Enter new title:");
                    String newTitle = input.nextLine();

                    itemToModify.setTitle(newTitle);
                    break;

                case 2:
                    System.out.println("Enter new author:");
                    String newAuthor = input.nextLine();

                    itemToModify.setAuthor(newAuthor);
                    break;

                case 3:
                    System.out.println("Enter new publisher:");
                    String newPublisher = input.nextLine();

                    itemToModify.setPublisher(newPublisher);
                    break;

                case 4:
                    System.out.println("Enter new status:");
                    char newStatus = input.next().charAt(0);
                    input.nextLine();
                    itemToModify.setStatus(newStatus);
                    break;

                case 5:
                    System.out.println("Enter new genre:");
                    String newGenre = input.nextLine();

                    itemToModify.setGenre(newGenre);
                    break;

                case 6:
                    if (itemToModify instanceof DVD) {
                        System.out.println("Enter new size in MB:");
                        double newSize = input.nextDouble();
                        input.nextLine();

                        ((DVD) itemToModify).setSizeInMB(newSize);
                        break;
                    } else {
                        System.out.println("Enter new number of pages:");
                        int newNumberOfPages = input.nextInt();

                        ((Book) itemToModify).setNumberOfPages(newNumberOfPages);
                        break;
                    }
            }
        }
    }

    /**
     * Modifies an existing member's information.
     *
     * @param members The list of members to modify.
     * @param id      The ID of the member to modify.
     */
    public static void modifyMember(ArrayList<Person> members, String id) {
        if (searchMemberById(id, members) != null) {
            Person memberToModify = searchMemberById(id, members);
            System.out.println("What do you want to modify:" +
                    "\n1- Name" +
                    "\n2- Gender" +
                    "\n3- Age" +
                    "\n4- Address" +
                    "\n5- Phone number");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter new name: ");
                    String newMemberName = input.nextLine();
                    memberToModify.setName(newMemberName);
                    break;
                case 2:
                    System.out.println("Enter new gender: ");
                    char newGender = input.next().charAt(0);
                    input.nextLine();
                    memberToModify.setGender(newGender);
                    break;
                case 3:
                    System.out.println("Enter new age:");
                    int newAge = input.nextInt();
                    input.nextLine();
                    memberToModify.setAge(newAge);
                    break;

                case 4:
                    System.out.println("Enter new address:");
                    String newAdress = input.nextLine();

                    memberToModify.setAddress(newAdress);
                    break;

                case 5:
                    System.out.println("Enter new phone number:");
                    String newPhoneNumber = input.next();
                    memberToModify.setPhoneNumber(newPhoneNumber);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

    }

    /**
     * Deletes a library item from the system.
     *
     * @param items      The list of items to delete from.
     * @param idToDelete The serial number of the item to delete.
     */
    public static void deleteItem(ArrayList<LibraryItem> items, long idToDelete) {
        LibraryItem itemToDelete = searchItemBySerialNb(idToDelete, items);
        if (itemToDelete != null) {
            items.remove(itemToDelete);
            System.out.println("Item deleted correctly!");
        }
    }

    /**
     * Adds a new member to the system.
     *
     * @param members The list of members to add to.
     */
    public static void addMember(ArrayList<Person> members) {
        System.out.println("Choose [s] for student and [c] for civilian :");
        char answer4 = input.next().charAt(0);
        input.nextLine();

        // enter person's attributes first
        if (answer4 == 'c' || answer4 == 'C' || answer4 == 'S' || answer4 == 's') {
            System.out.println("Enter the new id: ");
            String id = input.nextLine();

            System.out.println("Enter the member name: ");
            String name = input.nextLine();

            System.out.println("Enter the address: ");
            String address = input.nextLine();

            System.out.println("Male [M] or Female [F]:");
            char gender = input.next().charAt(0);
            input.nextLine();

            System.out.println("Enter the age :");
            int age = input.nextInt();
            input.nextLine();

            System.out.println("Enter phone number: ");
            String phoneNumber = input.next();
            input.nextLine();

            // If the person is a civilian, ask for the balance
            if (answer4 == 'c' || answer4 == 'C') {
                System.out.println("Enter the initial balance: ");
                double currentBal = input.nextDouble();
                input.nextLine();
                members.add(new Civilian(name, address, gender, age, phoneNumber, id, currentBal));
            } else {
                members.add(new Student(name, address, gender, age, phoneNumber, id));
            }
            System.out.println("New Member added correctly.\n\n*******************************\n\n");
        } else
            System.out.println("Invalid input!");
    }

    /**
     * Deletes a member from the system.
     *
     * @param members The list of members to delete from.
     * @param id      The ID of the member to delete.
     */
    public static void deleteMember(ArrayList<Person> members, String id) {
        Person memberToDelete = searchMemberById(id, members);

        if (memberToDelete != null) {
            members.remove(memberToDelete);
            System.out.println("Member info deleted correctly");
        }
    }

    /**
     * Allows a member to borrow a library item.
     *
     * @param items            The list of items to borrow from.
     * @param identifierItem   The title or serial number of the item to borrow.
     * @param members          The list of members.
     * @param identifierMember The name or ID of the member borrowing the item.
     */
    public static void borrowItem(ArrayList<LibraryItem> items, String identifierItem,
            ArrayList<Person> members,
            String identifierMember) {
        LibraryItem item = new LibraryItem();
        if (identifierItem.matches("\\d+")) {
            if (searchItemBySerialNb(Long.parseLong(identifierItem), items) != null) {
                item = searchItemBySerialNb(Long.parseLong(identifierItem), items);
            }
        } else {
            if (searchItemByTitle(identifierItem, items) != null) {
                item = searchItemByTitle(identifierItem, items);
            }
        }

        Person person;
        boolean check = false;
        if (searchMemberByNameOrId(identifierMember, members) != null) {
            person = searchMemberByNameOrId(identifierMember, members);
            if (person instanceof Student)
                check = ((Student) person).borrowItem(item);
            else
                check = ((Civilian) person).borrowItem(item);
        }
        if (check)
            System.out.println("Item borrowed. Return date: " + item.getDateAvailable());
    }

    /**
     * Allows a member to return a library item.
     *
     * @param identifier The title or serial number of the item to return.
     * @param items      The list of items to return to.
     */
    public static void returnItem(String identifier, ArrayList<LibraryItem> items) {
        LibraryItem item = new LibraryItem();
        if (identifier.matches("\\d+")) {
            if (searchItemBySerialNb(Long.parseLong(identifier), items) != null) {
                item = searchItemBySerialNb(Long.parseLong(identifier), items);
            }
        } else if (searchItemByTitle(identifier, items) != null) {
            item = searchItemByTitle(identifier, items);
        }
        boolean check = false;
        if (item != null) {
            if (item.getPastOwners().get(item.getPastOwners().size() - 1) instanceof Civilian)
                check = ((Civilian) item.getPastOwners().get(item.getPastOwners().size() - 1))
                        .returnItem(item);
            else
                check = ((Student) item.getPastOwners().get(item.getPastOwners().size() - 1))
                        .returnItem(item);
        }
        if (check)
            System.out.println("Item returned successfully. Thank you");
    }

    /**
     * Displays all library items in the system.
     *
     * @param items The list of items to display.
     */
    public static void displayItems(ArrayList<LibraryItem> items) {
        // Loop to print all items in the array items
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString() +
                    "\n--------\n\n");
        }
    }

    /**
     * Displays all members in the system.
     *
     * @param members The list of members to display.
     */
    public static void displayMembers(ArrayList<Person> members) {
        // Loop to print all members in the array members
        for (int i = 0; i < members.size(); i++) {
            System.out.println(members.get(i).toString() +
                    "\n--------\n\n");
        }
    }

    /**
     * Displays the menu and gets the user's choice.
     *
     * @return The user's choice as an integer.
     */
    public static int getChoice() {
        int choice = -1;
        // Loop to get the user's choice and display the menu until they enter 13
        while (choice < 0 || choice > 13) {
            System.out.println("Choose a number: ");
            System.out.println("1- add new Library Item");
            System.out.println("2- modify an item");
            System.out.println("3- delete an item");
            System.out.println("4- add new member");
            System.out.println("5- modify a member info");
            System.out.println("6- delete a member info");
            System.out.println("7- Search an item// or check availability");
            System.out.println("8- Search a member");
            System.out.println("9- Borrow an item");
            System.out.println("10- Return an item");
            System.out.println("11- Display all items");
            System.out.println("12- Display all members");
            System.out.println("13- exit");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();
            if (choice < 0 || choice > 13) {
                System.out.println("Invalid choice!!");
            }
        }
        System.out.println(choice);
        return choice;
    }
}

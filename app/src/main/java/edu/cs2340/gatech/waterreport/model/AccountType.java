package edu.cs2340.gatech.waterreport.model;

/**
 * The enum class of Account Type
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */

public enum AccountType implements java.io.Serializable {
    //define the enum type
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator"),
    DEFAULT("Select an Account Type");

    private final String name;


    AccountType(String name) {
        this.name = name;
    }

    /**
     * default constructor
     */
    AccountType() {
        this("Select an Account Type");
    }

    //getName of enum
    public String getName() {
        return name;
    }

    public String toString() { return name; }
}

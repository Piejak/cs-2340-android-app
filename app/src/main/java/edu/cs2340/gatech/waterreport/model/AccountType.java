package edu.cs2340.gatech.waterreport.model;

/**
 * Created by Hui Li on 2/21/2017.
 */

public enum AccountType implements java.io.Serializable {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator"),
    DEFAULT("Select an Account Type");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    AccountType() {
        this("Select an Account Type");
    }

    public String getName() {
        return name;
    }

    public String toString() { return name; }
}

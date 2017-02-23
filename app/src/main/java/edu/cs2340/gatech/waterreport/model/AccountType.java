package edu.cs2340.gatech.waterreport.model;

/**
 * Created by Hui Li on 2/21/2017.
 */

public enum AccountType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Admin"),
    DEFAULT("Select an Account Type");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

    AccountType() {
        this("Select an Account Type");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() { return name;
    }
}

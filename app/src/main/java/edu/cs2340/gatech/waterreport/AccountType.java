package edu.cs2340.gatech.waterreport;

/**
 * Created by Hui Li on 2/21/2017.
 */

public enum AccountType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

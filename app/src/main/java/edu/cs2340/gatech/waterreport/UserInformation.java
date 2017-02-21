package edu.cs2340.gatech.waterreport;

/**
 * Created by yudong on 17/2/21.
 */

public class UserInformation {
    private String email;
    private String password;
    private AccountType type;
    private String status;

    public UserInformation() {
        email = "";
        password = "";
        type = null;
        status = "unregistered";
    }

    public String getEmail() {
        return email;
    }
    public void setUsername(String name) {
        email = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String input) {
        password = input;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String input) {
        status = input;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType input) {
        type = input;
    }



}

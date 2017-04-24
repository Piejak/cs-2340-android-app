package edu.cs2340.gatech.waterreport.model;

/**
 * This checks if user is banned.
 */

public class UserSecurityLog {

    private int loginAttempts;
    private boolean isBanned;
    private String email;
    private String userID;

    public UserSecurityLog() {
        loginAttempts = 0;
        isBanned = false;
    }

    public UserSecurityLog(int loginAttempts, boolean isBanned, String email) {
        this.loginAttempts = loginAttempts;
        this.isBanned = isBanned;
        this.email = email;
    }

    public void incrementLoginAttempts() {
        loginAttempts++;
    }

    public void resetLoginAttempts() {
        loginAttempts = 0;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void banUser() {
        isBanned = true;
    }

    public void unbanUser() {
        isBanned = false;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public String getSecurityLogEmail() {
        return email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}

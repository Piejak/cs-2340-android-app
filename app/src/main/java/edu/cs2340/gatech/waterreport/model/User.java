package edu.cs2340.gatech.waterreport.model;

import java.io.Serializable;

/**
 * User representation for association with a water report
 */
public class User implements Serializable {
    public String email;
    public String uid;
    public UserInformation userInformation;

    /**
     * Default constructor used for serializable
     */
    public User() {
        //no arg constructor for serializable
    }

    /**
     * Create a new user representation
     * @param email the user's email
     * @param uid the unique identifier of the user
     * @param userInformation the user information object associated with the user
     */
    public User(String email, String uid, UserInformation userInformation) {
        this.email = email;
        this.uid = uid;
        this.userInformation = userInformation;
    }

    @Override
    public String toString() {
        return email;
    }
}

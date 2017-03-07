package edu.cs2340.gatech.waterreport.model;

import java.io.Serializable;

/**
 * User representation for association with a water report
 */
public class User implements Serializable {
    public String email;
    public String uid;

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
     */
    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return email;
    }
}

package edu.cs2340.gatech.waterreport.model;

import java.io.Serializable;

/**
 * Created by brianpiejak on 3/6/17.
 */

public class User implements Serializable {
    public String email;
    public String uid;

    public User() {
        //no arg constructor for serializable
    }

    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public String toString() {
        return email;
    }
}

package edu.cs2340.gatech.waterreport.model;

import java.util.Map;

/**
 * the UserInformation class used to store the User's information.
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */

public class UserInformation {
    private String name; // represents actual name of user
    private Integer age;
    private String address;
    private String affiliation;
    private AccountType type;

    /**
     * default constructor with no params
     */
    public UserInformation() {
    }

    //getter and setter methods

    /**
     * Getter for name of user
     * @return the user's name
     */
    public String getRealName() {
        return name;
    }

    /**
     * Setter for name of user
     * @param name of the user
     */
    public void setRealName(String name) {
        this.name = name;
    }

    /**
     * Getter for age of user
     * @return user's age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Setter for age of user
     * @param age to set the current user's age as
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets address of current user
     * @return user's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of current user
     * @param address to set user's address as
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets affiliation of current user
     * @return user's affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets affiliation of current user
     * @param affiliation to be set for the user
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Gets account type of current user
     * @return user's account type
     */
    public AccountType getAccountType() {
        if (this.type == null) {
            this.type = AccountType.DEFAULT;
        }
        return this.type;
    }

    /**
     * Sets account type of current user
     * @param accountType of user
     */
    public void setAccountType(AccountType accountType) {
            this.type = accountType;
    }

    /**
     * Sets all fields of user except age (use setAge for age)
     * @param name for current user
     * @param address for current user
     * @param affiliation for current user
     * @param accountType for current user
     */
    public void updateAllFields(String name, String address, String affiliation,
                                AccountType accountType) {
        setRealName(name);
        //setAge(age);
        setAddress(address);
        setAffiliation(affiliation);
        setAccountType(accountType);
        //System.out.println(accountType);
    }

    /**
     * Returns the String representation of current user
     * @return String representation of current user
     */
    public String toString() {
        return name + age + address + affiliation + type;
    }
}

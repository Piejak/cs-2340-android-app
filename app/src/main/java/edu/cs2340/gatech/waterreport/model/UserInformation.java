package edu.cs2340.gatech.waterreport.model;

/**
 * Created by yudong on 17/2/21.
 */

public class UserInformation {
    private String name; // represents actual name of user
    private int age;
    private String address;
    private String affiliation;
    private AccountType type;

    public UserInformation() {
    }

    public String getRealName() {
        return name;
    }
    public void setRealName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAffiliation() {
        return affiliation;
    }
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public AccountType getAccountType() {
        return type;
    }
    public void setAccountType(AccountType type) {
        this.type = type;
    }
}

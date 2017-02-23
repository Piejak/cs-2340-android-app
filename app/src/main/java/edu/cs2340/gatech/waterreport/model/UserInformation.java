package edu.cs2340.gatech.waterreport.model;

import java.util.Map;

/**
 * Created by yudong on 17/2/21.
 */

public class UserInformation {
    private String name; // represents actual name of user
    private Integer age;
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

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
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
        if (this.type == null) {
            this.type = AccountType.DEFAULT;
        }
        return this.type;
    }
    public void setAccountType(String type) {
        if (this.type == null || type == "") {
            this.type = AccountType.DEFAULT;
        }
        this.type.setName(type);
    }

    public void updateAllFields(String name, String address, String affiliation,
                                String type) {
        setRealName(name);
        //setAge(age);
        setAddress(address);
        setAffiliation(affiliation);
        setAccountType(type);
    }

    public String toString() {
        return name + age + address + affiliation + type;
    }
}

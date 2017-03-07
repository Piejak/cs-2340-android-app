package edu.cs2340.gatech.waterreport.model;

import java.util.Date;

/**
 * Class to represent a water source report
 * @author Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 */
public class WaterSourceReport {
    private Date date;
    private int number = 0;
    private User reporter;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private Location location;

    /**
     * No arg constructor for Firebase
     */
    public WaterSourceReport() {
    }

    /**
     * Constructor that takes in fields that are not autogenerated
     * @param user the user that submitted the report
     * @param waterType representation of the type of water
     * @param waterCondition the condition of the water
     * @param number the report number
     * @param location the location of the water source
     */
    public WaterSourceReport(User user, WaterType waterType, WaterCondition waterCondition, int number, Location location) {
        this.date = new Date();
        this.number = number;
        this.reporter = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.location = location;
    }

    /**
     * Gets the date that the report was created
     * @return date object from the creation of the report
     */
    public Date getDate() {
        return date;
    }

    /**
     * get the user that created the report
     * @return user object that created the report
     */
    public User getReporter() {
        return reporter;
    }

    /**
     * get the water type
     * @return watertype enum representation of the water type
     */
    public WaterType getWaterType() {
        return waterType;
    }

    /**
     * get the water condition
     * @return watercondition enum representation of the water condition
     */
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }
}

package edu.cs2340.gatech.waterreport.model;

import java.util.Date;

/**
 * Class representing a water purity report
 */
public class WaterPurityReport {

    private Date date;
    private int number;

    private WaterOverallCondition overallCondition;

    private User worker;
    private Location location;
    private int virusPPM;
    private int contaminantPPM;

    /**
     * No arg constructor needed for Firebase
     */
    public WaterPurityReport() {

    }


    /**
     * Constructor to make a new Water purity report
     * @param user The user that created the report
     * @param overallCondition the overall condition of the water
     * @param number the report number
     * @param location The location of the water source being represented
     * @param virusPPM the amount of virus contaminant in the water in parts per million
     * @param contaminantPPM the amount of contaminant in the water in parts per million
     */
    public WaterPurityReport(User user, WaterOverallCondition overallCondition, int number, Location location, int virusPPM, int contaminantPPM) {
        this.date = new Date();
        this.worker = user;
        this.overallCondition =  overallCondition;
        this.number = number;
        this.location = location;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
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
        return worker;
    }

    /**
     * gets the overall condition of the water
     * @return enum representing the condition of the water
     */
    public WaterOverallCondition getWaterOverallCondition() {
        return overallCondition;
    }
}

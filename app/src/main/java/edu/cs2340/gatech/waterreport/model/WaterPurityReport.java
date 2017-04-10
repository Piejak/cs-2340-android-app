package edu.cs2340.gatech.waterreport.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class representing a water purity report
 */
public class WaterPurityReport {

    private Date date;
    private int number;

    private WaterOverallCondition overallCondition;

    private User worker;
    private Location location;
    private double virusPPM;
    private double contaminantPPM;

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
    public WaterPurityReport(User user, WaterOverallCondition overallCondition, int number, Location location, double virusPPM, double contaminantPPM) {
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
     * Iterates through a List of purity reports and returns a list of the reports that have
     * locations that match the current one
     * @param list of purity reports to match
     * @return the list of purity report that match the current report's location
     */
    public List<WaterPurityReport> matchingLocations(List<WaterPurityReport> list) {
        if (list == null) {
            throw new IllegalArgumentException("List passed in is null");
        }
        List<WaterPurityReport> matchingReports = new ArrayList<>();
        for (WaterPurityReport report : list) {
            if (locationMatch(report.getLocation())) {
                matchingReports.add(report);
            }
        }
        return matchingReports;
    }

    /**
     * gets the overall condition of the water
     * @return enum representing the condition of the water
     */
    public WaterOverallCondition getWaterOverallCondition() {
        return overallCondition;
    }
    public double getVirusPPM() {
        return virusPPM;
    }
    public double getContaminantPPM() {
        return contaminantPPM;
    }
    public Location getLocation() {
        return location;
    }

    public boolean locationMatch(Location other) {
        return location.equals(other);
    }
}

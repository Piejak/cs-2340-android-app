package edu.cs2340.gatech.waterreport.model;

/**
 * Class to represent location in latitude and longitude
 */
public class Location {
    private double latitude;
    private double longitude;

    /**
     * constructor to create a new location
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     */
    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

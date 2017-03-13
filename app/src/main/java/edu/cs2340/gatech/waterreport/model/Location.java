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

    /**
     * Default constructor for Firebase (This is why we can't just use the Google LatLng class)
     */
    public Location() {

    }

    /**
     * getter for latitude
     * @return latitude in double format
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * getter method for the longitude
     * @return longitude in double form
     */
    public double getLongitude() {
        return longitude;
    }
}
package edu.cs2340.gatech.waterreport.model;

/**
 * Class to represent location in latitude and longitude
 */
public class Location {
    private double latitude;
    private double longitude;
    private static final int MAX_LAT = 90;
    private static final int MIN_LAT = -90;
    private static final int MAX_LONG = 180;
    private static final int MIN_LONG = -180;

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

    public boolean equals(Location other) {
        if (latitude == other.getLatitude() && longitude == other.getLongitude()) {
            return true;
        }
        return false;
    }

    /**
     * Method to validate that a given location is valid
     * @param location the location that is being checked for validity
     * @return true if the location is legal, false otherwise
     * @throws IllegalArgumentException argument cannot be null
     */
    public static String validateLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (location.getLongitude() > MAX_LONG) {
            return "Longitude must be less than " + MAX_LONG;
        }
        if (location.getLongitude() < MIN_LONG) {
            return "Longitude must be greater than " + MIN_LONG;
        }
        if (location.getLatitude() > MAX_LAT) {
            return "Latitude must be less than " + MAX_LAT;
        }
        if (location.getLatitude() < MIN_LAT) {
            return "Latitude must be greater than " + MIN_LAT;
        }
        return "Valid";
    }
}
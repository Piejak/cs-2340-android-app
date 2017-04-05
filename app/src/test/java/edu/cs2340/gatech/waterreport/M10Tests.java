package edu.cs2340.gatech.waterreport;

import org.junit.Test;

import edu.cs2340.gatech.waterreport.model.Location;

import static org.junit.Assert.*;

/**
 * Our JUnits tests for M10
 */
public class M10Tests {

    /**
     * Tests matchingLocation in WaterPurityReport.java
     * @author Johnny Lee
     */
    @Test
    public void testMatchingLocationsInWaterPurityReport() {

    }

    /**
     * Test passing a null argument to Location.validateLocation()
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullLocation() {
        Location.validateLocation(null);
    }

    @Test
    public void testValidateLocation() {
        assertEquals("Latitude greater than max lat", "Latitude must be less than 90", Location.validateLocation(new Location(91, 0)));
        assertEquals("Longitude greater than max long", "Longitude must be less than 180", Location.validateLocation(new Location(0, 181)));
        assertEquals("Longitude less than min long", "Longitude must be greater than -180", Location.validateLocation(new Location(0, -181)));
        assertEquals("Latitude less than min lat", "Latitude must be greater than -90", Location.validateLocation(new Location(-91, 0)));
        assertEquals("Valid location", "Valid", Location.validateLocation(new Location(45, -120)));

        //boundary cases
        assertEquals("Latitude is max double", "Latitude must be less than 90", Location.validateLocation(new Location(Double.MAX_VALUE, 0)));
        assertEquals("Longitude is max double", "Longitude must be less than 180", Location.validateLocation(new Location(0, Double.MAX_VALUE)));
        assertEquals("Longitude is min double", "Longitude must be greater than -180", Location.validateLocation(new Location(0, -Double.MAX_VALUE)));
        assertEquals("Latitude is min double", "Latitude must be greater than -90", Location.validateLocation(new Location(-Double.MAX_VALUE, 0)));

    }


}
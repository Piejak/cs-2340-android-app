package edu.cs2340.gatech.waterreport;


import edu.cs2340.gatech.waterreport.model.AccountType;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.cs2340.gatech.waterreport.model.Location;
import edu.cs2340.gatech.waterreport.model.User;
import edu.cs2340.gatech.waterreport.model.UserInformation;
import edu.cs2340.gatech.waterreport.model.WaterCondition;
import edu.cs2340.gatech.waterreport.model.WaterOverallCondition;
import edu.cs2340.gatech.waterreport.model.WaterPurityReport;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;
import edu.cs2340.gatech.waterreport.model.WaterType;


/**
 * Our JUnits tests for M10
 */
public class M10Tests {

    private WaterPurityReport report;

    @Before
    public void setUp() {
        this.report = new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(0, 0), 0.0, 0.0);
    }

    /**
     * Tests matchingLocation Exception in WaterPurityReport.java
     * by Johnny Lee
     */
    @Test
    public void testExceptionMatchingLocationsInWaterPurityReport() {
        try {
            report.matchingLocations(null);
            Assert.fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            System.out.print("");
        }
    }

    /**
     * Tests a valid list with and without matching locations in WaterPurityReport.java
     * by Johnny Lee
     */
    @Test
    public void testMatchingLocationsInWaterPurityReport() {

        // Testing list with no matching locations
        List<WaterPurityReport> noMatchingLocationList = new ArrayList<>();
        noMatchingLocationList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(1, 1), 0.0, 0.0));

        List<WaterPurityReport> matchingReports1 = report.matchingLocations(noMatchingLocationList);
        assertEquals("Size is incorrect", 0, matchingReports1.size());
        for (WaterPurityReport report : matchingReports1) {
            assertTrue("Locations are incorrect",
                    report.getLocation().equals(report.getLocation()));
        }

        // Testing list with 1 matching locations at the last index
        List<WaterPurityReport> lastIndexList = new ArrayList<>();
        lastIndexList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(1, 1), 0.0, 0.0));
        lastIndexList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(-1, -1), 0.0, 0.0));
        lastIndexList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(0, 0), 0.0, 0.0));

        List<WaterPurityReport> matchingReports2 = report.matchingLocations(lastIndexList);
        assertEquals("Size is incorrect", 1, matchingReports2.size());
        for (WaterPurityReport report : matchingReports2) {
            assertTrue("Locations are incorrect",
                    report.getLocation().equals(report.getLocation()));
        }


        // Testing list with 2 matching locations at the first and last index
        List<WaterPurityReport> firstAndLastList = new ArrayList<>();
        firstAndLastList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Treatable, 1,
                new Location(0, 0), 0.0, 0.0));
        firstAndLastList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(1, 1), 0.0, 0.0));
        firstAndLastList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(-1, -1), 0.0, 0.0));
        firstAndLastList.add(new WaterPurityReport(new User("test@test.com", "1",
                new UserInformation()), WaterOverallCondition.Safe, 1,
                new Location(0, 0), 0.0, 0.0));

        List<WaterPurityReport> matchingReports3 = report.matchingLocations(firstAndLastList);
        assertEquals("Size is incorrect", 2, matchingReports3.size());
        for (WaterPurityReport report : matchingReports3) {
            assertTrue("Locations are incorrect",
                    report.getLocation().equals(report.getLocation()));
        }

    }

    /**
     * Tests matchingLocation with randomly generated locations in WaterPurityReport.java
     * by Johnny Lee
     */
    @Test
    public void testRandomListMatchingLocationsInWaterPurityReport() {
        List<WaterPurityReport> list = new ArrayList<>();
        int numMatchingLocations = 0;
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            Location location1 = new Location(rand.nextInt(), rand.nextInt());
            Location location2 = new Location(rand.nextInt(), rand.nextInt());
            Location location3 = new Location(rand.nextInt(), rand.nextInt());
            if (location1.getLatitude() == report.getLocation().getLatitude()
                    && location1.getLongitude() == report.getLocation().getLongitude()) {
                numMatchingLocations++;
            }
            if (location2.getLatitude() == report.getLocation().getLatitude()
                    && location2.getLongitude() == report.getLocation().getLongitude()) {
                numMatchingLocations++;
            }
            if (location3.getLatitude() == report.getLocation().getLatitude()
                    && location3.getLongitude() == report.getLocation().getLongitude()) {
                numMatchingLocations++;
            }

            list.add(new WaterPurityReport(new User("test@test.com", "dfajio",
                    new UserInformation()), WaterOverallCondition.Treatable, rand.nextInt(),
                    location1, rand.nextDouble(), rand.nextDouble()));
            list.add(new WaterPurityReport(new User("test2@test2.com", "fdjao",
                    new UserInformation()), WaterOverallCondition.Safe, rand.nextInt(),
                    location2, rand.nextDouble(), rand.nextDouble()));
            list.add(new WaterPurityReport(new User("test3@test3.com", "dfakp",
                    new UserInformation()), WaterOverallCondition.Unsafe, rand.nextInt(),
                    location3, rand.nextDouble(), rand.nextDouble()));
        }

        List<WaterPurityReport> matchingReports = report.matchingLocations(list);
        assertEquals("Size is incorrect", numMatchingLocations, matchingReports.size());
        for (WaterPurityReport report : matchingReports) {
            assertTrue("Locations are incorrect",
                    report.getLocation().equals(report.getLocation()));
        }
    }

    /**
     * Tests matchingLocation with an empty list in WaterPurityReport.java
     * by Johnny Lee
     */
    @Test
    public void testEmptyListMatchingLocationsInWaterPurityReport() {
        List<WaterPurityReport> list = new ArrayList<>();

        List<WaterPurityReport> matchingReports = report.matchingLocations(list);
        assertEquals("Size is incorrect", 0, matchingReports.size());
        for (WaterPurityReport report : matchingReports) {
            assertTrue("Locations are incorrect",
                    report.getLocation().equals(report.getLocation()));
        }
    }

    /**
     * Test passing a null argument to Location.validateLocation()
     * by Brian Piejak
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullLocation() {
        Location.validateLocation(null);
    }

    /**
     * Test Location.validateLocation()
     * by Brian Piejak
     */
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

    /**
     * Test update the user information
     * by Hui Li
     */
    @Test
    public void testUpdateUserInfo() {
        User testUser = new User("12345@qq.com", "2" , new UserInformation());
        UserInformation testInfo = testUser.userInformation;
        testInfo.updateAllFields("Hui Li", "Atlanta", "GT", AccountType.ADMINISTRATOR);
        assertEquals(AccountType.ADMINISTRATOR, testInfo.getAccountType());
        assertEquals("Hui Li", testInfo.getRealName());
        assertEquals("Atlanta", testInfo.getAddress());
        assertEquals("GT", testInfo.getAffiliation());
    }

    /**
    * Test update the user information to null
    * by Hui Li
    */
    @Test
    public void testUpdateUserInfoNull() {
        User testUser = new User("12345@qq.com", "2" , new UserInformation());
        UserInformation testInfo = testUser.userInformation;
        testInfo.updateAllFields("Hui Li", "Atlanta", "GT", AccountType.ADMINISTRATOR);
        testInfo.updateAllFields(null,null,null,AccountType.DEFAULT);
        assertEquals(AccountType.DEFAULT, testInfo.getAccountType());
        assertEquals(null, testInfo.getRealName());
        assertEquals(null, testInfo.getAddress());
        assertEquals(null, testInfo.getAffiliation());
    }

    /**
     * Test WaterSourceReport
     *  by Yudong Shao
     */

    public void testWaterSourceReport() {
        WaterSourceReport wsr = new WaterSourceReport(new User("85223375@163.com", "3" ,
                new UserInformation()), WaterType.Lake, WaterCondition.TreatableClear,
                3, new Location(5, 6));
        User testUser = wsr.getReporter();
        UserInformation testInfo = wsr.getReporter().userInformation;
        testInfo.updateAllFields("Yudong", "Atlanta", "GT", AccountType.MANAGER);
        Location testLocation = wsr.getLocation();

        assertEquals(AccountType.MANAGER, testInfo.getAccountType());
        assertEquals("Yudong", testInfo.getRealName());
        assertEquals("Atlanta", testInfo.getAddress());
        assertEquals("GT", testInfo.getAffiliation());
        assertEquals(WaterType.Lake, wsr.getWaterType());
        assertEquals(WaterCondition.TreatableClear, wsr.getWaterCondition());
        assertEquals(5, wsr.getLocation().getLatitude());
        assertEquals(6, wsr.getLocation().getLongitude());

    }

    /**
     * Test WaterSourceReport with null value
     *  by Yudong Shao
     */
    public void testWaterSourceReportNull() {
        WaterSourceReport wsr = new WaterSourceReport(new User("85223375@163.com", "3" ,
                new UserInformation()), WaterType.Lake, WaterCondition.TreatableClear,
                3, null);
        User testUser = wsr.getReporter();
        UserInformation testInfo = testUser.userInformation;
        testInfo.updateAllFields("Yudong", "Atlanta", "GT", AccountType.MANAGER);
        Location testLocation = wsr.getLocation();


        assertEquals(null, testLocation);

    }


//    /**
//     * Test WaterCondition
//     *  by Jimmy Dinh-Nguyen
//     */
//    public void testWaterCondition() {
//        WaterCondition w = new WaterCondition("Waste");
//        WaterCondition w = new WaterCondition("Treatable-Clear");
//        WaterCondition w = new WaterCondition("Treatable-Muddy");
//        WaterCondition w = new WaterCondition("Potable");
//    }
//
//    /**
//     * Test AccountType
//     * by Jimmy Dinh-Nguyen
//     */
//    public void testAccountType() {
//        AccountType aT1 = new AccountType("User");
//        AccountType aT2 = new AccountType("Worker");
//        AccountType aT3 = new AccountType("Manager");
//        AccountType aT4 = new AccountType("Administrator");
//
//    }
}
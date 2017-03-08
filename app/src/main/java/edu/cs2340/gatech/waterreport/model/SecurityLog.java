package edu.cs2340.gatech.waterreport.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Logs activity on the system
 */
public class SecurityLog {
    private static ArrayList<String> logs = new ArrayList<>();

    /**
     * Adds a new login attempt to the log
     * @param timestamp the time the attempt occurred
     * @param uid the id of the user trying to login
     * @param status the success status of the attemp - successful, bad email, bad password
     */
    public static void logLoginAttempt(Date timestamp, String uid, String status) {
        logs.add("LOGIN " + timestamp.toString() + " " + uid + " " + status);
    }

    /**
     * Logs the deletion of an account
     * @param timestamp the time the deletion occurred
     * @param adminId the id of the admin that deleted the account
     * @param userId the id of the account that was deleted
     */
    public static void logAccountDelete(Date timestamp, String adminId, String userId) {
        logs.add("ACCOUNT_DELETE " + timestamp.toString() + " " +  adminId + " " + userId);
    }

    public static void logAccountBan(Date timestamp, String adminId, String userId) {
        logs.add("BAN " + timestamp.toString() + " " +  adminId + " " + userId);
    }

    public static void logAccountUnblock(Date timestamp, String adminId, String userId) {
        logs.add("UNBLOCK " + timestamp.toString() + " " +  adminId + " " + userId);
    }

    public static void logReportDelete(Date timestamp, String managerId, String reportId) {
        logs.add("REPORT_DELETE " + timestamp.toString() + " " +  managerId + " " + reportId);
    }

}

package edu.cs2340.gatech.waterreport.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Logs activity on the system
 */
public class SecurityLogCustom {
    private static final ArrayList<String> logs = new ArrayList<>();

    /**
     * Adds a new login attempt to the log
     * @param timestamp the time the attempt occurred
     * @param uid the id of the user trying to login
     * @param status the success status of the attempt - successful, bad email, bad password
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

    /**
     * Logs the banning of an account
     * @param timestamp the time at which the account was banned
     * @param adminId the admin that banned the account
     * @param userId the user that was banned
     */
    public static void logAccountBan(Date timestamp, String adminId, String userId) {
        logs.add("BAN " + timestamp.toString() + " " +  adminId + " " + userId);
    }

    /**
     * Logs the unblocking of an account
     * @param timestamp the time the account was unblocked
     * @param adminId the admin that unblocked the account
     * @param userId the user that was unblocked
     */
    public static void logAccountUnblock(Date timestamp, String adminId, String userId) {
        logs.add("UNBLOCK " + timestamp.toString() + " " +  adminId + " " + userId);
    }

    /**
     * Logs the deletion of a report
     * @param timestamp the time the report was deleted
     * @param managerId the manager that deleted the report
     * @param reportId the report that was deleted
     */
    public static void logReportDelete(Date timestamp, String managerId, String reportId) {
        logs.add("REPORT_DELETE " + timestamp.toString() + " " +  managerId + " " + reportId);
    }

}

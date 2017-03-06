package edu.cs2340.gatech.waterreport.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterSourceReport {
    private Date date;
    private int number = 0;

    private User reporter;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private Location location;

    public WaterSourceReport() {
        // Default constructor required for calls to DataSnapshot.getValue(WaterSourceReport.class)
    }

    public WaterSourceReport(User user, WaterType waterType, WaterCondition waterCondition, int number, Location location) {
        date = new Date();
        this.number = number;
        reporter = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public WaterType getWaterType() {
        return waterType;
    }

    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    public void setWaterCondition(WaterCondition waterCondition) {
        this.waterCondition = waterCondition;
    }
}

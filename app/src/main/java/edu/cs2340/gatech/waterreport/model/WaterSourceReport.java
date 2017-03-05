package edu.cs2340.gatech.waterreport.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterSourceReport {
    private Date date;
    private int number = 0;

    private FirebaseUser reporter;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private Location location;

    public WaterSourceReport() {
        // Default constructor required for calls to DataSnapshot.getValue(WaterSourceReport.class)
    }

    public WaterSourceReport(FirebaseUser user, WaterType waterType, WaterCondition waterCondition, int number, Location location) {
        date = new Date();
        this.number = number;
        reporter = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public FirebaseUser getReporter() {
        return reporter;
    }

    public void setReporter(FirebaseUser reporter) {
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

package edu.cs2340.gatech.waterreport.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterSourceReport {
    private String date = "";
    private int number = 0;

    private FirebaseUser reporter;
    private WaterType waterType;
    private WaterCondition waterCondition;

    public WaterSourceReport() {
        // Default constructor required for calls to DataSnapshot.getValue(WaterSourceReport.class)
    }

    public WaterSourceReport(FirebaseUser user, WaterType waterType, WaterCondition waterCondition) {
        Calendar temp = Calendar.getInstance();
        int day = temp.get(Calendar.DATE);
        int month = temp.get(Calendar.MONTH);
        int year = temp.get(Calendar.YEAR);
        date = "" + year + "/" + month + "/" + day;
        reporter = user;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

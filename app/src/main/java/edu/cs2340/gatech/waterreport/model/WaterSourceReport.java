package edu.cs2340.gatech.waterreport.model;

import java.util.Calendar;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterSourceReport {
    private String date = "";
    private int number = 0;

    private String reporter;
    private WaterType waterType;
    private WaterCondition waterCondition;

    public WaterSourceReport() {
        Calendar temp = Calendar.getInstance();
        int day = temp.get(Calendar.DATE);
        int month = temp.get(Calendar.MONTH);
        int year = temp.get(Calendar.YEAR);
        date = "" + year + "/" + month + "/" + day;
        number++;
    }

    public String getDate() {
        return date;
    }
    public String getReporter() {
        return reporter;
    }
    public int getNumber() {
        return number;
    }
    public WaterType getWaterType() {
        return waterType;
    }
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }
}

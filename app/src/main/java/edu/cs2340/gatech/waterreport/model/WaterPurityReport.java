package edu.cs2340.gatech.waterreport.model;


import java.util.Calendar;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterPurityReport {
    private int day;
    private int month;
    private int year;
    private String date = "";
    private int number = 0;

    private OverallCondition ocondition;

    private String worker;
    private int virus;
    private int contaminant;

    public WaterPurityReport() {
        Calendar temp = Calendar.getInstance();
        day = temp.get(Calendar.DATE);
        month = temp.get(Calendar.MONTH);
        year = temp.get(Calendar.YEAR);
        date = "" + year + "/" + month + "/" + day;
        number++;
    }
    public String getDate() {
        return date;
    }
    public String getWorker() {
        return worker;
    }
    public int getNumber() {
        return number;
    }
    public int getVirus() {
        return virus;
    }
    public int getContaminant() {
        return contaminant;
    }
    public OverallCondition getOcondition() {
        return ocondition;
    }

    public enum OverallCondition {
        Safe("Safe"), Treatable("Treatable"), Unsafe("Unsafe");
        private String overall;
        OverallCondition(String input) {
            overall = input;
        }
        public String getOverall() {
            return overall;
        }
    }





}

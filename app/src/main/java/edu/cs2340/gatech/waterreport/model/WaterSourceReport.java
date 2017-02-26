package edu.cs2340.gatech.waterreport.model;

import java.util.Calendar;

/**
 * Created by yudong on 17/2/25.
 */

public class WaterSourceReport {
    private int day;
    private int month;
    private int year;
    private String date = "";
    private int number = 0;

    private String reporter;
    private TypeOfWater twater;
    private ConditionOfWater cwater;

    public WaterSourceReport() {
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
    public String getReporter() {
        return reporter;
    }
    public int getNumber() {
        return number;
    }
    public TypeOfWater getTwater() {
        return twater;
    }
    public ConditionOfWater getCwater() {
        return cwater;
    }


    public enum TypeOfWater {
        Bottled("Bottled"),
        Well("Well"),
        Stream("Stream"),
        Lake("Lake"),
        Spring("Spring"),
        Other("Other");
        private String type;
        TypeOfWater(String input) {
            type = input;
        }
        public String getType() {
            return type;
        }
    }
    public enum ConditionOfWater {
        Waste("Waste"), TreatableClear("Treatable-Clear"),
        TreatableMuddy("Treatable-Muddy"), Potable("Potable");
        private String condition;
        ConditionOfWater(String input) {
            condition = input;
        }
        public String getCondition() {
            return condition;
        }


    }





}

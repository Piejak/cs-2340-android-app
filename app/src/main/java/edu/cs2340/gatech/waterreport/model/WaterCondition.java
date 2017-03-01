package edu.cs2340.gatech.waterreport.model;

/**
 * Created by brianpiejak on 3/1/17.
 */

public enum WaterCondition {
    Waste("Waste"),
    TreatableClear("Treatable-Clear"),
    TreatableMuddy("Treatable-Muddy"),
    Potable("Potable");

    private String condition;

    WaterCondition(String input) {
        condition = input;
    }

    public String getCondition() {
        return condition;
    }
}

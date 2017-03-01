package edu.cs2340.gatech.waterreport.model;

/**
 * Created by brianpiejak on 3/1/17.
 */

public enum WaterType {
    Bottled("Bottled"),
    Well("Well"),
    Stream("Stream"),
    Lake("Lake"),
    Spring("Spring"),
    Other("Other");

    private String type;

    WaterType(String input) {
        type = input;
    }
    public String getType() {
        return type;
    }
}

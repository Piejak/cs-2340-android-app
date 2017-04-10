package edu.cs2340.gatech.waterreport.model;

/**
 * Enum representation of the water type of a water source
 */
public enum WaterType {
    Bottled("Bottled"),
    Well("Well"),
    Stream("Stream"),
    Lake("Lake"),
    Spring("Spring"),
    Other("Other");

    private final String type;

    /**
     * construct a new water type
     * @param input the type of the water
     */
    WaterType(String input) {
        type = input;
    }

    @Override
    public String toString() {
        return type;
    }
}

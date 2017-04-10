package edu.cs2340.gatech.waterreport.model;

/**
 * Enum representation of the condition of a water source
 */
public enum WaterCondition {
    Waste("Waste"),
    TreatableClear("Treatable-Clear"),
    TreatableMuddy("Treatable-Muddy"),
    Potable("Potable");

    private final String condition;

    /**
     * Create a new water condition with the condition passed in as a string
     * @param input the condition of the water
     */
    WaterCondition(String input) {
        condition = input;
    }

    @Override
    public String toString() {
        return condition;
    }
}

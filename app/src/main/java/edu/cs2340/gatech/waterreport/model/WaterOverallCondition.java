package edu.cs2340.gatech.waterreport.model;

/**
 * Enum representation of the overall condition of a water source for use with water
 * purity reports
 */
public enum WaterOverallCondition {
    Safe("Safe"),
    Treatable("Treatable"),
    Unsafe("Unsafe");

    private String condition;

    /**
     * Create a new water condition with the condition passed in as a string
     * @param input the condition of the water
     */
    WaterOverallCondition(String input) {
        condition = input;
    }

    @Override
    public String toString() {
        return condition;
    }
}

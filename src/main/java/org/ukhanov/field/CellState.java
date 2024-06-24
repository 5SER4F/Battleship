package org.ukhanov.field;

public enum CellState {
    EMPTY('#'),
    HIT('X'),
    MISS('.'),
    SHIP('S');
    private char label;

    CellState(char label) {
        this.label = label;
    }

    public char getLabel() {
        return label;
    }
}

package org.ukhanov.field;

public interface EnemyField {
    /**
     * Проверяем стреляли ли уже в эту клетку
     */
    boolean canStrike(int row, int column);
    
    void strikeResult(int row, int column, CellState strikeStatus);

    CellState getCellState(int row, int column);
}
